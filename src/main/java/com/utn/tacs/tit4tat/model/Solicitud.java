package com.utn.tacs.tit4tat.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name="Solicitudes")
public class Solicitud implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	private String detalle;
	private int state;
	

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Solicitud(String detalle) {
		this.setDetalle(detalle);
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Solicitud [id=" + id + ", detalle=" + detalle + "]";
	}
	
	protected Solicitud() {
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
