package com.packageIxia.sistemaControleEscala.services.projetos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.packageIxia.sistemaControleEscala.daos.projeto.ProjetoFolgaSemanalDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoFolgaSemanal;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.IReferencias;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoFolgaSemanal;
import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemana;
import com.packageIxia.sistemaControleEscala.models.referencias.MotivoAusencia;

@Service
public class ProjetoFolgaSemanalService implements IProjetoFolgaSemanal {

	private ProjetoFolgaSemanalDao projetoFolgaSemanalDao;
	private IReferencias referenciasService;

	@Autowired
	public ProjetoFolgaSemanalService(
			ProjetoFolgaSemanalDao projetoFolgaSemanalDao,
    		IReferencias referenciasService) {
		this.projetoFolgaSemanalDao = projetoFolgaSemanalDao;
		this.referenciasService = referenciasService;
	}
	
	@Override
	public List<ProjetoFolgaSemanal> findAllByProjetoEscalaPrestadorId(long projetoEscalaPrestadorId) {
		if (projetoEscalaPrestadorId == 0) {
			return new ArrayList<ProjetoFolgaSemanal>();
		}
		
		List<DiaSemana> diasSemanas = this.referenciasService.getDiasSemana();
		List<MotivoAusencia> motivos = this.referenciasService.getMotivosAusencia();
		List<ProjetoFolgaSemanal> list = Utilities.toList(projetoFolgaSemanalDao.findAllByProjetoEscalaPrestadorId(projetoEscalaPrestadorId));
		for (ProjetoFolgaSemanal item : list) {			
			item.setDiaSemana(diasSemanas.stream().filter(x->x.getId() == item.getDiaSemanaId()).findAny().orElse(null));
			item.setMotivo(motivos.stream().filter(x->x.getId() == item.getMotivoId()).findFirst().get());
		}
		return list;
	}

	@Override
	public String validaFolgasSemanais(List<ProjetoFolgaSemanal> folgasSemanais, ProjetoEscala escalaPrestador, List<ProjetoEscala> escalas) {

		for (ProjetoFolgaSemanal item : folgasSemanais) {		

			if (item.getHoraInicio() == "") {
				return "Nas folgas semanais o campo 'Hora início' deve ser preenchido";
			}
			
			if (item.getHoraFim() == "") {
				return "Nas folgas semanais o campo 'Hora fim' deve ser preenchido";
			}


			if (!Utilities.validarHora(item.getHoraInicio())) {
				return "Nas folgas semanais o campo 'Hora início' esta inválido";
			}
			if (!Utilities.validarHora(item.getHoraFim())) {
				return "Nas folgas semanais o campo 'Hora fim' esta inválido";
			}
			
			
			if (Integer.parseInt(item.getHoraInicio().replace(":", "")) > Integer.parseInt(item.getHoraFim().replace(":", ""))) {
				return "Nas folgas semanais o campo 'Hora início' deve ser maior que 'Hora fim'";
			}
				
			ProjetoEscala escala = (ProjetoEscala) escalas.stream().filter(x->x.getId() == escalaPrestador.getId()).findFirst().get();
			
			if (item.getDiaSemana().getId() != 0 && 
				escala.getDiaSemanaDe().getId() != 0 &&
				escala.getDiaSemanaAte().getId() != 0 &&
				item.getDiaSemana().getId() < escala.getDiaSemanaDe().getId() || 
				item.getDiaSemana().getId() > escala.getDiaSemanaAte().getId()) {
				return "O campo 'Dia da semana de/até' das folgas semanais deve estar entre o 'Dia da semana de/até' da escala'";
			}
			
			if (item.getHoraInicio() != "" && 
				item.getHoraFim() != "" &&
				escala.getHoraInicio() != "" && 
				escala.getHoraFim() != "" &&
				(Integer.parseInt(item.getHoraInicio().replace(":", "")) < Integer.parseInt(escala.getHoraInicio().replace(":", "")) ||
				 Integer.parseInt(item.getHoraFim().replace(":", "")) > Integer.parseInt(escala.getHoraFim().replace(":", "")))) {
				return "O campo 'Hora início/fim' das folgas semanais deve estar entre a 'Hora início/fim' da escala'";
			}
		
		}
		
		return "";
	}
	
