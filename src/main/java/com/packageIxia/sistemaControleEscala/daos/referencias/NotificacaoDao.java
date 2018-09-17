package com.packageIxia.sistemaControleEscala.daos.referencias;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;

public interface NotificacaoDao extends CrudRepository<Notificacao, Long> {

	List<Notificacao> findAllByUsuarioId(long usuarioId);

	Notificacao findAllByIdAndUsuarioId(long id, long usuarioId);

}
