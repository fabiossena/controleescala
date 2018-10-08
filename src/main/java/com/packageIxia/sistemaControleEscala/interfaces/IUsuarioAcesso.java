package com.packageIxia.sistemaControleEscala.interfaces;

import com.packageIxia.sistemaControleEscala.models.CadastroInicialPage;
import com.packageIxia.sistemaControleEscala.models.LoginPage;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

public interface IUsuarioAcesso {

	String efetuarLoginUsuario(LoginPage login);

	String LoginUsuario(Usuario usuario);

	String insertUsuarioCadastroInicial(CadastroInicialPage cadastro);

	String validateLoginUsuario(LoginPage login, Usuario usuario);

}