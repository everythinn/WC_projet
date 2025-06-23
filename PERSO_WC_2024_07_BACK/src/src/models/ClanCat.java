package src.models;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;


import src.enums.GenderEnum;
import src.enums.RankEnum;

@Entity
@Table(name="clancat")
public class ClanCat extends ACat {
	
	public String prefix;
	public String suffix;
	public Clan clan;
	public RankEnum rank;
	public ClanCat mentor = null;
	public int lives = 1;
	
	public ClanCat() {};
	
	public ClanCat(String prefix, String suffix, int age, GenderEnum gender, String appearance, Clan clan, RankEnum rank) {
		this.prefix=prefix;
		this.suffix=getSuffixByRank(rank, suffix);
		this.age=age;
		this.gender=gender;
		this.appearance=appearance;
		this.clan=clan;
		this.rank=rank;
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
	@Column(length=20, name="rank")
	public RankEnum getRank() {
		return rank;
	}
	
	public void setRank(RankEnum rank) {
		this.rank=rank;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mentor")
	public ClanCat getMentor() {
		return mentor;
	}
	
	public void setMentor(RankEnum rank, ClanCat mentor) {
		this.mentor=getMentorByRank(rank, mentor);
	}
	
	@Column(name="lives")
	public void setLives(RankEnum rank, int lives) {
		this.lives=getLivesByRank(rank, lives);
	}
	
	public String getSuffixByRank(RankEnum rank, String suffix) {
		if(rank==RankEnum.LEADER) {
			return "star";
		} else if (rank==RankEnum.APPRENTICE) {
			return "paw";
		} else if (rank==RankEnum.KIT) {
			return "kit";
		} else return suffix;
	}
	
	public ClanCat getMentorByRank(RankEnum rank, ClanCat mentor) {
		if(rank==RankEnum.APPRENTICE) {
			return mentor;
		} else return null;
	}
	
	public int getLivesByRank(RankEnum rank, int lives) {
		if(rank==RankEnum.LEADER) {
			return lives;
		} else return 1;
	}
	
	public void skipMoon() {
		age++;
	}
	
	public void becomeApprentice(ClanCat mentor) {
		if (age>=6) {
			setRank(RankEnum.APPRENTICE);
			setSuffix(RankEnum.APPRENTICE, "paw");
			setMentor(RankEnum.APPRENTICE, mentor);
		}
	}
	
	public void becomeWarrior(String suffix) {
		if (age>=12) {
			setRank(RankEnum.WARRIOR);
			setSuffix(RankEnum.WARRIOR, suffix);
			setMentor(RankEnum.WARRIOR, null);
		}
	}
	
	public void becomeMedCat(String suffix) {
		if (age>=12) {
			setRank(RankEnum.MEDICINE_CAT);
			setSuffix(RankEnum.MEDICINE_CAT, suffix);
			setMentor(RankEnum.MEDICINE_CAT, null);
		}
	}
	
	public void becomeDeputy() {
		setRank(RankEnum.DEPUTY);
	}
	
	public void becomeLedaer() {
		this.lives=9;
		setRank(RankEnum.LEADER);
		setSuffix(RankEnum.LEADER, "star");
	}
}
