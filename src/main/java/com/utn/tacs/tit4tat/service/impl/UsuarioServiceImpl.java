package com.utn.tacs.tit4tat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.UsuarioDao;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.service.UsuarioService;

@Service(value="usuarioService")
@Scope(value="prototype")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public Usuario saveUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Usuario> getUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario getUsuariosById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}
}
