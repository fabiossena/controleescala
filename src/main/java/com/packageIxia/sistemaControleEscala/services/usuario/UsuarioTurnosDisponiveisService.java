package com.packageIxia.sistemaControleEscala.services.usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.usuario.FolgaSemanalPlanejadaUsuarioDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.IReferencias;
import com.packageIxia.sistemaControleEscala.interfaces.usuario.IUsuario;
import com.packageIxia.sistemaControleEscala.interfaces.usuario.IUsuarioTurnosDisponiveis;
import com.packageIxia.sistemaControleEscala.models.referencias.DadoGenerico;
import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemana;
import com.packageIxia.sistemaControleEscala.models.referencias.MotivoAusencia;
import com.packageIxia.sistemaControleEscala.models.usuario.FolgaSemanalPlanejadaUsuario;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class UsuarioTurnosDisponiveisService implements IUsuarioTurnosDisponiveis {

    private FolgaSemanalPlanejadaUsuarioDao folgaSemanalPlanejadaUsuarioDao;
    private IReferencias referenciasService;
	private IUsuario usuarioService;
    
    @Autowired
	public UsuarioTurnosDisponiveisService(
    		FolgaSemanalPlanejadaUsuarioDao folgaSemanalPlanejadaUsuarioDao,
    		IUsuario usuarioService,
    		IReferencias referenciasService) {

    	this.folgaSemanalPlanejadaUsuarioDao = folgaSemanalPlanejadaUsuarioDao;
    	this.referenciasService = referenciasService;
    	this.usuarioService = usuarioService;
	}
	
	@Override
	public List<FolgaSemanalPlanejadaUsuario> getFolgasSemanaisPlanejadasUsuario(long usuarioId) {
		
		List<FolgaSemanalPlanejadaUsuario> list = Utilities.toList(this.folgaSemanalPlanejadaUsuarioDao.findAllByUsuarioId(usuarioId)); // new ArrayList<FolgaSemanalPlanejadaUsuario>();

		List<DiaSemana> diasSemanas = this.referenciasService.getDiasSemana();
		List<MotivoAusencia> motivos = this.referenciasService.getMotivosAusencia();
		for (FolgaSemanalPlanejadaUsuario item : list) {			
			item.setDiaSemana(diasSemanas.stream().filter(x->x.getId() == item.getDiaSemanaId()).findAny().orElse(null));
			item.setMotivo(motivos.stream().filter(x->x.getId() == item.getMotivoId()).findFirst().get());
		}
		
		return list;
	}
	
	@Override
	public void saveFolgasSemanaisPlanejadasUsuario(List<FolgaSemanalPlanejadaUsuario> folgasSemanaisPlanejadas, Long usuarioId) {		

		for (FolgaSemanalPlanejadaUsuario item : Utilities.toList(this.folgaSemanalPlanejadaUsuarioDao.findAllByUsuarioId(usuarioId))) {
			if (!folgasSemanaisPlanejadas.stream().anyMatch(x -> x.getId() == item.getId())){
				this.folgaSemanalPlanejadaUsuarioDao.delete(item);
			}
		}
		
		for (FolgaSemanalPlanejadaUsuario item : folgasSemanaisPlanejadas) {
    		item.setUsuarioId(usuarioId);
    		if (item.getId()<1) {
    			item.setId(0);
    			item.setMotivoId(item.getMotivoId());
    			item.setDiaSemanaId(item.getDiaSemanaId());
    		}
		}
    	
		this.folgaSemanalPlanejadaUsuarioDao.saveAll(folgasSemanaisPlanejadas);
	}

	@Override
	public int preSaveFolgaSemanalPlanejadaUsuario(List<FolgaSemanalPlanejadaUsuario> folgasSemanaisPlanejadas,
			FolgaSemanalPlanejadaUsuario folgaSemanalPlanejada) {
		int id = folgaSemanalPlanejada.getId();
		if (id == 0) {
			id = folgasSemanaisPlanejadas.stream().count() == 0 ? 0 : folgasSemanaisPlanejadas.stream().min(Comparator.comparing(FolgaSemanalPlanejadaUsuario::getId)).get().getId();
			id = id > 0 ? -1 : id - 1; // atribui o menor id encontrado encontrado para adicionar este item quando salvar o usuario
			folgaSemanalPlanejada.setId(id); 
			folgasSemanaisPlanejadas.add(0, folgaSemanalPlanejada);
		}
		else {
			for (int index = 0; index < folgasSemanaisPlanejadas.size(); index++) {
				if (folgasSemanaisPlanejadas.get(index).getId() == folgaSemanalPlanejada.getId()) {
					folgasSemanaisPlanejadas.get(index).setDiaSemana(folgaSemanalPlanejada.getDiaSemana());
					folgasSemanaisPlanejadas.get(index).setMotivo(folgaSemanalPlanejada.getMotivo());
					folgasSemanaisPlanejadas.get(index).setHoraInicio(folgaSemanalPlanejada.getHoraInicio());
					folgasSemanaisPlanejadas.get(index).setHoraFim(folgaSemanalPlanejada.getHoraFim());
					break;
				}
			}
		}
		
		return id;
	}


	@Override
	public int preDeleteFolgaSemanalPlanejadaUsuario(
			List<FolgaSemanalPlanejadaUsuario> folgasSemanaisPlanejadas,
			int id) {
		int index = 1;
		for (FolgaSemanalPlanejadaUsuario folga : folgasSemanaisPlanejadas) {
			if (folga.getId() == id) {
				break;
			}
			index++;
		}

		folgasSemanaisPlanejadas.remove(index-1);
		
		return id;
	}


	@Override
	public List<String> findEscalaFolgaSugerida(long id) {
		List<String> resultado = new ArrayList<String>();
		
		Usuario usuario = this.usuarioService.findByUsuarioId(id);
		String escalaSugerida = referenciasService.getPeriodos().stream().filter(x->x.getId() == usuario.getPeriodoDisponivelId()).findFirst().orElse(new DadoGenerico(0, "")).getNome();
		resultado.add(escalaSugerida == null || escalaSugerida == "" ? "" :  "Escala sugerida pelo prestador: " + escalaSugerida);
		
		String folgaSemanalSugerida = "";
		List<FolgaSemanalPlanejadaUsuario> list = this.getFolgasSemanaisPlanejadasUsuario(id);
		for (FolgaSemanalPlanejadaUsuario item : list) {			
			folgaSemanalSugerida += folgaSemanalSugerida == "" ? "<br />Folga semanal sugerida pelo prestador: <br />" : folgaSemanalSugerida  + "<br />";
			folgaSemanalSugerida += item.getDiaSemana().getNome() + " " + item.getHoraInicio() + " às " + item.getHoraFim() + " (" + item.getMotivo().getNome() + ")";
		}
		
		resultado.add(folgaSemanalSugerida);
		
		return resultado;
	}
}
