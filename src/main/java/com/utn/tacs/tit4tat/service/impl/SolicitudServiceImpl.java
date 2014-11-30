package com.utn.tacs.tit4tat.service.impl;

import java.util.ArrayList;
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
			this.updateSolicitudAceptada(solicitud);			
		} else if(state.equalsIgnoreCase("refused")) {
			this.updateSolicitudRechazada(solicitud);
		} else {
			throw new RuntimeException("Invalid state"); 
		}
	}

	private void updateSolicitudRechazada(Solicitud solicitud) {
		solicitud.setRefused();
		this.updateSolicitud(solicitud);
	}

	private void updateSolicitudAceptada(Solicitud solicitud) {
		Usuario requestUser = solicitud.getRequestItem().getOwner();
		Usuario offeredUser = solicitud.getOfferedItem().getOwner();
		
		solicitud.getRequestItem().setOwner(offeredUser);
		solicitud.getOfferedItem().setOwner(requestUser);
		
		solicitud.setAcepted();
		this.updateSolicitud(solicitud);
	}
	
	@Override
	public List<Solicitud> getSolicitudesPendientesByUser(Usuario usuario) {
		List<Solicitud> solicitudes = this.solicitudDao.getSolicitudByUser(usuario);
		List<Solicitud> solicitudesPendientes = new ArrayList<Solicitud>();
		
		
		if (solicitudes != null) {
			for (Solicitud solicitud : solicitudes) {
				if (solicitud.isPending())
					solicitudesPendientes.add(solicitud);
			}
		}
		
		return solicitudesPendientes;
	}

	@Override
	public List<Solicitud> getSolicitudesByUser(Usuario usuario) {
		return this.solicitudDao.getSolicitudByUser(usuario);
	}
}
