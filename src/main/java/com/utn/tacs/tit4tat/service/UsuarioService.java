package com.utn.tacs.tit4tat.service;

import java.util.List;

import com.utn.tacs.tit4tat.model.Usuario;

public interface UsuarioService {

	public Usuario saveUsuario(Usuario usuario);

	public void deleteUsuario(Usuario usuario);

	public List<Usuario> getUsuarios();

	public Usuario getUsuariosById(Long id);

	public void updateUsuario(Usuario usuario);
}
