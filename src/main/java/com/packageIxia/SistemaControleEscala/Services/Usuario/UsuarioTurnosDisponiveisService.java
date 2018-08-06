package com.packageIxia.SistemaControleEscala.Services.Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.SistemaControleEscala.Daos.FolgaSemanalPlanejadaUsuarioDao;
import com.packageIxia.SistemaControleEscala.Helper.Utilities;
import com.packageIxia.SistemaControleEscala.Models.Referencias.DadoGenerico;
import com.packageIxia.SistemaControleEscala.Models.Referencias.DiaSemana;
import com.packageIxia.SistemaControleEscala.Models.Referencias.MotivoAusencia;
import com.packageIxia.SistemaControleEscala.Models.Usuario.FolgaSemanalPlanejadaUsuario;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;
import com.packageIxia.SistemaControleEscala.Services.ReferenciasService;

@Service
public class UsuarioTurnosDisponiveisService {

    private FolgaSemanalPlanejadaUsuarioDao folgaSemanalPlanejadaUsuarioDao;
    private ReferenciasService referenciasService;
	private UsuarioService usuarioService;
    
    @Autowired
	public UsuarioTurnosDisponiveisService(
    		FolgaSemanalPlanejadaUsuarioDao folgaSemanalPlanejadaUsuarioDao,
    		UsuarioService usuarioService,
    		ReferenciasService referenciasService) {

    	this.folgaSemanalPlanejadaUsuarioDao = folgaSemanalPlanejadaUsuarioDao;
    	this.referenciasService = referenciasService;
    	this.usuarioService = usuarioService;
	}
	
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

	public List<String> findEscalaFolgaSugerida(long id) {
		List<String> resultado = new ArrayList<String>();
		
		Usuario usuario = this.usuarioService.findByUsuarioId(id);
		String escalaSugerida = referenciasService.getPeriodos().stream().filter(x->x.getId() == usuario.getPeriodoDisponivelId()).findFirst().orElse(new DadoGenerico(0, "")).getNome();
		resultado.add(escalaSugerida == null || escalaSugerida == "" ? "" :  "Escala sugerida pelo prestador: " + escalaSugerida);
		
		String folgaSemanalSugerida = "";
		List<FolgaSemanalPlanejadaUsuario> list = this.getFolgasSemanaisPlanejadasUsuario(id);
		for (FolgaSemanalPlanejadaUsuario item : list) {			
			folgaSemanalSugerida += folgaSemanalSugerida == "" ? "Folga semanal sugerida pelo prestador: <br />" : folgaSemanalSugerida  + "<br />";
			folgaSemanalSugerida += item.getDiaSemana().getNome() + " " + item.getHoraInicio() + " às " + item.getHoraFim() + " (" + item.getMotivo().getNome() + ")";
		}
		
		resultado.add(folgaSemanalSugerida);
		
		return resultado;
	}
}
