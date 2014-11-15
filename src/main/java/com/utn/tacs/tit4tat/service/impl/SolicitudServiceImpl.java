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
//		Long id = this.solicitudDao.save(solicitud);
//		solicitud.setId(id);
		this.solicitudDao.save(solicitud);
		return solicitud;
	}

	@Override
	public void deleteSolicitud(Solicitud solicitud) {
		this.solicitudDao.delete(solicitud);
	}

	@Override
	public List<Solicitud> getSolicitudes() {
		return this.solicitudDao.findAll();
	}

	@Override
	public Solicitud getSolicitudesById(Long id) {
		return this.solicitudDao.getById(id);
	}

	@Override
	public void updateSolicitud(Solicitud solicitud) {
		this.solicitudDao.saveOrUpdate(solicitud);
	}

	@Override
	public void changeStateOfSolicitud(String permuteId, String state) {
		Solicitud solicitud = this.getSolicitudesById(Long.parseLong(permuteId));
		
		if (state.equalsIgnoreCase("acepted")) {
			solicitud.setAcepted();
		} else if(state.equalsIgnoreCase("refused")) {
			solicitud.setRefused();
		} else {
			throw new RuntimeException("Invalid state"); 
		}
		
		this.updateSolicitud(solicitud);
	}
	
	@Override
	public List<Solicitud> getSolicitudesPendientes() {
		return this.solicitudDao.getSolicitudesPendientes();
	}
}
