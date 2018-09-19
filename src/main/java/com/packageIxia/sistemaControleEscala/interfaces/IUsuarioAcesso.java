package com.packageIxia.sistemaControleEscala.interfaces;

import javax.servlet.http.HttpSession;

import com.packageIxia.sistemaControleEscala.models.CadastroInicialPage;
import com.packageIxia.sistemaControleEscala.models.LoginPage;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

public interface IUsuarioAcesso {

	String efetuarLoginUsuario(LoginPage login, HttpSession session);

	String LoginUsuario(HttpSession session, Usuario usuario);

	String insertUsuarioCadastroInicial(CadastroInicialPage cadastro, HttpSession session);

	String validateLoginUsuario(LoginPage login, Usuario usuario);

}