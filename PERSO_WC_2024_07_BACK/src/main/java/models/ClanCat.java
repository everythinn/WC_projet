package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import enums.GenderEnum;
import enums.RankEnum;

@Entity
@Table(name="clancat")
@NamedQueries({
	@NamedQuery(name = "ClanCat.findById", query = "SELECT cc FROM ClanCat cc WHERE cc.id = :id"),
	@NamedQuery(name = "ClanCat.findAll", query = "SELECT cc FROM ClanCat cc"),
	@NamedQuery(name = "ClanCat.findLeaders", query = "SELECT cc FROM ClanCat cc WHERE cc.clanRank = :leader"),
	@NamedQuery(name = "ClanCat.findDeputies", query = "SELECT cc FROM ClanCat cc WHERE cc.clanRank = :deputy"),
	@NamedQuery(name = "ClanCat.findMedCats", query = "SELECT cc FROM ClanCat cc WHERE cc.clanRank = :medicine_cat")
})
public class ClanCat extends ACat {
	
	public String prefix;
	public String suffix;
	public Clan clan;
	public RankEnum clanRank;
	public ClanCat mentor = null;
	@Column(name="lives")
	public int lives = 1;
	
	public ClanCat() {};
	
	public ClanCat(String prefix, String suffix, int age, GenderEnum gender, String appearance, Clan clan, RankEnum clanRank) {
		this.prefix=prefix;
		this.suffix=getSuffixByRank(clanRank, suffix);
		this.age=age;
		this.gender=gender;
		this.appearance=appearance;
		this.clan=clan;
		this.clanRank=clanRank;
	}

	@Column(name="prefix")
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix=prefix;
	}
	
	@Column(name="suffix")
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix=suffix;
	}
	
	public void setSuffix(RankEnum rank, String suffix) {
		this.suffix=getSuffixByRank(rank, suffix);
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="clan")
	public Clan getClan() {
		return clan;
	}
	
	public void setClan(Clan clan) {
		this.clan=clan;
	}
	
	@Enumerated
	@Column(length=20, name="clanRank")
	public RankEnum getClanRank() {
		return clanRank;
	}
	
	public void setClanRank(RankEnum clanRank) {
		this.clanRank=clanRank;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mentor")
	public ClanCat getMentor() {
		return mentor;
	}
	
	public void setMentor(ClanCat mentor){
		this.mentor=mentor;
	}
	
	public void setMentor(RankEnum clanRank, ClanCat mentor) {
		this.mentor=getMentorByRank(clanRank, mentor);
	}
	
	public void setLives(RankEnum rank, int lives) {
		this.lives=getLivesByRank(rank, lives);
	}
	
	// return entered suffix or overrides it if the cat is of a rank that requires a specific suffix
	public String getSuffixByRank(RankEnum clanRank, String suffix) {
		if(clanRank==RankEnum.LEADER) {
			return "star";
		} else if (clanRank==RankEnum.APPRENTICE) {
			return "paw";
		} else if (clanRank==RankEnum.KIT) {
			return "kit";
		} else return suffix;
	}
	
	// returns the cat's mentor or returns null if the cat isn't an apprentice
	public ClanCat getMentorByRank(RankEnum clanRank, ClanCat mentor) {
		if(clanRank==RankEnum.APPRENTICE) {
			return mentor;
		} else return null;
	}
	
	// returns the amount of lives is 1 or returns the current amount of lives if the cat is a leader
	public int getLivesByRank(RankEnum clanRank, int lives) {
		if(clanRank==RankEnum.LEADER) {
			return lives;
		} else return 1;
	}
	
	// skips a moon and adds one to the cat's age
	public void skipMoon() {
		age++;
	}
	
	// makes the cat become an apprentice only if age is above 6, then changes their rank, suffix and mentor accordingly
	public void becomeApprentice(ClanCat mentor) {
		if (age>=6) {
			setClanRank(RankEnum.APPRENTICE);
			setSuffix(RankEnum.APPRENTICE, "paw");
			setMentor(RankEnum.APPRENTICE, mentor);
		}
	}
	
	// makes the cat become a warrior only if age is above 12, then changes rank, removes mentor and updates suffix to entered suffix
	public void becomeWarrior(String suffix) {
		if (age>=12) {
			setClanRank(RankEnum.WARRIOR);
			setSuffix(RankEnum.WARRIOR, suffix);
			setMentor(RankEnum.WARRIOR, null);
		}
	}
	
	// makes the cat become a medicine cat only if age is above 12, then changes rank, removes mentor and updates suffix to entered suffix
	public void becomeMedCat(String suffix) {
		if (age>=12) {
			setClanRank(RankEnum.MEDICINE_CAT);
			setSuffix(RankEnum.MEDICINE_CAT, suffix);
			setMentor(RankEnum.MEDICINE_CAT, null);
		}
	}
	
	// changes rank to deputy
	public void becomeDeputy() {
		setClanRank(RankEnum.DEPUTY);
	}
	
	// changes rank to leader, updates suffix and grants 9 lives
	public void becomeLedaer() {
		this.lives=9;
		setClanRank(RankEnum.LEADER);
		setSuffix(RankEnum.LEADER, "star");
	}
}