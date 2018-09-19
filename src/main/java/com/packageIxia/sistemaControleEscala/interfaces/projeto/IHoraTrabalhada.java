package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraTrabalhada;

@Service
public interface IHoraTrabalhada {

	List<HoraTrabalhada> findByHoraAprovacaoId(Long id);

	HoraTrabalhada findById(Long id);

	List<HoraTrabalhada> findByPrestadorId(Long id);

	HoraTrabalhada findLastByPrestadorId(long prestadorId) throws Exception;

	HoraTrabalhada findLastStartedByPrestadorId(long prestadorId) throws Exception;

	String save(long escalaId, int tipoAcao, boolean novoSomenteDataInicial, String motivoAcao) throws Exception;

	String setAndSave(HoraTrabalhada horaTrabalhada, HoraTrabalhada horaTrabalhadaAnterior);

	String save(HoraTrabalhada horaTrabalhada, HoraTrabalhada horaTrabalhadaAnterior);

	List<HoraTrabalhada> findByProjetoEscalaId(long id, int ano, int mes);

	void deleteAllByMonthYear(int monthValue, int year);

}