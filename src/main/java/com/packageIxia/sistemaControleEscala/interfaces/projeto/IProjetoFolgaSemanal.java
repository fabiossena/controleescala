package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoFolgaSemanal;

@Service
public interface IProjetoFolgaSemanal {

	List<ProjetoFolgaSemanal> findAllByProjetoEscalaPrestadorId(long projetoEscalaPrestadorId);

	String validaFolgasSemanais(List<ProjetoFolgaSemanal> folgasSemanais, ProjetoEscala escalaPrestador,
			List<ProjetoEscala> escalas);

	void saveFolgasSemanais(List<ProjetoFolgaSemanal> folgasSemanais, Long projetoEscalaPrestadorId);

	String delete(long id);

	long preSaveFolgaSemanal(List<ProjetoFolgaSemanal> folgasSemanais, ProjetoFolgaSemanal folgaSemanal);

	int preDeleteFolgaSemanal(List<ProjetoFolgaSemanal> folgasSemanais, int id);

	String findEscalaFolgaSemanal(long id);

	String deleteByProjetoEscalaPrestadorId(long id);

}