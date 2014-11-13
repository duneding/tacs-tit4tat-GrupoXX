package com.utn.tacs.tit4tat.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.utn.tacs.tit4tat.objectify.Identifiable;

@Entity
public class Solicitud implements Identifiable {

	@Id
	private Long id;
	private String detail;
	
	private Ref<Usuario> offeredUser;	
	private Ref<Item> offeredItem;
	
	private Ref<Usuario> requestUser;
	private Ref<Item> requestItem;

	@Index
	private int state;
	
	public Item getOfferedItem() {
		return offeredItem.get();
	}

	public void setOfferedItem(Item itemOfrecido) {
		this.offeredItem = Ref.create(itemOfrecido);
	}

	
	public Item getRequestItem() {
		return requestItem.get();
	}

	public void setRequestItem(Item itemSolicitado) {
		this.requestItem = Ref.create(itemSolicitado);
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detalle) {
		this.detail = detalle;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public Usuario getOfferedUser() {
		return offeredUser.get();
	}

	public void setOfferedUser(Usuario offeredUser) {
		this.offeredUser = Ref.create(offeredUser);
	}

	public Usuario getRequestUser() {
		return requestUser.get();
	}

	public void setRequestUser(Usuario requestUser) {
		this.requestUser = Ref.create(requestUser);
	}
	
	/*public Solicitud(String detail, Item requestItem, Item offeredItem) {
		this.setDetail(detail);
		this.setRequestItem(requestItem);
		this.setOfferedItem(offeredItem);
	}*/
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Solicitud [id=" + id + ", detalle=" + detail + "]";
	}
	
	public void setAcepted() {
		this.setState(Solicitud.ACEPTED);
	}
	
	public void setRefused() {
		this.setState(Solicitud.REFUSED);
	}
	
	public Solicitud() {
	}

	public static final int ACEPTED = 1; 
	
	public static final int REFUSED = -1;
	
	public static final int PENDING = 0;
}
