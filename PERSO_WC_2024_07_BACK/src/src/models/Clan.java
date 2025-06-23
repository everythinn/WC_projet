package src.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="clan")
public class Clan {
	
	public int id;
	public String name;
	public String description;
	public String territory_type;
	public String preys;
	public List<ClanCat> members;
	
	public Clan() {};
	
	public Clan(String name, String description, String terr_type, String preys) {
		this.name=name;
		this.description=description;
		this.territory_type=terr_type;
		this.preys=preys;
	}
	
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}
	
	@Column(name="territoryType")
	public String getTerrType() {
		return territory_type;
	}
	
	public void setTerrType(String territory_type) {
		this.territory_type=territory_type;
	}
	
	@Column(name="preys")
	public String getPreys() {
		return preys;
	}
	
	public void setPreys(String preys) {
		this.preys=preys;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="members")
	public List<ClanCat> getMembers(){
		return members;
	}
	
	public void setMembers(List<ClanCat> members) {
		this.members=members;
	}
	
	public void addMember(ClanCat cat) {
		members.add(cat);
	}
	
}
