package com.utn.tacs.tit4tat.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.utn.tacs.tit4tat.objectify.Identifiable;

@SuppressWarnings("serial")
@Entity
//@Scope("session")
public class Item implements Identifiable, Serializable {

	@Id
	private Long id;

	private String[] category;

	private String shortDescription;
	
	private String description;
	
	private Blob image;
	
	private String permalink;

	@Index
	private Ref<Usuario> owner;
	
	
	public Item() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String description) {
		this.shortDescription = description;
	}
	
	public Usuario getOwner() {
		return owner.get();
	}

	public void setOwner(Usuario owner) {
		this.owner = Ref.create(owner);
	}
	
	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}	

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	
	@Override
	public String toString() {
//		return "Item [id=" + id + ", category=" + category + ", description="
//				+ description + ", owner=" + owner.getName() + "]";
		return "Item [id=" + id + ", description="
				+ description + "]";
	}
	
	public String flatCategories() {
		StringBuilder builder = new StringBuilder();
		for(String s : category) {
		    builder.append(s);
		}
		return builder.toString();

	}
	
}
