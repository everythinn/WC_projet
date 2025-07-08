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
	public int formerApprentices = 0;
	public int lives = 1;
	
	public ClanCat() {};
	
	public ClanCat(int age, GenderEnum gender, String appearance, String prefix, Clan clan, RankEnum clanRank, String suffix) {
		this.age=age;
		this.gender=gender;
		this.appearance=appearance;
		this.prefix=prefix;
		this.clan=clan;
		this.clanRank=checkRankByAge(clanRank, age);
		this.suffix=checkSuffixByRank(suffix, clanRank);
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
	
	@Column(name="formerApps")
	public int getFormerApprentices() {
		return formerApprentices;
	}

	public void setFormerApprentices(int formerApprentices) {
		this.formerApprentices = formerApprentices;
	}

	@Column(name="lives")
	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives=lives;
	}
	
	// returns proper rank according to age
	public RankEnum checkRankByAge(RankEnum clanRank, int age) {
		if (age<6) {
			return RankEnum.KIT;
		} else if (age>=6 && age<12) {
			return RankEnum.APPRENTICE;
		} else return clanRank;
	}
	
	// return entered suffix or overrides it if the cat is of a rank that requires a specific suffix
	public String checkSuffixByRank(String suffix, RankEnum clanRank) {
		if(clanRank==RankEnum.LEADER) {
			return "star";
		} else if (clanRank==RankEnum.APPRENTICE) {
			return "paw";
		} else if (clanRank==RankEnum.KIT) {
			return "kit";
		} else return suffix;
	}
	
	// returns the amount of lives is 1 or returns the current amount of lives if the cat is a leader
	public int checkLivesByRank(int lives, RankEnum clanRank) {
		if(clanRank==RankEnum.LEADER) {
			return lives;
		} else return 1;
	}
	

}