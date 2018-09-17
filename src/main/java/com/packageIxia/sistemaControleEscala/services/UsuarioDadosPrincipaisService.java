package com.packageIxia.sistemaControleEscala.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.usuario.UsuarioDao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class UsuarioDadosPrincipaisService {

	@Autowired
	private UsuarioDao usuarioDao;
	
	public String validateUsuario(Usuario usuario) {

		if (usuario == null) {
			return "Dados inválidos";
		}

		if (usuario.getCpf().trim().isEmpty()) {
			return "Preencha o campo cpf";
		}

		if (usuario.getTelefone().trim().isEmpty()) {
			return "Preencha o campo telefone";
		}
		
		Usuario usuarioBase = usuarioDao.findByEmail(usuario.getEmail());		
		if (usuarioBase != null && usuarioBase.getId() != usuario.getId()) {
			return "E-mail já usado por outro  usuário";
		}	
		
		usuarioBase = usuarioDao.findByCpf(usuario.getCpf());
		if (usuarioBase != null && usuarioBase.getId() != usuario.getId()) {
			return "Cpf já usado por outro  usuário";
		}
		
		usuarioBase = usuarioDao.findByMatricula(usuario.getMatricula());
		if (usuarioBase != null && usuarioBase.getId() != usuario.getId()) {
			return "Matricula já usada por outro  usuário";
		}
				
		return "";
	}

}
