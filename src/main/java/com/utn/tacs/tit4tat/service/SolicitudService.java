package com.utn.tacs.tit4tat.service;

import java.util.List;

import com.utn.tacs.tit4tat.model.Solicitud;

public interface SolicitudService {

	public Solicitud saveSolicitud(Solicitud solicitud);

	public void deleteSolicitud(Solicitud solicitud);

	public List<Solicitud> getSolicitudes();

	public Solicitud getSolicitudesById(Long id);

	public void updateSolicitud(Solicitud solicitud);

	public void changeStateOfSolicitud(String permuteId, String state);
}
