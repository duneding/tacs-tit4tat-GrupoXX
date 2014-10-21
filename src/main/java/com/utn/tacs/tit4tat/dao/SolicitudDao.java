package com.utn.tacs.tit4tat.dao;

import java.util.List;

import com.utn.tacs.tit4tat.model.Solicitud;

public interface SolicitudDao extends GenericDao<Solicitud, Long> {

	public List<Solicitud> getSolicitudesPendientes();
}
