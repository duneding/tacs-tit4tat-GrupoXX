package com.utn.tacs.tit4tat.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="Solicitudes")
public class Solicitud implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	private String detalle;

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
	
	protected Solicitud() {
	}
	
	public Solicitud(String detalle) {
		this.setDetalle(detalle);
	}

	@Override
	public String toString() {
		return "Solicitud [id=" + id + ", detalle=" + detalle + "]";
	}
}
