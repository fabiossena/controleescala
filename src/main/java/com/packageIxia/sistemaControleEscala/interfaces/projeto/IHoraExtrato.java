package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;

import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.models.projeto.HoraExtrato;

@Service
public interface IHoraExtrato {
	List<HoraExtrato> findAllByUsuarioId(Long usuarioId);
	List<HoraExtrato> findAllByUsuarioIdAndData(Long usuarioId, int ano, int mes);
	HoraExtrato findLastByUsuarioId(Long usuarioId);
	String save(HoraExtrato horaExtrato);
}
