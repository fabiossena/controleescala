package com.packageIxia.SistemaControleEscala.Services.Usuario;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.packageIxia.SistemaControleEscala.Daos.UsuarioDao;
import com.packageIxia.SistemaControleEscala.Daos.UsuarioEmailPrimeiroAcessoDao;
import com.packageIxia.SistemaControleEscala.Helper.Utilities;
import com.packageIxia.SistemaControleEscala.Models.UsuarioEmailPrimeiroAcesso;
import com.packageIxia.SistemaControleEscala.Models.Referencias.FuncaoEnum;

@Service
public class UsuarioConviteService {
	
	private UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao;
	private UsuarioDao usuarioDao;

	public UsuarioConviteService(
			UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao,
			UsuarioDao usuarioDao) {
		this.usuarioEmailPrimeiroAcessoDao = usuarioEmailPrimeiroAcessoDao;
		this.usuarioDao = usuarioDao;
	}

	public List<UsuarioEmailPrimeiroAcesso> findAll() {
		List<UsuarioEmailPrimeiroAcesso> usuarioAcesso = Utilities.toList(this.usuarioEmailPrimeiroAcessoDao.findAll());
		usuarioAcesso.stream().forEach(x -> x.setFuncao(FuncaoEnum.GetFuncaoFromId(x.getFuncaoId())));
		return usuarioAcesso.stream().sorted(Comparator.comparing(UsuarioEmailPrimeiroAcesso::getId).reversed()).collect(Collectors.toList());
	}

	public String save(UsuarioEmailPrimeiroAcesso usuarioConvite) {
		
		if (Strings.isBlank(usuarioConvite.getMatricula().trim())) {
			return "Preencha o campo matrícula";
		}

		if (Strings.isBlank(usuarioConvite.getEmail().trim())) {
			return "Preencha o campo e-mail";
		}

		if (usuarioConvite.getFuncaoId() == 0) {
			return "Preencha o campo função";
		}
		
		UsuarioEmailPrimeiroAcesso usuarioConviteExistente = this.usuarioEmailPrimeiroAcessoDao.findByMatricula(usuarioConvite.getMatricula()); 
		if (usuarioConviteExistente != null && usuarioConviteExistente.getId() != usuarioConvite.getId()) {
			return "Matrícula já utilizada";
		}
		
		if (usuarioConviteExistente != null && usuarioConviteExistente.isAceito()) {
			return "Convite já aceito";
		}
		
		if (this.usuarioDao.existsByMatricula(usuarioConvite.getMatricula())) {
			return "Já existe um usuário cadastrado com esta matrícula";
		}

		if (this.usuarioDao.existsByEmail(usuarioConvite.getEmail())) {
			return "Já existe um usuário cadastrado com este e-mail";
		}
		
		// todo: enviar e-mail
		
		this.usuarioEmailPrimeiroAcessoDao.save(usuarioConvite);
		
		return "";
	}
}
