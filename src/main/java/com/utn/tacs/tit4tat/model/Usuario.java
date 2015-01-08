package com.utn.tacs.tit4tat.model;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.utn.tacs.tit4tat.objectify.Identifiable;

@Entity
public class Usuario implements Identifiable, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String name;

	//Cuando el scope es facebook no tiene contraseña. Se utiliza la autenticacion misma de FB
	//Cuando tiene contraseña son usuarios externos para el uso de la API de Tit4Tat
	private String password; 
	
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
	
	public Usuario() {
	}
	
	public Usuario(String name) {
		this.setName(name);
	}

	 @Override
	 public String toString() {
	 return "Usuario [id=" + id + ", name=" + name + ",password=" + password + "]";
	 }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}