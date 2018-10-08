package com.packageIxia.sistemaControleEscala.unitTests.usuario;

import com.packageIxia.sistemaControleEscala.models.LoginPage;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

public class UsuarioConfiguraDados {

	public Usuario getUsuarioTestBase() {
		Usuario entity = new Usuario();
		entity.setMatricula("1234");
		entity.setSenha("123456");
		Funcao f = new Funcao();
		f.setNome("Atendente");
		f.setId(1);
		entity.setFuncao(f);
		entity.setEmail("test1@gmail.com");
		entity.setAtivo(true);
		entity.setAtivo(true);
		return entity;
	}

	public LoginPage getDadosBaseLoginPage(String matricula, String senha) {
		LoginPage loginPage = new LoginPage();
		loginPage.setMatricula(matricula);
		loginPage.setSenha(senha);
		return loginPage;
	}

}
