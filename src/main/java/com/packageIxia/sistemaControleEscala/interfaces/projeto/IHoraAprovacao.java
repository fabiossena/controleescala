package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraAprovacao;

@Service
public interface IHoraAprovacao {

	List<HoraAprovacao> findAll() throws Exception;

	List<HoraAprovacao> findAll(int ano, int mes) throws Exception;

	HoraAprovacao findByDateAndPrestadorId(long prestadorId, int ano, int mes) throws Exception;

	HoraAprovacao findLastByPrestadorIdOrInsert(long prestadorId, int ano, int mes) throws Exception;

	HoraAprovacao findById(Long id);

	String save(HoraAprovacao horaAprovacao);

	String delete(long id);

	void updateAprovacaoReset(long id, double horas, double valor);

	void updateAprovacao(boolean aprovar, long id, String motivo, HoraAprovacao aprovacaoHora) throws Exception;

	void uploadNota(long id, String arquivoNota);

	void updateCsvGerado(List<Long> itens);

}