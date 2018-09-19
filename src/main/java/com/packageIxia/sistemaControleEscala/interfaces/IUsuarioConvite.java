package com.packageIxia.sistemaControleEscala.interfaces;

import java.util.List;

import com.packageIxia.sistemaControleEscala.models.UsuarioEmailPrimeiroAcesso;

public interface IUsuarioConvite {

	List<UsuarioEmailPrimeiroAcesso> findAll();

	String save(UsuarioEmailPrimeiroAcesso usuarioConvite);

}