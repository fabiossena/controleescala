package com.packageIxia.sistemaControleEscala.models;

import javax.validation.constraints.NotEmpty;

import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;

public class LoginPage {

    @NotEmpty(message="Preencha o campo matricula")
    private String matricula;

    @NotEmpty(message="Preencha o campo senha")
    private String senha;

    private Funcao funcao;

    public String getMatricula()
    {
        return this.matricula;
    }

    public void setMatricula(String matricula)
    {
        this.matricula = matricula;
    }

    public String getSenha()
    {
        return this.senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
}