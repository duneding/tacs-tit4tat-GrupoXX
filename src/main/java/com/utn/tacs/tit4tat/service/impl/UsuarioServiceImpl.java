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
		Long id = this.usuarioDao.save(usuario);
		usuario.setId(id);
		return usuario;
	}

	@Override
	public void deleteUsuario(Usuario usuario) {
		this.usuarioDao.delete(usuario);
	}

	@Override
	public List<Usuario> getUsuarios() {
		return this.usuarioDao.findAll();
	}

	@Override
	public Usuario getUsuariosById(Long id) {
		return this.getUsuariosById(id);
	}

	@Override
	public void updateUsuario(Usuario usuario) {
		this.usuarioDao.saveOrUpdate(usuario);
	}
}
