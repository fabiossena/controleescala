package com.packageIxia.sistemaControleEscala.models;

import javax.validation.constraints.NotEmpty;

public class LoginPage {

    @NotEmpty(message="Preencha o campo matricula")
    private String matricula;

    @NotEmpty(message="Preencha o campo senha")
    private String senha;

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
}