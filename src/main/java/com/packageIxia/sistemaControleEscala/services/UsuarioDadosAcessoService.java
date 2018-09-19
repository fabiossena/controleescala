package com.packageIxia.sistemaControleEscala.services;

import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.interfaces.IUsuarioDadosAcesso;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class UsuarioDadosAcessoService implements IUsuarioDadosAcesso {

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
