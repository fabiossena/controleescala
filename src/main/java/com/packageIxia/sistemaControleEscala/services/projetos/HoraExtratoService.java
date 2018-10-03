package com.packageIxia.sistemaControleEscala.services.projetos;

import java.util.List;

import org.springframework.stereotype.Service;

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
		return horaExtratoDao.findAllByUsuarioId(usuarioId);
	}
	
	@Override
	public List<HoraExtrato> findAllByUsuarioIdAndData(Long usuarioId, int ano, int mes) {
		return horaExtratoDao.findAllByUsuarioId(usuarioId);
	}

	@Override
	public String save(HoraExtrato horaExtrato) {
		horaExtratoDao.save(horaExtrato); 
		return "";
	}

}
