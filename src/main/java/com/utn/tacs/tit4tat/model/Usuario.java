package com.utn.tacs.tit4tat.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
//@Entity
//@Table(name="usuarios")
public class Usuario implements Serializable {
	
//	@Id
//	@GeneratedValue
	private Long id;
	
	private String name;
	
//	@OneToMany(mappedBy="owner")
	private List<Item> items;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public Usuario() {
	}
	
	public Usuario(String name) {
		this.setName(name);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + "]";
	}
}
