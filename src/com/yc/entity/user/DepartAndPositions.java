package com.yc.entity.user;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@DiscriminatorValue("departAndPositions")
@JsonIgnoreProperties(value = { "personnels" })
public class DepartAndPositions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "departments_id")
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "positions_id")
	private Positions positions;
	
	@OneToMany(mappedBy = "departAndPositions")
	private List<Personnel> personnels;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Positions getPositions() {
		return positions;
	}

	public void setPositions(Positions positions) {
		this.positions = positions;
	}

	@JsonIgnore
	public List<Personnel> getPersonnels() {
		return personnels;
	}
	@JsonIgnore
	public void setPersonnels(List<Personnel> personnels) {
		this.personnels = personnels;
	}
	
}