	@Override
	public void saveFolgasSemanais(List<ProjetoFolgaSemanal> folgasSemanais, Long projetoEscalaPrestadorId) {		

		folgasSemanais = folgasSemanais != null ? folgasSemanais : new ArrayList<ProjetoFolgaSemanal>();
		for (ProjetoFolgaSemanal item : Utilities.toList(this.projetoFolgaSemanalDao.findAllByProjetoEscalaPrestadorId(projetoEscalaPrestadorId))) {
			if (!folgasSemanais.stream().anyMatch(x -> x.getId() == item.getId())){
				this.projetoFolgaSemanalDao.delete(item);
			}
		}
		
		for (ProjetoFolgaSemanal item : folgasSemanais) {
    		item.setProjetoEscalaPrestadorId(projetoEscalaPrestadorId);
    		if (item.getId()<1) {
    			item.setId(0);
    			item.setMotivoId(item.getMotivoId());
    			item.setDiaSemanaId(item.getDiaSemanaId());
    			item.setProjetoEscalaPrestadorId(projetoEscalaPrestadorId);
    		}
		}
    	
		this.projetoFolgaSemanalDao.saveAll(folgasSemanais);
	}

	@Override
	public String delete(long id) {
		
		this.projetoFolgaSemanalDao.deleteById(id);
		return "";
	}

	@Override
	public long preSaveFolgaSemanal(List<ProjetoFolgaSemanal> folgasSemanais,
			ProjetoFolgaSemanal folgaSemanal) {
		folgasSemanais = folgasSemanais != null ? folgasSemanais : new ArrayList<ProjetoFolgaSemanal>();
		long id = folgaSemanal.getId();
		if (id == 0) {
			id = folgasSemanais.stream().count() == 0 ? 0 : folgasSemanais.stream().min(Comparator.comparing(ProjetoFolgaSemanal::getId)).get().getId();
			id = id > 0 ? -1 : id - 1; // atribui o menor id encontrado encontrado para adicionar este item quando salvar o usuario
			folgaSemanal.setId(id); 
			folgasSemanais.add(0, folgaSemanal);
		}
		else {
			for (int index = 0; index < folgasSemanais.size(); index++) {
				if (folgasSemanais.get(index).getId() == folgaSemanal.getId()) {
					folgasSemanais.get(index).setDiaSemana(folgaSemanal.getDiaSemana());
					folgasSemanais.get(index).setMotivo(folgaSemanal.getMotivo());
					folgasSemanais.get(index).setHoraInicio(folgaSemanal.getHoraInicio());
					folgasSemanais.get(index).setHoraFim(folgaSemanal.getHoraFim());
					break;
				}
			}
		}
		
		return id;
	}

	@Override
	public int preDeleteFolgaSemanal(
			List<ProjetoFolgaSemanal> folgasSemanais,
			int id) {

		folgasSemanais = folgasSemanais != null ? folgasSemanais : new ArrayList<ProjetoFolgaSemanal>();
		
		if (folgasSemanais.isEmpty()) {
			return id;			
		}
		
		int index = 1;
		for (ProjetoFolgaSemanal folga : folgasSemanais) {
			if (folga.getId() == id) {
				break;
			}
			index++;
		}

		folgasSemanais.remove(index-1);
		
		return id;
	}

	@Override
	public String findEscalaFolgaSemanal(long id) {		
		
		String folgaSemanalSugerida = "";		
		List<ProjetoFolgaSemanal> list = this.findAllByProjetoEscalaPrestadorId(id);
		
		if (list.size() > 0) {
			folgaSemanalSugerida = "Folga semanal: ";
			for (ProjetoFolgaSemanal item : list) {
				folgaSemanalSugerida += "<br />" + item.getDiaSemana().getNome() + " " + item.getHoraInicio() + " às " + item.getHoraFim() + " (" + item.getMotivo().getNome() + ")";
			}
		}
		
		return folgaSemanalSugerida;
	}
	
	@Override
	@Transactional
	public String deleteByProjetoEscalaPrestadorId(long id) {
		this.projetoFolgaSemanalDao.deleteByProjetoEscalaPrestadorId(id);
		return "";
	}
}
