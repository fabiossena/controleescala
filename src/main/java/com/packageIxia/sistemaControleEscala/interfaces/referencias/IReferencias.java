package com.packageIxia.sistemaControleEscala.interfaces.referencias;

import java.util.List;

import com.packageIxia.sistemaControleEscala.models.referencias.Banco;
import com.packageIxia.sistemaControleEscala.models.referencias.Cidade;
import com.packageIxia.sistemaControleEscala.models.referencias.DadoGenerico;
import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemana;
import com.packageIxia.sistemaControleEscala.models.referencias.Estado;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;
import com.packageIxia.sistemaControleEscala.models.referencias.MotivoAusencia;
import com.packageIxia.sistemaControleEscala.models.referencias.Pais;
import com.packageIxia.sistemaControleEscala.models.referencias.TipoApontamentoHoras;

public interface IReferencias {

	List<Banco> getBancos();

	List<Funcao> getFuncoes();

	List<Funcao> getFuncoes(int tipo);

	List<Pais> getPaises();

	List<Estado> getEstado(int idPais);

	List<Estado> getEstados();

	List<Cidade> getCidade(int idEstado);

	List<Cidade> getCidades();

	List<DadoGenerico> getPeriodos();

	List<DiaSemana> getDiasSemana();

	List<MotivoAusencia> getMotivosAusencia(int tipo);

	List<MotivoAusencia> getMotivosAusencia();

	List<TipoApontamentoHoras> getTipoApontamentoHoras();

	List<DadoGenerico> getPrioridades();

}