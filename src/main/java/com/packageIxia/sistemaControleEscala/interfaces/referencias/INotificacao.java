package com.packageIxia.sistemaControleEscala.interfaces.referencias;

import java.util.List;

import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;

public interface INotificacao {

	String save(Notificacao notificacao);

	List<Notificacao> findAndAbrirAllByUsuarioId(long usuarioId, boolean ler);

	List<Notificacao> findAllByUsuarioId(long usuarioId);

	Notificacao findById(long usuarioId);

	Notificacao findByIdAndUsuarioId(long id, long usuarioId);

}