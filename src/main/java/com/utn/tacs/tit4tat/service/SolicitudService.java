package com.utn.tacs.tit4tat.service;

import java.util.List;

import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.model.Usuario;

public interface SolicitudService {

	public Solicitud saveSolicitud(Solicitud solicitud);

	public void deleteSolicitud(Solicitud solicitud);

	public List<Solicitud> getSolicitudes();
	
	public List<Solicitud> getSolicitudesPendientes();

	public Solicitud getSolicitudesById(Long id);

	public void updateSolicitud(Solicitud solicitud);

	public void changeStateOfSolicitud(String permuteId, String state);

	public List<Solicitud> getSolicitudesByUser(Usuario usuario);
}
