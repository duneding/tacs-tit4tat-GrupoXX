package com.utn.tacs.tit4tat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.ItemDao;
import com.utn.tacs.tit4tat.dao.SolicitudDao;
import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.service.SolicitudService;

@Service(value="solicitudService")
@Scope(value="prototype")
public class SolicitudServiceImpl implements SolicitudService {

	@Autowired
	private SolicitudDao solicitudDao;
	
	@Autowired
	private ItemDao itemDao;

	@Override
	public Solicitud saveSolicitud(Solicitud solicitud) {
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
			this.itemDao.delete(solicitud.getRequestItem());
			this.itemDao.delete(solicitud.getOfferedItem());
			
			this.solicitudDao.delete(solicitud);
//			solicitud.setAcepted();
		} else if(state.equalsIgnoreCase("refused")) {
			solicitud.setRefused();
			this.updateSolicitud(solicitud);
		} else {
			throw new RuntimeException("Invalid state"); 
		}
	}
	
	@Override
	public List<Solicitud> getSolicitudesPendientes() {
		return this.solicitudDao.getSolicitudesPendientes();
	}

	@Override
	public List<Solicitud> getSolicitudesByUser(Usuario usuario) {
		return this.solicitudDao.getSolicitudByUser(usuario);
	}
}
