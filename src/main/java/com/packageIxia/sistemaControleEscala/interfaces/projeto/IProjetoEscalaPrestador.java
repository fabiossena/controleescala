package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestadorDiaHoraTrabalho;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public interface IProjetoEscalaPrestador {

	List<Usuario> findAllPrestadoresByProjetoEscalaId(long projetoEscalaId);

	List<ProjetoEscalaPrestador> findAllByProjetoEscalaId(long projetoEscalaId);

	List<Usuario> findAllByProjetoEscalaIdExceptUsuarioId(long projetoEscalaId, long usuarioId);

	ProjetoEscalaPrestador findByProjetoEscalaIdAndPrestadorIdAndExcluido(long projetoEscalaId, long prestadorId);

	ProjetoEscalaPrestador findById(long id);

	String save(ProjetoEscalaPrestador prestador);

	String delete(long prestadorId);

	List<ProjetoEscalaPrestador> findAllProjetosByPrestadorId(long id, boolean trazerInformacaoFolga,
			boolean trazerInformacaoStatus, boolean trazerStatusReal, boolean trazerSomenteAtivos);

	List<ProjetoEscalaPrestador> findAllProjetosByPrestadorId(long id, boolean trazerInformacaoFolga,
			boolean trazerInformacaoStatus, boolean trazerStatusReal, boolean trazerSomenteAtivos,
			boolean trazerSomentePendentes, boolean trazerSomenteDataAtiva, boolean trazerSomenteInformacoesBasicas);

	String aceitePrestador(Usuario usuarioLogado, long projetoEscalaPrestadorId, int statusAceite, String motivo);

	boolean existsByPrestadorId(long prestadorId);

	boolean existsByProjetoEscalaId(long projetoEscalaId);

	List<ProjetoEscalaPrestador> findAllByProjetoId(long projetoId);

	List<ProjetoEscalaPrestador> findAllByPrestadorId(long prestadorId);

	List<ProjetoEscalaPrestador> findAllByRamalIntegracaoRobo(String ramalRobo);
	
	String convertoToJson(List<ProjetoEscalaPrestadorDiaHoraTrabalho> diasHorasTrabalho);

}