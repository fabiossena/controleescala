package com.packageIxia.sistemaControleEscala.services.projetos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.daos.projeto.HoraAprovacaoDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.HoraTrabalhadaDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IHoraAprovacao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjeto;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.INotificacao;
import com.packageIxia.sistemaControleEscala.models.projeto.DadosAcessoAprovacaoHoras;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraAprovacao;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraTrabalhada;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class HoraAprovacaoService implements IHoraAprovacao {
	
	private HoraAprovacaoDao horaAprovacaoDao;
	private HttpSession session;
	private IProjeto projetoService;
	private HoraTrabalhadaDao horaTrabalhadaDao;
	private INotificacao notificacaoService;

	@Autowired
	public HoraAprovacaoService(
			HoraAprovacaoDao horaAprovacaoDao,
			HoraTrabalhadaDao horaTrabalhadaDao,
			IProjeto projetoService,
			IProjetoEscalaPrestador projetoEscalaPrestadorService,
			INotificacao notificacaoService,
			HttpSession session) {
		this.horaAprovacaoDao = horaAprovacaoDao;
		this.horaTrabalhadaDao = horaTrabalhadaDao;
		this.projetoService = projetoService;
		this.session = session;
		this.notificacaoService = notificacaoService;
	}

	
	@Override
	public List<HoraAprovacao> findAll() throws Exception {
		return this.findAll(0, 0);
	}
	
	@Override
	public List<HoraAprovacao> findAll(int ano, int mes) throws Exception {
		
		if (mes == 0 || ano == 0) {
			throw new Exception("Preencha os parametros obrigatórios");
		}
		
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId()) {
			return this.horaAprovacaoDao.findByPrestadorId(usuarioLogado.getId());
		}

		List<Projeto> projetos = this.projetoService.findAll();
		
		List<HoraAprovacao> all = this.horaAprovacaoDao.findAllByDate(ano, mes);
				
		if (all.size() == 0) {
			return new ArrayList<HoraAprovacao>();			
		}

		List<Long> iterator = all.stream().map(x->x.getId()).collect(Collectors.toList());
		all =  Utilities.toList(this.horaAprovacaoDao.findAllById(iterator));

		List<HoraAprovacao> result = new ArrayList<HoraAprovacao>();
		for (HoraAprovacao horaAprovacao : all) {
			boolean retorna = false;
			//if (horaAprovacao.getTotalHoras() == 0) {
				for (HoraTrabalhada horaTrabalhada : horaAprovacao.getHorasTrabalhadas()) {
					//horaAprovacao.setTotalHoras(horaAprovacao.getTotalHoras() + horaTrabalhada.getHoras());
					this.setProjetos(projetos, horaTrabalhada.getHoraAprovacao());
					
					retorna = usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() ||
							  usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.financeiro.getId() ||
							  usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.diretoria.getId();
					
					if (!retorna) {
						if (horaTrabalhada.getProjetoEscala().getProjeto().getGerente().getId() == usuarioLogado.getId()) {
							retorna = true;
						}
						else if (horaTrabalhada.getProjetoEscala().getMonitor().getId() == usuarioLogado.getId()) {
							retorna = true;
						}
					}
				}
				
				if (retorna) {
					result.add(horaAprovacao);
				}
				
				//horaAprovacao.setTotalValor(horaAprovacao.getTotalHoras() * 1.6); 
			//}
		}
		
		return result;		
	}
	
	@Override
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
	
	@Override
	public String insertLastByPrestadorIdOrInsert(long id, int ano, int mes) throws Exception {

		if (mes <= 0 || mes > 12) {
    		return "Digite um mês válido";
    	}

		if (ano < 2018 || ano > Utilities.now().getYear()) {
    		return "Digite um ano válido";
    	}

		if (Utilities.stringToDateTime(ano + "-" + (mes > 9 ? "" : "0") + mes + "-01 00:00:00").isAfter(  
			Utilities.stringToDateTime(Utilities.now().getYear() + "-"  + (Utilities.now().getMonthValue() > 9 ? "" : "0") + Utilities.now().getMonthValue() + "-01 00:00:00"))) {
    		return "Não é permitido gerar horas para os meses acima do atual";
    	}


		if (id <= 0) {
    		return "Digite um prestador válido";
    	}
		
		Usuario usuarioLogado = ((Usuario)session.getAttribute("usuarioLogado"));

    	if (id > 0 &&
			!(usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId() 
			|| usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.gerencia.getId() 
			|| usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() 
			|| usuarioLogado.getId() != id)) {
            return "Não permitido gerar horas com o usuário logado atual";
    	}

    	HoraAprovacao horaAprovacao = this.findByDateAndPrestadorId(id, ano, mes);
    	if (horaAprovacao == null) {
    		this.findLastByPrestadorIdOrInsert(id, ano, mes);
		}
    	else {
    		return "Usuário já possui horas geradas para o mês selecionado";
    	}
		
    	return "";
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public String save(HoraAprovacao horaAprovacao) {
		this.horaAprovacaoDao.save(horaAprovacao);
		this.setProjetos(horaAprovacao);
		return "";
	}

	@Override
	public String delete(long id) {
		return "";
	}

	@Override
	public void updateAprovacaoReset(long id, double horas, double valor) {
		this.horaAprovacaoDao.updateAprovacaoReset(id,  "Alterações", horas, valor);		
	}
	
	@Override
	public void updateAprovacao(boolean aprovar, long id, String motivo, HoraAprovacao aprovacaoHora) throws Exception {
		
		aprovacaoHora = aprovacaoHora != null ? aprovacaoHora : this.horaAprovacaoDao.findById(id).get();
		if (aprovacaoHora == null) {
			throw new Exception("Aprovação hora não selecionada devidamente");	
		}

		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		if (aprovacaoHora.getDadosAcessoAprovacaoHoras() == null) {
			aprovacaoHora.setDadosAcessoAprovacaoHoras(new DadosAcessoAprovacaoHoras(aprovacaoHora, usuarioLogado));
		}
		
		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.financeiro.getId()) {
			this.horaAprovacaoDao.updateAprovacaoResponsavel(aprovar ? 1 : 2, id, usuarioLogado.getId(), motivo);
			if (!aprovar) {
				this.updateAprovacaoReset(id, aprovacaoHora.getTotalHoras(), aprovacaoHora.getTotalValor());
				Collection<Long> enviadoMonitor = new ArrayList<Long>();
				for (HoraTrabalhada hora : aprovacaoHora.getHorasTrabalhadas()) {	
					this.horaTrabalhadaDao.updateAprova(hora.getId(), hora.getResponsavelAprovacao().getId(), 0, "");
					if (!enviadoMonitor .stream().anyMatch(x->x == hora.getResponsavelAprovacao().getId())) {
						this.notificacaoService.save(new Notificacao(3, "As horas trabalhadas do prestador ''" + aprovacaoHora.getPrestador().getNomeCompleto()  + "'' foram recusadas pelo financeiro, para que sejam efetuados os ajustes necessários (após retornando para sua aprovação)!", "Aprovação de horas", aprovacaoHora.getPrestador()));
						enviadoMonitor.add(hora.getResponsavelAprovacao().getId());
					}
				}
				
				this.notificacaoService.save(new Notificacao(3, "Suas horas trabalhadas foram ''recusadas'', por favor verifique e realize os ajustes necessários!", "Aprovação de horas", aprovacaoHora.getPrestador()));
			}
			else {
				this.notificacaoService.save(new Notificacao(1, "Suas horas trabalhadas foram aprovadas!", "Aprovação de horas", aprovacaoHora.getPrestador()));				
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

		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId() && aprovacaoHora.getHorasTrabalhadas().stream().anyMatch(x->x.getProjetoEscala().getMonitor().getId() ==  usuarioLogado.getId())) {

			if (aprovacaoHora.getAceiteAprovador() == 2) {
				this.horaAprovacaoDao.updateAprovacaoResponsavel(0, id, aprovacaoHora.getAprovador().getId(), "");
			}
			
			if (!aprovar) {
				this.updateAprovacaoReset(id, aprovacaoHora.getTotalHoras(), aprovacaoHora.getTotalValor());
				this.notificacaoService.save(new Notificacao(3, "Suas horas trabalhadas foram ''recusadas'', por favor verifique e realize os ajustes necessários!", "Aprovação de horas", aprovacaoHora.getPrestador()));
			}
			
			for (HoraTrabalhada hora : aprovacaoHora.getHorasTrabalhadas().stream().filter(x->x.getProjetoEscala().getMonitor().getId() == usuarioLogado.getId()).collect(Collectors.toList())) {	
				this.horaTrabalhadaDao.updateAprova(hora.getId(), usuarioLogado.getId(), aprovar ? 1 : 2, motivo);
			}
		}
	}

	@Override
	public void uploadNota(long id, String arquivoNota) {
		this.horaAprovacaoDao.updateNota(id, arquivoNota);
	}

	@Override
	public void updateCsvGerado(List<Long> itens) {		
		this.horaAprovacaoDao.updateCsvGerado(itens);		
	}
}
