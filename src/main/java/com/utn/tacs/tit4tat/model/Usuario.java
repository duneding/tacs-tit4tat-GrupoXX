package com.utn.tacs.tit4tat.model;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.utn.tacs.tit4tat.objectify.Identifiable;

@Entity
public class Usuario implements Identifiable {

	@Id
	private Long id;

	private String name;

	private List<Item> items;

	@Override
	public Long getId() {
		return id;
	}

	@Override
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