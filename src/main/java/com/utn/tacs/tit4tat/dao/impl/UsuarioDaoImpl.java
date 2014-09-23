package com.utn.tacs.tit4tat.dao.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.UsuarioDao;
import com.utn.tacs.tit4tat.model.Usuario;

@Service("usuarioDao")
@Scope("Singleton")
public class UsuarioDaoImpl extends GenericDaoImpl<Usuario, Long> implements
		UsuarioDao {

}
