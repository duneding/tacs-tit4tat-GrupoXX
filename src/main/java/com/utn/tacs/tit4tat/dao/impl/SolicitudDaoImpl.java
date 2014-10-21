package com.utn.tacs.tit4tat.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.SolicitudDao;
import com.utn.tacs.tit4tat.model.Solicitud;

@Service("solicitudDao")
@Scope("singleton")
public class SolicitudDaoImpl extends GenericDaoImpl<Solicitud, Long> implements
		SolicitudDao {

	@Override
	public List<Solicitud> getSolicitudesPendientes() {
		String hql = "from Solicitud s where s.state = " + 0;
		
		@SuppressWarnings("unchecked")
		List<Solicitud> result = (List<Solicitud>) this.hibernateTemplate.find(hql);
		
		return result;
	}
}
