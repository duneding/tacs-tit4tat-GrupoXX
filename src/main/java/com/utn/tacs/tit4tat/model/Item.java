package com.utn.tacs.tit4tat.model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "items")
public class Item implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	private String[] category;

	private String description;
	
	private byte[] image;
	
	private URL permalink;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private Usuario owner;

	public Long getId() {
		return id;
	}

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

	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}	

	public URL getPermalink() {
		return permalink;
	}

	public void setPermalink(URL permalink) {
		this.permalink = permalink;
	}
	
	@Override
	public String toString() {
//		return "Item [id=" + id + ", category=" + category + ", description="
//				+ description + ", owner=" + owner.getName() + "]";
		return "Item [id=" + id + ", description="
				+ description + "]";
	}

}
