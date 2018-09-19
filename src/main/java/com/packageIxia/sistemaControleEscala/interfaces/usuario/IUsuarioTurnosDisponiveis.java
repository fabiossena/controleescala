package com.packageIxia.sistemaControleEscala.interfaces.usuario;

import java.util.List;

import com.packageIxia.sistemaControleEscala.models.usuario.FolgaSemanalPlanejadaUsuario;

public interface IUsuarioTurnosDisponiveis {

	List<FolgaSemanalPlanejadaUsuario> getFolgasSemanaisPlanejadasUsuario(long usuarioId);

	void saveFolgasSemanaisPlanejadasUsuario(List<FolgaSemanalPlanejadaUsuario> folgasSemanaisPlanejadas,
			Long usuarioId);

	int preSaveFolgaSemanalPlanejadaUsuario(List<FolgaSemanalPlanejadaUsuario> folgasSemanaisPlanejadas,
			FolgaSemanalPlanejadaUsuario folgaSemanalPlanejada);

	int preDeleteFolgaSemanalPlanejadaUsuario(List<FolgaSemanalPlanejadaUsuario> folgasSemanaisPlanejadas, int id);

	List<String> findEscalaFolgaSugerida(long id);

}