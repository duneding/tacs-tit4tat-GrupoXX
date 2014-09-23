package com.utn.tacs.tit4tat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.SolicitudDao;
import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.service.SolicitudService;

@Service(value="solicitudService")
@Scope(value="prototype")
public class SolicitudServiceImpl implements SolicitudService {

	@Autowired
	private SolicitudDao solicitudDao;

	@Override
	public Solicitud saveSolicitud(Solicitud solicitud) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSolicitud(Solicitud solicitud) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Solicitud> getSolicitudes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solicitud getSolicitudesById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSolicitud(Solicitud solicitud) {
		// TODO Auto-generated method stub
		
	}
}
