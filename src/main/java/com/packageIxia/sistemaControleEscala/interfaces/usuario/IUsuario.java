package com.packageIxia.sistemaControleEscala.interfaces.usuario;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

public interface IUsuario {

	String saveUsuario(Usuario usuario, HttpSession session, boolean isCadastroUsuarioPage);

	String validaSeguranca(Usuario usuario, HttpSession session, boolean isCadastroUsuarioPage);

	List<Usuario> findAllByUsuarioLogado(Usuario usuarioLogado);

	Usuario findByUsuarioId(long usuarioId);

	List<Usuario> findByPerfilAcessoId(int id);

	String delete(long id);

	List<Usuario> findByPerfilAcessoId(int[] ids);

}