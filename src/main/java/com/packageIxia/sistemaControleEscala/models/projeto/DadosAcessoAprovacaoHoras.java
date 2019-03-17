package com.packageIxia.sistemaControleEscala.models.projeto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.referencias.DadoGenerico;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
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
	private String observacaoHoras;
	private int diasTrabalhados;
	List<ProjetoEscalaPrestador> projetoEscalaPrestadores;
	
	private int historicoAlterado = 1, exclusao = 1, inclusao = 1,		
				horasAcima15m = 1, horasAcima30m = 1, horasAcima1h = 1, horasAcima3h = 1, horasAcima7h = 1, horasAcima12h = 1, horasAcima23h = 1,
				horasAbaixo15m = 1, horasAbaixo30m = 1, horasAbaixo1h = 1, horasAbaixo3h = 1, horasAbaixo5h = 1;
	private List<AusenciaSolicitacao> ausenciaSolicitacoes;
	

	public DadosAcessoAprovacaoHoras() {
		
	}
	
	public DadosAcessoAprovacaoHoras(
			HoraAprovacao horaAprovacao, 
			Usuario usuarioLogado, 
			List<ProjetoEscalaPrestador> projetoEscalaPrestadores,
			List<AusenciaSolicitacao> ausenciaSolicitacoes) {
		this.usuarioLogado = usuarioLogado;	
		this.projetoEscalaPrestadores = projetoEscalaPrestadores;
		this.ausenciaSolicitacoes = ausenciaSolicitacoes;
		
		if (horaAprovacao == null) {
			return;
		}
		
		setDadosAcesso(horaAprovacao);
		
		if (horaAprovacao.getHorasTrabalhadas() != null && horaAprovacao.getHorasTrabalhadas().size() > 0) {
			this.setDadosAprovacao(horaAprovacao.getHorasTrabalhadas());
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

		if (horaAprovacao == null) {
			return;
		}
		
		this.dadosAcesso = false;
		this.aprovador = new Usuario();
    	if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId() ||
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId()) {

    		this.dadosAcesso = true;
    		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId()) {
	    		
	    		List<HoraTrabalhada> hr = 
	    				horaAprovacao.getHorasTrabalhadas().stream().filter(x->
						 x.getProjetoEscala().getMonitor().getId() == usuarioLogado.getId()).collect(Collectors.toList());
	    		
	    		this.aprovador = usuarioLogado;
	    		if (hr.size() > 0) {
	    			this.aprovado =  hr.stream().allMatch(x->x.getAprovadoResponsavel() == 1) ? 1 : (hr.stream().allMatch(x->x.getAprovadoResponsavel() == 2) ? 2 : 0);
	    		}
	    	}
    	}

    	boolean acessoAdm = usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() || 
    				 usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.financeiro.getId();
    	if (acessoAdm) {
    		List<HoraTrabalhada> hr = horaAprovacao.getHorasTrabalhadas();
    		
    		this.aprovador = usuarioLogado;
    		if (hr.size() > 0) {
    			this.aprovado =  hr.stream().allMatch(x->x.getAprovadoResponsavel() == 1) ? 1 : (hr.stream().allMatch(x->x.getAprovadoResponsavel() == 2) ? 2 : 0);
    		}
    	}
	}
	
	public void setDadosAprovacao(List<HoraTrabalhada> horasTrabalhadas) {
		
		this.dadosAprovacao = new ArrayList<DadoGenerico>();
		
		historicoAlterado = 1; exclusao = 1; inclusao = 1;		
		horasAcima15m = 1; horasAcima30m = 1; horasAcima1h = 1; horasAcima3h = 1; horasAcima7h = 1; horasAcima12h = 1; horasAcima23h = 1;
		horasAbaixo15m = 1; horasAbaixo30m = 1; horasAbaixo1h = 1; horasAbaixo3h = 1; horasAbaixo5h = 1;
		
		int baseHorasDia = 6;
		double horasDia = 0; 
		
		LocalDate dataPercorrida = LocalDate.MIN;
		boolean primeiroLoop = true;
		
		for (HoraTrabalhada horaTrabalhada : horasTrabalhadas) {
			
			if (primeiroLoop) {
				this.setDiasTrabalhados(1);		
			}
			
			if (!primeiroLoop && !dataPercorrida.equals(horaTrabalhada.getDataHoraInicio().toLocalDate())) {
				this.diasTrabalhados++;
				//double horasCauculo = horasDia - baseHorasDia; 
				//this.setDadosHorasCauculoObservacoes(horasDia, horasCauculo);
				this.setDadosHorasCauculoInconsistencias(horasDia);				
				
				horasDia = 0;
			}
			
			primeiroLoop = false;
			dataPercorrida = horaTrabalhada.getDataHoraInicio().toLocalDate();
			
			double horas = horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getHoras();
			
			this.setDadosHorasTrabalhadas(horasTrabalhadas, horaTrabalhada, horas);
			
			horasDia += (horaTrabalhada.isExcluido() ? 0 : 
				(horaTrabalhada.getTipoAcao() == 1 ? horaTrabalhada.getHoras() : -horaTrabalhada.getHoras()));
			
			if (horaTrabalhada.getHistoricoCorrecao() != null && 
				!horaTrabalhada.getHistoricoCorrecao().isEmpty()) {
				historicoAlterado++;
			}			
			if (horaTrabalhada.isExcluido()) {
				exclusao++;
			}			
			if (horaTrabalhada.isIncluidoManualmente()) {
				inclusao++;
			}			
		}

		//double horasCauculo = horasDia - baseHorasDia; 
		//this.setDadosHorasCauculoObservacoes(horasDia, horasCauculo);
		this.setDadosHorasCauculoInconsistencias(horasDia);
		
		this.setObservacoesHoras(
				historicoAlterado, exclusao, inclusao,
				horasAcima15m, horasAcima30m, horasAcima1h, horasAcima3h, horasAcima7h, horasAcima12h, horasAcima23h, 
				horasAbaixo15m, horasAbaixo30m, horasAbaixo1h, horasAbaixo3h, horasAbaixo5h);
		
		this.totalValor = Utilities.Round(this.totalValor);
		this.totalHoras = Utilities.Round(this.totalHoras, 3);
		this.totalSegundos = Utilities.Round(this.totalSegundos, 3);
	}

	private void setDadosHorasCauculoInconsistencias(double horasDia) {
		if (horasDia >= 23) {
			horasAcima23h++;
		} else if (horasDia >= 12) {
			horasAcima12h++;
		} else if (horasDia >= 7) {
			horasAcima7h++;
		} else /*if (horasDia >= 3) {
			horasAcima3h++;
		} else if (horasDia >= 1) {
			horasAcima1h++;
		} else if (horasDia >= 0.5) {
			horasAcima30m++;
		} else if (horasDia > 0 && horasDia >= 0.25) {
			horasAcima15m++;
		} 
		else */ if (horasDia > 0 && horasDia <= 0.25){
			horasAbaixo15m++;
		} else 
		if (horasDia > 0 && horasDia <= 0.5) {
			horasAbaixo30m++;
		} else if (horasDia > 0 && horasDia <= 1) {
			horasAbaixo1h++;
		} else if (horasDia > 0 && horasDia <= 3) {
			horasAbaixo3h++;
		} else if (horasDia > 0 && horasDia <= 5) {
			horasAbaixo5h++;
		}
	}

	private void setDadosHorasTrabalhadas(List<HoraTrabalhada> horasTrabalhadas, HoraTrabalhada horaTrabalhada, double horas) {
		
		boolean encontrou = false;
		Usuario prestador = horaTrabalhada.getHoraAprovacao().getPrestador();
		
		double segundos = horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getSegundos();
		double valor = ((segundos/60)  * prestador.getValorMinuto());
		//double totalHorasDiaEscala = Utilities.horaValueDiff(horaTrabalhada.getProjetoEscala().getHoraFim(), horaTrabalhada.getProjetoEscala().getHoraInicio());
		
		
		for (DadoGenerico dadoGenerico : this.dadosAprovacao) {
			if (dadoGenerico.getId() == horaTrabalhada.getProjetoEscala().getId()) {
				
				dadoGenerico.setDoubleValue(
						Utilities.Round(
								dadoGenerico.getDoubleValue() + (horaTrabalhada.isExcluido() ? 0 : 
									(horaTrabalhada.getTipoAcao() == 1 ? horaTrabalhada.getHoras() : -horaTrabalhada.getHoras())), 3));
				
				if (horaTrabalhada.getTipoAcao() == 1) {
					this.totalValor += valor;
					this.totalHoras += horas;
					this.totalSegundos +=  segundos;
				}
				else {
					this.totalValor -= valor;
					this.totalHoras -= horas;
					this.totalSegundos -= segundos;
				}					
				
				dadoGenerico.setDescricao(Utilities.converterSecToTime((int)(dadoGenerico.getDoubleValue()*60*60)));
				encontrou = true;
				break;
			}
		}
		
		if (!encontrou) {
			
			ProjetoEscalaPrestador projetoEscalaPrestador = null;
			if (projetoEscalaPrestadores != null && projetoEscalaPrestadores.size() > 0) {
				 Stream<ProjetoEscalaPrestador> projetoEscalaPrestadoresStream = projetoEscalaPrestadores.stream().filter(x->x.getProjetoEscala().getId() == horaTrabalhada.getProjetoEscala().getId());
				System.out.println(projetoEscalaPrestadoresStream.count());
				projetoEscalaPrestador =  projetoEscalaPrestadoresStream == null || projetoEscalaPrestadoresStream.count() == 0 ? null : projetoEscalaPrestadoresStream.findFirst().get();
			}
			String observacaoHoras = 
					(projetoEscalaPrestador == null ? 
					horaTrabalhada.getProjetoEscala().getObservacaoHorasEscala(horaTrabalhada.getDataHoraInicio().getMonthValue(), horaTrabalhada.getDataHoraInicio().getYear()) : 
					projetoEscalaPrestador.getObservacaoHorasEscala(horaTrabalhada.getDataHoraInicio().getMonthValue(), horaTrabalhada.getDataHoraInicio().getYear()) //+ 
					//projetoEscalaPrestador.get().getObservacaoTodasFolgas()
							);
					
			this.dadosAprovacao.add(
					new DadoGenerico(
							horaTrabalhada.getProjetoEscala().getId(), 
							"(" + getStatusAprovacaoHoras(horasTrabalhadas, horaTrabalhada) + ") Monitor: " + 
							horaTrabalhada.getProjetoEscala().getMonitor().getNomeCompletoMatricula() + "<br>" +
							(horaTrabalhada.getMotivoRecusa() == null || horaTrabalhada.getMotivoRecusa().trim().isEmpty() ? "" : "<i>Motivo: " + horaTrabalhada.getMotivoRecusa() + "</i><br>") + 
							"<i>" + horaTrabalhada.getProjetoEscala().getDescricaoCompletaEscala() + "	" + observacaoHoras + "</i>", 
							
							Utilities.Round(horaTrabalhada.isExcluido() ? 0 : (horaTrabalhada.getTipoAcao() == 1 ? horaTrabalhada.getHoras() : -horaTrabalhada.getHoras()), 3),
							(horaTrabalhada.getTipoAcao() == 1 ? "" : "-") + Utilities.converterSecToTime(horaTrabalhada.isExcluido() ? 0 : (int)horaTrabalhada.getSegundos()),
							""));
			
			if (horaTrabalhada.getTipoAcao() == 1) {
				this.totalValor += ((segundos/60) * prestador.getValorMinuto());
				this.totalHoras += (horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getHoras());
				this.totalSegundos += horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getSegundos();
			}
			else {
				this.totalValor -= ((segundos/60) * prestador.getValorMinuto());
				this.totalHoras -= (horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getHoras());
				this.totalSegundos -= horaTrabalhada.isExcluido() ? 0 : horaTrabalhada.getSegundos();
			}
		}
	}

	private void setObservacoesHoras(
			int historicoAlterado, int exclusao, int inclusao,
			int horasAcima15m, int horasAcima30m, int horasAcima1h, int horasAcima3h, int horasAcima7h, int horasAcima12h, int horasAcima23h, 
			int horasAbaixo15m, int horasAbaixo30m, int horasAbaixo1h, int horasAbaixo3h, int horasAbaixo5h) {

//		String observacaoHorasAcima = setObservacaoHorasAcima();
//
//		String observacaoHorasAbaixo = setObservacaoHorasAbaixo();
		
		String observacaoHorasInconsistencia = setObservacaoHorasInconsistentes();
		
		String observacaoInclusao = "";
		if (inclusao > 1) {
			observacaoInclusao = (inclusao == 2 ? "1 inclusão manual" : ((inclusao-1) + " inclusões manual"));
		}
		
		String observacaoExclusao = "";
		if (exclusao > 1) {
			observacaoExclusao = (exclusao == 2 ? "1 apagado" : ((exclusao-1) + " apagados"));
		}
		
		String historicoAlteracao = "";
		if (historicoAlterado > 1) {
			historicoAlteracao = (historicoAlterado == 2 ? "1 histórico alteração" : ((historicoAlterado-1) + " históricos de alterações"));
		}

		this.observacaoHoras = "";
		//this.observacaoHoras += observacaoHorasAcima == "" ? "" : "<br>"+observacaoHorasAcima;
		//this.observacaoHoras += observacaoHorasAbaixo == "" ? "" : "<br>"+observacaoHorasAbaixo;
		this.observacaoHoras += observacaoHorasInconsistencia == "" ? "" : "<br>"+observacaoHorasInconsistencia; 
		this.observacaoHoras += observacaoInclusao == "" ? "" : "<br>"+observacaoInclusao;
		this.observacaoHoras += observacaoExclusao == "" ? "" : "<br>"+observacaoExclusao;
		this.observacaoHoras += historicoAlteracao == "" ? "" : "<br>"+historicoAlteracao;
		
		if (this.ausenciaSolicitacoes != null && !this.ausenciaSolicitacoes.isEmpty()) {
			List<AusenciaSolicitacao> ferias = this.ausenciaSolicitacoes.stream().filter(x->x.getMotivoAusencia().getNome().toLowerCase()=="férias").collect(Collectors.toList());
			
			List<AusenciaSolicitacao> ausencias = this.ausenciaSolicitacoes.stream().filter(x->x.getMotivoAusencia().getNome().toLowerCase()!="férias").collect(Collectors.toList());
		}
		// folgas / ferias
	}

	private String setObservacaoHorasInconsistentes() {
		

		String observacaoHorasInconsistentes = "";
		
		if (horasAcima23h > 1) {
			observacaoHorasInconsistentes += (observacaoHorasInconsistentes.contains("Horas acima/abaixo") ? " " : "Horas acima/abaixo ") + ">23hr("+(horasAcima23h-1)+")";
		}
		if (horasAcima12h > 1) {
			observacaoHorasInconsistentes += (observacaoHorasInconsistentes.contains("Horas acima/abaixo") ? " " : "Horas acima/abaixo ") + ">12hr("+(horasAcima12h-1)+")";
		}
		if (horasAcima7h > 1) {
			observacaoHorasInconsistentes += (observacaoHorasInconsistentes.contains("Horas acima/abaixo") ? " " : "Horas acima/abaixo ") + ">7hr("+(horasAcima7h-1)+")";
		}		
		
		if (horasAbaixo5h > 1) {
			observacaoHorasInconsistentes += (observacaoHorasInconsistentes.contains("Horas acima/abaixo ") ? " " : "Horas acima/abaixo ") + "<5hr("+(horasAbaixo5h-1)+")";
		}
		if (horasAbaixo3h > 1) {
			observacaoHorasInconsistentes += (observacaoHorasInconsistentes.contains("Horas acima/abaixo ") ? " " : "Horas acima/abaixo ") + "<3hr("+(horasAbaixo3h-1)+")";
		}
		if (horasAbaixo1h > 1) {
			observacaoHorasInconsistentes += (observacaoHorasInconsistentes.contains("Horas acima/abaixo ") ? " " : "Horas acima/abaixo ") + "<1hr("+(horasAbaixo1h-1)+")";				
		}
		if (horasAbaixo30m > 1) {
			observacaoHorasInconsistentes += (observacaoHorasInconsistentes.contains("Horas acima/abaixo ") ? " " : "Horas acima/abaixo ") + "<30min("+(horasAbaixo30m-1)+")";
		}
		if (horasAbaixo15m > 1) {
			observacaoHorasInconsistentes += (observacaoHorasInconsistentes.contains("Horas acima/abaixo ") ? " " : "Horas acima/abaixo ") + "<15min("+(horasAbaixo15m-1)+")";
		}
		
		return observacaoHorasInconsistentes;
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

	public String getTotalHorasFormatada() {
		return Utilities.converterSecToTime((int)this.totalSegundos);
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

	public String getObservacaoHoras() {
		return observacaoHoras;
	}

	public void setObservacaoHoras(String observacao) {
		this.observacaoHoras = observacao;
	}

	public int getDiasTrabalhados() {
		return diasTrabalhados;
	}

	public void setDiasTrabalhados(int diasTrabalhados) {
		this.diasTrabalhados = diasTrabalhados;
	}
}
