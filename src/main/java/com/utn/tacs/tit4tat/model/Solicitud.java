package com.utn.tacs.tit4tat.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.utn.tacs.tit4tat.objectify.Identifiable;

@Entity
public class Solicitud implements Identifiable {

	@Id
	private Long id;
	private String detail;
	
	private Item offeredItem;
	
	private Item requestItem;
	
	private int state;
	
	public Item getOfferedItem() {
		return offeredItem;
	}

	public void setOfferedItem(Item itemOfrecido) {
		this.offeredItem = itemOfrecido;
	}

	
	public Item getRequestItem() {
		return requestItem;
	}

	public void setRequestItem(Item itemSolicitado) {
		this.requestItem = itemSolicitado;
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
