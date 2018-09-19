package com.packageIxia.sistemaControleEscala.services.referencias;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.referencias.NotificacaoDao;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.INotificacao;
import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;

@Service
public class NotificacaoService implements INotificacao {
	private NotificacaoDao notificacaoDao;

	public NotificacaoService(NotificacaoDao notificacaoDao) {
		this.notificacaoDao = notificacaoDao;
	}
	
	@Override
	public String save(Notificacao notificacao) {
		this.notificacaoDao.save(notificacao);
		return "";
	}
	
	@Override
	public List<Notificacao> findAndAbrirAllByUsuarioId(long usuarioId, boolean ler) {
		List<Notificacao> notificacoes = this.notificacaoDao.findAllByUsuarioId(usuarioId).stream().filter(x->x.getLeitura() == null).unordered().collect(Collectors.toList());
		if (ler) {
			for (Notificacao notificacao : notificacoes) {
				if (notificacao.getAberta() != null) { 
					notificacao.setAberta(LocalDateTime.now());
					this.notificacaoDao.save(notificacao);
				}				
			}
		}
		
		return notificacoes;
	}
	
	@Override
	public List<Notificacao> findAllByUsuarioId(long usuarioId) {
		return this.notificacaoDao.findAllByUsuarioId(usuarioId);
	}
	
	@Override
	public Notificacao findById(long usuarioId) {
		return this.notificacaoDao.findById(usuarioId).orElseGet(null);
	}
	
	@Override
	public Notificacao findByIdAndUsuarioId(long id, long usuarioId) {
		return this.notificacaoDao.findAllByIdAndUsuarioId(id, usuarioId);
	}
}
