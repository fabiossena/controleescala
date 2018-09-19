package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;

@Service
public interface IProjetoEscala {

	List<ProjetoEscala> findAllByProjetoId(long projetoId);

	ProjetoEscala findById(long id);

	String saveEscala(ProjetoEscala escala);

	boolean existsByProjetoId(long projetoId);

	String delete(long escalaId);

	boolean existsByMonitorId(long monitorId);

	List<ProjetoEscala> findAllByMonitorId(long monitorId);

	List<ProjetoEscala> findAllByPrestadorId(long id);

	List<ProjetoEscala> findAllByPrestadorId(long id, boolean somenteTipoApontamentoSistema);

	List<ProjetoEscala> findAllByPrestadorIdExceptPrestadorEscalaId(long usuarioId, long prestadorEscalaId);

	List<ProjetoEscala> findAllByPermissao();

	List<ProjetoEscala> findAllByPermissao(boolean somenteTipoApontamentoSistema);

}