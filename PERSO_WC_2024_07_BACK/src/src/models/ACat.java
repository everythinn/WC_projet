package src.models;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import src.enums.GenderEnum;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class ACat {
	
	protected int id;
	protected int age;
	protected GenderEnum gender;
	protected String appearance;
	
	public ACat() {};
	
	public ACat(int id, int age, GenderEnum gender, String appearance) {
		this.id=id;
		this.age=age;
		this.gender=gender;
		this.appearance=appearance;
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
	
	@Column(name="age")
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	@Enumerated
	@Column(length=10, name="gender")
	public GenderEnum getGender() {
		return gender;
	}
	
	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
	
	@Column(name="appearance")
	public String getAppearance() {
		return appearance;
	}
	
	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}
	
}
