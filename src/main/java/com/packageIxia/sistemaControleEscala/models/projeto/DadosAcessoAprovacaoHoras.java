package com.packageIxia.sistemaControleEscala.models.projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.referencias.DadoGenerico;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

public class DadosAcessoAprovacaoHoras {

	private ArrayList<DadoGenerico> dadosAprovacao;
	private Usuario usuarioLogado;
	private boolean dadosAcesso;

	private Usuario aprovador;
	private int aprovado;
	
	private double totalHoras;
	private double totalSegundos;
	private double totalValor;
	
	public DadosAcessoAprovacaoHoras() {
		
	}
	
	public DadosAcessoAprovacaoHoras(HoraAprovacao horaAprovacao, Usuario usuarioLogado, List<FuncaoConfiguracao> funcaoConfiguracoes) {
		this.usuarioLogado = usuarioLogado;	
		
		setDadosAcesso(horaAprovacao);
		
		if (horaAprovacao.getHorasTrabalhadas() != null && horaAprovacao.getHorasTrabalhadas().size() > 0) {
			setDadosAprovacao(horaAprovacao.getHorasTrabalhadas(), funcaoConfiguracoes);
		}
	}

	public boolean getDadosAcesso() {
		return this.dadosAcesso;
	}

	public ArrayList<DadoGenerico> getDadosAprovacao() {
		return dadosAprovacao;
	}

	public Usuario getAprovador() {
		return aprovador;
	}

	public int getAprovado() {
		return aprovado;
	}
	
