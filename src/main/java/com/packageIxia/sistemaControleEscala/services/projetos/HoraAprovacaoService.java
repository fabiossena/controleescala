package com.packageIxia.sistemaControleEscala.services.projetos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.projeto.HoraAprovacaoDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.HoraTrabalhadaDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.projeto.DadosAcessoAprovacaoHoras;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraAprovacao;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraTrabalhada;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class HoraAprovacaoService {
	
	private HoraAprovacaoDao horaAprovacaoDao;
	private HttpSession session;
	private ProjetoService projetoService;
	private HoraTrabalhadaDao horaTrabalhadaDao;
	private FuncaoConfiguracaoService funcaoConfiguracaoService;

	@Autowired
	public HoraAprovacaoService(
			HoraAprovacaoDao horaAprovacaoDao,
			HoraTrabalhadaDao horaTrabalhadaDao,
			ProjetoService projetoService,
			ProjetoEscalaPrestadorService projetoEscalaPrestadorService,
			FuncaoConfiguracaoService funcaoConfiguracaoService,
			HttpSession session) {
		this.horaAprovacaoDao = horaAprovacaoDao;
		this.horaTrabalhadaDao = horaTrabalhadaDao;
		this.projetoService = projetoService;
		this.funcaoConfiguracaoService = funcaoConfiguracaoService;
		this.session = session;
	}

	
	public List<HoraAprovacao> findAll() throws Exception {
		return this.findAll(0, 0);
	}
	
	public List<HoraAprovacao> findAll(int ano, int mes) throws Exception {
		
		if (mes == 0 || ano == 0) {
			throw new Exception("Preencha os parametros obrigatórios");
		}
		
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.getFuncao().getId()) {
			return this.horaAprovacaoDao.findByPrestadorId(usuarioLogado.getId());
		}

		List<Projeto> projetos = this.projetoService.findAll();
		
		List<HoraAprovacao> all = this.horaAprovacaoDao.findAllByDate(ano, mes);
				
		if (all.size() == 0) {
			return new ArrayList<HoraAprovacao>();			
		}

		List<Long> iterator = all.stream().map(x->x.getId()).collect(Collectors.toList());
		all =  Utilities.toList(this.horaAprovacaoDao.findAllById(iterator));
		
		for (HoraAprovacao horaAprovacao : all) {
			if (horaAprovacao.getTotalHoras() == 0) {
				for (HoraTrabalhada horaTrabalhada : horaAprovacao.getHorasTrabalhadas()) {
					//horaAprovacao.setTotalHoras(horaAprovacao.getTotalHoras() + horaTrabalhada.getHoras());
					this.setProjetos(projetos, horaTrabalhada.getHoraAprovacao());
				}
				
				//horaAprovacao.setTotalValor(horaAprovacao.getTotalHoras() * 1.6); 
			}
		}
		
		return all;		
	}
	
	public HoraAprovacao findByDateAndPrestadorId(long prestadorId, int ano, int mes) throws Exception {
		if (mes == 0 || ano == 0) {
			throw new Exception("Escolha uma data");
		}
		
		HoraAprovacao prestador = this.horaAprovacaoDao.findAllByDateAndPrestadorId(ano, mes, prestadorId);
				
		if (prestador == null) {
			return null;			
		}

		HoraAprovacao horaAprovacao = this.horaAprovacaoDao.findById(prestador.getId()).orElse(null);
		this.setProjetos(horaAprovacao);
		
		return horaAprovacao;
				
	}
	
	public HoraAprovacao findLastByPrestadorIdOrInsert(long prestadorId, int ano, int mes) throws Exception {
		
		if (mes == 0 || ano == 0 || prestadorId == 0) {
			throw new Exception("Preencha os parametros obrigatórios");
		}
		
		HoraAprovacao horaAprovacao = this.findByDateAndPrestadorId(prestadorId, ano, mes);
		
		if (horaAprovacao == null) {
			horaAprovacao = save(prestadorId, LocalDate.of(ano, mes, 1));
			return findById(horaAprovacao.getId());
		}

		this.setProjetos(horaAprovacao);
		return horaAprovacao;
	}


	private HoraAprovacao save(long prestadorId, LocalDate data) {
		
		HoraAprovacao horaAprovacao = new HoraAprovacao();
		
		Usuario prestador = new Usuario();
		prestador.setId(prestadorId);
		horaAprovacao.setPrestador(prestador);
		
		horaAprovacao.setData(data);
		
		this.save(horaAprovacao);
		this.setProjetos(horaAprovacao);
		
		return horaAprovacao;
	}
	
	public HoraAprovacao findById(Long id) {
		HoraAprovacao horaAprovacao = this.horaAprovacaoDao.findById(id).orElse(null);
		this.setProjetos(horaAprovacao);
		
		return horaAprovacao;
	}

	// todo:remover isto e usar manyToOne
	private void setProjetos(HoraAprovacao horaAprovacao) {
		this.setProjetos(null, horaAprovacao);
	}
	
	// todo:remover isto e usar manyToOne
	private void setProjetos(List<Projeto> projetos, HoraAprovacao horaAprovacao) {
		if (horaAprovacao == null || horaAprovacao.getHorasTrabalhadas() == null || horaAprovacao.getHorasTrabalhadas().size() == 0) {
			return;	
		}
		
		projetos = projetos != null ? projetos : this.projetoService.findAll();
		if (horaAprovacao != null) {
			for (HoraTrabalhada hrs : horaAprovacao.getHorasTrabalhadas()) {
				hrs.getProjetoEscala().setProjeto(projetos.stream().filter(x->x.getId() == hrs.getProjetoEscala().getProjetoId()).findFirst().get());
			} 
		}
	}	
	
	public String save(HoraAprovacao horaAprovacao) {
		this.horaAprovacaoDao.save(horaAprovacao);
		this.setProjetos(horaAprovacao);
		return "";
	}

	public String delete(long id) {
		return "";
	}

	public void updateAprovacaoReset(long id, double horas, double valor) {
		this.horaAprovacaoDao.updateAprovacaoReset(id,  "Alterações", horas, valor);		
	}
	
	public void updateAprovacao(boolean aprovar, long id, String motivo, HoraAprovacao aprovacaoHora) throws Exception {
		
		aprovacaoHora = aprovacaoHora != null ? aprovacaoHora : this.horaAprovacaoDao.findById(id).get();
		if (aprovacaoHora == null) {
			throw new Exception("Aprovação hora não selecionada devidamente");	
		}

		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		if (aprovacaoHora.getDadosAcessoAprovacaoHoras() == null) {
			aprovacaoHora.setDadosAcessoAprovacaoHoras(new DadosAcessoAprovacaoHoras(aprovacaoHora, usuarioLogado, this.funcaoConfiguracaoService.findAll()));
		}
		
		if (usuarioLogado.getFuncaoId() == FuncaoEnum.financeiro.getFuncao().getId()) {
			this.horaAprovacaoDao.updateAprovacaoResponsavel(aprovar ? 1 : 2, id, usuarioLogado.getId(), motivo);
			if (!aprovar) {
				this.updateAprovacaoReset(id, aprovacaoHora.getTotalHoras(), aprovacaoHora.getTotalValor());
				for (HoraTrabalhada hora : aprovacaoHora.getHorasTrabalhadas()) {	
					this.horaTrabalhadaDao.updateAprova(hora.getId(), hora.getResponsavelAprovacao().getId(), 0, "");
				}
			}
			return;
		}
			
		if (aprovacaoHora != null && aprovacaoHora.getPrestador().getId() == usuarioLogado.getId()) {
			this.updateAprovacaoReset(id, aprovacaoHora.getTotalHoras(), aprovacaoHora.getTotalValor());			
			this.horaAprovacaoDao.updateAprovacaoPrestador(aprovar ? 1 : 2, id, usuarioLogado.getId(), motivo);
			for (HoraTrabalhada hora : aprovacaoHora.getHorasTrabalhadas()) {	
				this.horaTrabalhadaDao.updateAprova(hora.getId(), hora.getResponsavelAprovacao().getId(), 0, "");
			}	
		}

		if (usuarioLogado.getFuncaoId() == FuncaoEnum.monitoramento.getFuncao().getId() && aprovacaoHora.getHorasTrabalhadas().stream().anyMatch(x->x.getProjetoEscala().getMonitor().getId() ==  usuarioLogado.getId())) {

			if (aprovacaoHora.getAceiteAprovador() == 2) {
				this.horaAprovacaoDao.updateAprovacaoResponsavel(0, id, aprovacaoHora.getAprovador().getId(), "");
			}
			
			if (!aprovar) {
				this.updateAprovacaoReset(id, aprovacaoHora.getTotalHoras(), aprovacaoHora.getTotalValor());
			}
			
			for (HoraTrabalhada hora : aprovacaoHora.getHorasTrabalhadas().stream().filter(x->x.getProjetoEscala().getMonitor().getId() == usuarioLogado.getId()).collect(Collectors.toList())) {	
				this.horaTrabalhadaDao.updateAprova(hora.getId(), usuarioLogado.getId(), aprovar ? 1 : 2, motivo);
			}
		}
	}

	public void uploadNota(long id, String arquivoNota) {
		this.horaAprovacaoDao.updateNota(id, arquivoNota);
	}

	public void updateCsvGerado(List<Long> itens) {		
		this.horaAprovacaoDao.updateCsvGerado(itens);		
	}
}
