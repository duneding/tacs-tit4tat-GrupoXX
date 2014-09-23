package com.utn.tacs.tit4tat.dao.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.SolicitudDao;
import com.utn.tacs.tit4tat.model.Solicitud;

@Service("solicitudDao")
@Scope("Singleton")
public class SolicitudDaoImpl extends GenericDaoImpl<Solicitud, Long> implements
		SolicitudDao {

}
