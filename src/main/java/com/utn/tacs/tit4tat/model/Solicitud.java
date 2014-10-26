package com.utn.tacs.tit4tat.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name="Solicitudes")
public class Solicitud implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	private String detail;
	
	@ManyToOne
	@JoinColumn(name="offered_item_id")
	private Item offeredItem;
	
	@ManyToOne
	@JoinColumn(name="request_item_id")
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

	
	public Long getId() {
		return id;
	}

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
	
	@Transient
	public static final int ACEPTED = 1; 
	
	@Transient
	public static final int REFUSED = -1;
	
	@Transient
	public static final int PENDING = 0;
}
