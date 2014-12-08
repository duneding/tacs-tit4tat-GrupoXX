package com.utn.tacs.tit4tat.model;

import java.net.URL;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.utn.tacs.tit4tat.objectify.Identifiable;

@Entity
public class ItemMeli implements Identifiable {

	@Id
	private Long id;

	private String[] category;

	private String description;
	
	private Blob image;
	
	private URL permalink;	
	
	private String thumbnail;
	
	public ItemMeli() {
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
	
	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}	

	public URL getPermalink() {
		return permalink;
	}

	public void setPermalink(URL permalink) {
		this.permalink = permalink;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	@Override
	public String toString() {
//		return "Item [id=" + id + ", category=" + category + ", description="
//				+ description + ", owner=" + owner.getName() + "]";
		return "Item [id=" + id + ", description="
				+ description + "]";
	}

}
