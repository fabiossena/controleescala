package com.packageIxia.sistemaControleEscala.services.projetos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.projeto.AusenciaReposicaoDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaReposicao;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaSolicitacao;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
@Service
public class AusenciaReposicaoService {
	
	private AusenciaReposicaoDao ausenciaReposicaoDao;
	private ProjetoEscalaService projetoEscalaService;
	private ProjetoService projetoService;

	@Autowired
	public AusenciaReposicaoService(
			AusenciaReposicaoDao ausenciaReposicaoDao,
			ProjetoEscalaService projetoEscalaService,
			ProjetoService projetoService) {
		this.ausenciaReposicaoDao = ausenciaReposicaoDao;
		this.projetoEscalaService = projetoEscalaService;
		this.projetoService = projetoService;
	}
	
	public String save(AusenciaReposicao ausenciaReposicao) {
		this.ausenciaReposicaoDao.save(ausenciaReposicao);
		return "";
	}

	public String delete(long ausenciaReposicaoId) {
		this.ausenciaReposicaoDao.deleteById(ausenciaReposicaoId);
		return "";
	}

	public long preSaveReposicoes(AusenciaReposicao ausenciaReposicao, AusenciaSolicitacao solicitacaoEditada) {
			long id = ausenciaReposicao.getId();
			if (solicitacaoEditada.getAusenciaReposicoes() == null) {
				solicitacaoEditada.setAusenciaReposicoes(new ArrayList<AusenciaReposicao>());
			}
			
			if(ausenciaReposicao.getId() == 0) {
				id = solicitacaoEditada.getAusenciaReposicoes().size() == 0 ? 0 : solicitacaoEditada.getAusenciaReposicoes().stream().min(Comparator.comparing(AusenciaReposicao::getId)).get().getId();
				id = id > 0 ? -1 : id - 1; // atribui o menor id encontrado encontrado para adicionar este item quando salvar
				ausenciaReposicao.setId(id);
				
				// todo: remover depois que colocar join
				ProjetoEscala escala = projetoEscalaService.findById(ausenciaReposicao.getProjetoEscalaTroca().getId());
				escala.setProjeto(projetoService.findById(escala.getProjetoId()));
				ausenciaReposicao.setProjetoEscalaTroca(escala);
				
				solicitacaoEditada.getAusenciaReposicoes().add(ausenciaReposicao);
			}
			else {
				for (int i = 0; i < solicitacaoEditada.getAusenciaReposicoes().size();i++) {
					if(solicitacaoEditada.getAusenciaReposicoes().get(i).getId() == ausenciaReposicao.getId()) {
						solicitacaoEditada.getAusenciaReposicoes().get(i).setData(ausenciaReposicao.getData());
						solicitacaoEditada.getAusenciaReposicoes().get(i).setHoraInicio(ausenciaReposicao.getHoraInicio());
						solicitacaoEditada.getAusenciaReposicoes().get(i).setHoraFim(ausenciaReposicao.getHoraFim());
						solicitacaoEditada.getAusenciaReposicoes().get(i).setProjetoEscalaTroca(this.projetoEscalaService.findById(ausenciaReposicao.getProjetoEscalaTroca().getId()));
						solicitacaoEditada.getAusenciaReposicoes().get(i).setIndicadoOutroUsuario(ausenciaReposicao.isIndicadoOutroUsuario());
						if (solicitacaoEditada.getAusenciaReposicoes().get(i).isIndicadoOutroUsuario()) {
							solicitacaoEditada.getAusenciaReposicoes().get(i).setUsuarioTroca(ausenciaReposicao.getUsuarioTroca());
						}
						else {
							solicitacaoEditada.getAusenciaReposicoes().get(i).setUsuarioTroca(null);
						}
						solicitacaoEditada.getAusenciaReposicoes().get(i).setObservacao(ausenciaReposicao.getObservacao());
						break;
					}
				}
			}
			return id;
		}

	public List<AusenciaReposicao> findAllByProjetoEscalaId(int anoAtual, int mesAtual, long escalaId, int aceito) {
		// todo: usar query sql
		List<AusenciaReposicao> solicitacaoReposicoes = Utilities.toList(this.ausenciaReposicaoDao.findAllByProjetoEscalaTrocaId(escalaId));
		
		return solicitacaoReposicoes.stream().filter(x->
			x.getAusenciaSolicitacao().getAceito() >= aceito && 
			x.getData().getYear() == anoAtual && 
			x.getData().getMonthValue() == mesAtual).collect(Collectors.toList());
				
	}

	public List<AusenciaReposicao> findAllByProjetoId(int anoAtual, int mesAtual, long projetoId, int aceito) {
		List<Long> ids = this.projetoEscalaService.findAllByProjetoId(projetoId).stream().map(x->x.getId()).collect(Collectors.toList());
		List<AusenciaReposicao> solicitacaoReposicoes = new ArrayList<AusenciaReposicao>();
		for (Long id : ids) {
			solicitacaoReposicoes.addAll(this.findAllByProjetoEscalaId(anoAtual, mesAtual, id, aceito));
		}
		
		return solicitacaoReposicoes;		
	}

	public boolean existsByUsuarioTrocaId(long prestadorId) {
		return this.ausenciaReposicaoDao.existsByUsuarioTrocaId(prestadorId);
	}
}