	private void setDadosAcesso(HoraAprovacao horaAprovacao) {

		this.dadosAcesso = false;
		this.aprovador = new Usuario();
    	if (!(usuarioLogado.getFuncaoId() != FuncaoEnum.monitoramento.funcao.getId() &&
			usuarioLogado.getFuncaoId() != FuncaoEnum.atendimento.funcao.getId())) {

    		this.dadosAcesso = true;
	    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.monitoramento.funcao.getId()) {
	    		
	    		List<HoraTrabalhada> hr = 
	    				horaAprovacao.getHorasTrabalhadas().stream().filter(x->
						(x.getProjetoEscala().getMonitor().getId() == usuarioLogado.getId())).collect(Collectors.toList());
	    		
	    		this.aprovador = usuarioLogado;
	    		if (hr.size() > 0) {
	    			this.aprovado =  hr.stream().allMatch(x->x.getAprovadoResponsavel() == 1) ? 1 : (hr.stream().allMatch(x->x.getAprovadoResponsavel() == 2) ? 2 : 0);
	    		}
	    	}
    	}

    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.financeiro.funcao.getId()) {
    		List<HoraTrabalhada> hr = horaAprovacao.getHorasTrabalhadas();
    		
    		this.aprovador = usuarioLogado;
    		if (hr.size() > 0) {
    			this.aprovado =  hr.stream().allMatch(x->x.getAprovadoResponsavel() == 1) ? 1 : (hr.stream().allMatch(x->x.getAprovadoResponsavel() == 2) ? 2 : 0);
    		}
    	}
	}
	
	public void setDadosAprovacao(List<HoraTrabalhada> horasTrabalhadas, List<FuncaoConfiguracao> funcaoConfiguracoes) {
		
		 this.dadosAprovacao = new ArrayList<DadoGenerico>();
	
		for (HoraTrabalhada horaTrabalhada : horasTrabalhadas) {
			boolean encontrou = false;
			
			double hora = horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getHoras();
			double segundos = horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getSegundos();
			String funcao = FuncaoEnum.GetFuncaoFromId(horaTrabalhada.getHoraAprovacao().getPrestador().getFuncaoId()).getNome();
			FuncaoConfiguracao config = funcaoConfiguracoes.stream().filter(x->x.getChave().toLowerCase().equals(funcao.toLowerCase())).findFirst().orElse(new FuncaoConfiguracao("", 0));
			
			for (DadoGenerico dadoGenerico : this.dadosAprovacao) {
				if (dadoGenerico.getId() == horaTrabalhada.getProjetoEscala().getId()) {
					dadoGenerico.setDoubleValue(
							Utilities.Round(
									dadoGenerico.getDoubleValue() + (horaTrabalhada.isExcluido() ? 0 : 
										(horaTrabalhada.getTipoAcao() == 1 ? horaTrabalhada.getHoras() : -horaTrabalhada.getHoras())), 3));
					if (horaTrabalhada.getTipoAcao() == 1) {
						this.totalValor += ((segundos/60) * config.getValorMinuto());
						this.totalHoras += hora;
						this.totalSegundos +=  segundos;
					}
					else {
						this.totalHoras -= (horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getHoras());
						this.totalValor -= ((segundos/60)  * config.getValorMinuto());
						this.totalSegundos -= horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getSegundos();
					}
					
					encontrou = true;
					break;
				}
			}
			
			if (!encontrou) {
				this.dadosAprovacao.add(
						new DadoGenerico(
								horaTrabalhada.getProjetoEscala().getId(), 
								"(" + getStatusAprovacaoHoras(horasTrabalhadas, horaTrabalhada) + ") Monitor: " + 
								horaTrabalhada.getProjetoEscala().getMonitor().getNomeCompletoMatricula() + "<br>" +
								(horaTrabalhada.getMotivoRecusa() != null && horaTrabalhada.getMotivoRecusa().trim().isEmpty() ? "" : "<i>Motivo: " + horaTrabalhada.getMotivoRecusa() + "</i><br>") + 
								"<i>" + horaTrabalhada.getProjetoEscala().getDescricaoCompletaEscala() + "</i>", 
								
								
								Utilities.Round(horaTrabalhada.isExcluido() ? 0 : (horaTrabalhada.getTipoAcao() == 1 ? horaTrabalhada.getHoras() : -horaTrabalhada.getHoras()), 3)));
				if (horaTrabalhada.getTipoAcao() == 1) {
					this.totalValor += ((segundos/60) * config.getValorMinuto());
					this.totalHoras += (horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getHoras());
					this.totalSegundos += horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getSegundos();
				}
				else {
					this.totalValor -= ((segundos/60) * config.getValorMinuto());
					this.totalHoras -= (horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getHoras());
					this.totalSegundos -= horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getSegundos();
				}
			}
		}
		
		this.totalValor = Utilities.Round(this.totalValor);
		this.totalHoras = Utilities.Round(this.totalHoras, 3);
		this.totalSegundos = Utilities.Round(this.totalSegundos, 3);
	}

	private String getStatusAprovacaoHoras(List<HoraTrabalhada> horasTrabalhadas, HoraTrabalhada horaTrabalhada) {
		List<HoraTrabalhada> horasTrabalhadasFiltered = 
				horasTrabalhadas.stream().filter(x->
					x.getProjetoEscala().getId() == horaTrabalhada.getProjetoEscala().getId()).collect(Collectors.toList());

		boolean aprovado = false;
		boolean reprovado = false;
		boolean pendentes = false;
		
		for (HoraTrabalhada hora : horasTrabalhadasFiltered) {
			aprovado = aprovado ? true : hora.getAprovadoResponsavel() == 1;
			reprovado = reprovado ? true : hora.getAprovadoResponsavel() == 2;
			pendentes = pendentes ? true : hora.getAprovadoResponsavel() == 0;
		}
		
		return aprovado && !reprovado && !pendentes ? "Aprovado" :
					(reprovado && !aprovado && !pendentes  ? "Reprovado" : 
						(pendentes && !reprovado && !reprovado ? "Pendente" : 
							(pendentes && aprovado ? "Parcialmente aprovado" : "Pedente")));
	}

	public double getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(double totalHoras) {
		this.totalHoras = totalHoras;
	}

	public double getTotalValor() {
		return totalValor;
	}

	public void setTotalValor(double totalValor) {
		this.totalValor = totalValor;
	}

	public double getTotalSegundos() {
		return totalSegundos;
	}
}
