package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaSolicitacao;

@Service
public interface IAusenciaSolicitacao {

	List<AusenciaSolicitacao> findAll();

	List<AusenciaSolicitacao> findAll(boolean isSomenteComAcesso);

	String save(AusenciaSolicitacao solicitacao);

	String delete(long ausenciaSolicitacaoId);

	AusenciaSolicitacao findById(long id);

	String aceita(long id, boolean aceita, String motivo);

	List<AusenciaSolicitacao> findAllByProjetoId(int year, int mount, long projetoId, int aceito);

	List<AusenciaSolicitacao> findAllByProjetoEscalaId(int year, int mount, long projetoEscalaId, int aceito);

	boolean existsByUsuarioId(long prestadorId);

	List<AusenciaSolicitacao> findAllByPrestadorId(int year, int mount, long prestadorId);

}