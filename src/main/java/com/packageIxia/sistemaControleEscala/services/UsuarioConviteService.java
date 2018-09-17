package com.packageIxia.sistemaControleEscala.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.UsuarioEmailPrimeiroAcessoDao;
import com.packageIxia.sistemaControleEscala.daos.usuario.UsuarioDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.UsuarioEmailPrimeiroAcesso;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

@Service
public class UsuarioConviteService {
	
	private UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao;
	private UsuarioDao usuarioDao;
	private HttpServletRequest request;

	public UsuarioConviteService(
			HttpServletRequest request, 
			UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao,
			UsuarioDao usuarioDao) {
		this.usuarioEmailPrimeiroAcessoDao = usuarioEmailPrimeiroAcessoDao;
		this.usuarioDao = usuarioDao;
		this.request = request;
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

		usuarioConvite.setMatricula(usuarioConvite.getMatricula().trim());
		
		if (Strings.isBlank(usuarioConvite.getEmail().trim())) {
			return "Preencha o campo e-mail";
		}
		
		usuarioConvite.setEmail(usuarioConvite.getEmail().trim());

		try {
			if (InternetAddress.parse(usuarioConvite.getEmail()).length == 0) {
				return "Digite um e-mail válido";
			}
		} catch (AddressException e) {
			return "Digite um e-mail válido";
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
		
		this.usuarioEmailPrimeiroAcessoDao.save(usuarioConvite);

		try {
			new EnvioEmail(usuarioConvite.getEmail(), usuarioConvite.getMatricula(), request.getRequestURL().toString().replace("convite", "")).start();
		} catch (Exception e) {
			System.out.println("erro envio e-mail");
			System.out.println(e.toString());
		}
		
		return "";
	}
}