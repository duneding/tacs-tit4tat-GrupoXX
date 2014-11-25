package com.utn.tacs.tit4tat.dao;

import java.util.List;

import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.model.Usuario;

public interface SolicitudDao extends GenericDao<Solicitud, Long> {

	public List<Solicitud> getSolicitudesPendientes();
	
	public List<Solicitud> getSolicitudByUser(Usuario usuario);
}
