package com.packageIxia.SistemaControleEscala.Services.Usuario;

import org.springframework.stereotype.Service;

import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

@Service
public class UsuarioDadosAcessoService {

	public String validateUsuario(Usuario usuario) {

		if (usuario == null) {
			return "Dados inválidos!";
		}
		
		if (!usuario.getRepetirSenha().equals(usuario.getSenha())) {
			return "As senhas digitadas estão diferentes!";
		}
		
		return "";
	}
}
