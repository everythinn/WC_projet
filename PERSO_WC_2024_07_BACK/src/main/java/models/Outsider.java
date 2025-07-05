package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import enums.GenderEnum;
import enums.StatusEnum;

@Entity
@Table(name="outsider")
public class Outsider extends ACat {

	public String name;
	public StatusEnum status;
	
	public Outsider() {};
	
	public Outsider(String name, int age, GenderEnum gender, String appearance, StatusEnum status) {
		this.name=name;
		this.age=age;
		this.gender=gender;
		this.appearance=appearance;
		this.status=status;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	@Enumerated
	@Column(length=20, name="status")
	public StatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(StatusEnum status) {
		this.status=status;
	}
}
