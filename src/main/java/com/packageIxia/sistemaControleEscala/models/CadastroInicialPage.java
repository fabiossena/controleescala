package com.packageIxia.sistemaControleEscala.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class CadastroInicialPage {

    @NotEmpty(message="Preencha o campo e-mail")
    @Pattern(regexp="^(.+)@(.+)$", message="Digite um e-mail válido")
    private String email;

    @NotEmpty(message="Preencha o campo matrícula")
    private String matricula;

    @NotEmpty(message="Preencha o campo primeiro nome")
    private String primeiroNome;

    @NotEmpty(message="Preencha o campo sobrenome")
    private String sobrenome;
    
    @NotEmpty(message="Preencha o campo senha")
    @Length(min = 6, message = "O campo senha deve ter no mínimo 6 caracteres")
    private String senha;
    
    @NotEmpty(message="Preencha o campo repetir senha")
    private String repetirSenha;

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getMatricula()
    {
        return this.matricula;
    }

    public void setMatricula(String matricula)
    {
        this.matricula = matricula;
    }
    
    public String getPrimeiroNome()
    {
        return this.primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome)
    {
        this.primeiroNome = primeiroNome;
    }
    
    public String getSobrenome()
    {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome)
    {
        this.sobrenome = sobrenome;
    }


    public String getSenha()
    {
        return this.senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

    public String getRepetirSenha()
    {
        return this.repetirSenha;
    }

    public void setRepetirSenha(String repetirSenha)
    {
        this.repetirSenha = repetirSenha;
    }
    
}
