package com.packageIxia.SistemaControleEscala.Models;

import javax.validation.constraints.NotEmpty;

import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

public class UsuarioCadastroPage {
	
	private Usuario usuario;

    @NotEmpty(message="Preencha o campo repetir senha")
    private String repetirSenha;
	  
	public UsuarioCadastroPage(Usuario usuario) {
	  this.usuario = usuario;
	  if (usuario == null) {
		return; 
	  }
	  
	  this.repetirSenha = usuario.getSenha();
	}

	public Usuario getUsuario()
	{
	    return this.usuario;
	}
	
	public void setUsuario(Usuario usuario)
	{
	    this.usuario = usuario;
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
