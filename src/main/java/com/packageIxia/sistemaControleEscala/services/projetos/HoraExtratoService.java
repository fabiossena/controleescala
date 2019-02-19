package com.packageIxia.sistemaControleEscala.services.projetos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import com.amazonaws.jmespath.Comparator;
import com.packageIxia.sistemaControleEscala.daos.projeto.HoraExtratoDao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IHoraExtrato;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraExtrato;

@Service
public class HoraExtratoService implements IHoraExtrato {

	private HoraExtratoDao horaExtratoDao;

	public HoraExtratoService(HoraExtratoDao horaExtratoDao) {
		this.horaExtratoDao = horaExtratoDao;
	}
	
	@Override
	public List<HoraExtrato> findAllByUsuarioId(Long usuarioId) {
		List<HoraExtrato> extratoOrder = horaExtratoDao.findAllByUsuarioId(usuarioId).stream().sorted(java.util.Comparator.comparing(HoraExtrato::getData)).collect(Collectors.toList());
		int minutos = 0;
		
		for (HoraExtrato horaExtrato : extratoOrder) {
			minutos += horaExtrato.getMinutosEntradaSaida();
			horaExtrato.setMinutosTotalDisponiveis(minutos);
		}
		
		return extratoOrder;
	}
	
	@Override
	public List<HoraExtrato> findAllByUsuarioIdAndData(Long usuarioId, int ano, int mes) {
		return this.findAllByUsuarioId(usuarioId).stream().filter(x->x.getData().getYear() == ano && x.getData().getMonthValue() == mes).collect(Collectors.toList());
	}
	
	@Override
	public HoraExtrato findLastByUsuarioId(Long usuarioId) {
		List<HoraExtrato> extrato = this.findAllByUsuarioId(usuarioId);
		return extrato.size() == 0 ? null : extrato.get(extrato.size()-1);
	}

	@Override
	public String save(HoraExtrato horaExtrato) {
		horaExtratoDao.save(horaExtrato); 
		return "";
	}

}
