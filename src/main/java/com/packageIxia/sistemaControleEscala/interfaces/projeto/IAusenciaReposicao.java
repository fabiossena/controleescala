package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaReposicao;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaSolicitacao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public interface IAusenciaReposicao {

	String save(AusenciaReposicao ausenciaReposicao);

	String delete(long ausenciaReposicaoId);

	long preSaveReposicoes(AusenciaReposicao ausenciaReposicao, AusenciaSolicitacao solicitacaoEditada);

	List<AusenciaReposicao> findAllByProjetoEscalaId(int anoAtual, int mesAtual, long escalaId, int aceito);

	List<AusenciaReposicao> findAllByProjetoId(int anoAtual, int mesAtual, long projetoId, int aceito);

	boolean existsByUsuarioTrocaId(long prestadorId);
	
	String validaReposicao(AusenciaSolicitacao solicitacao, Usuario usuario);

}