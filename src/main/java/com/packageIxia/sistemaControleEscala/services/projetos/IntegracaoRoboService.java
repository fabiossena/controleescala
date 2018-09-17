package com.packageIxia.sistemaControleEscala.services.projetos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraAprovacao;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraTrabalhada;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;
import com.packageIxia.sistemaControleEscala.services.referencias.NotificacaoService;
import com.packageIxia.sistemaControleEscala.services.storage.StorageService;

@Service
public class IntegracaoRoboService extends Thread  {

    private ProjetoEscalaPrestadorService projetoEscalaPrestadorService;
	private HoraTrabalhadaService horaTrabalhadaService;
	private HoraAprovacaoService horaAprovacaoService;
	private NotificacaoService notificacaoService;
	private MultipartFile file;
	private Usuario usuario;
	private StorageService storageService;
	private String nomeCompleto;
    
	@Autowired
    public IntegracaoRoboService(
    		ProjetoEscalaPrestadorService projetoEscalaPrestadorService,
			HoraTrabalhadaService horaTrabalhadaService, 
			HoraAprovacaoService horaAprovacaoService,
			NotificacaoService notificacaoService,
			StorageService storageService) {
    	this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.horaTrabalhadaService = horaTrabalhadaService;
		this.horaAprovacaoService = horaAprovacaoService;
		this.notificacaoService = notificacaoService;
		this.storageService = storageService;
	}
    
	@Override
    public void run() {
    	try {
    		List<Notificacao>  mensagens = integraDadosRobo();
    		
    		List<String>  mensagens1 = new ArrayList<String>(); 
    		List<String>  mensagens2 = new ArrayList<String>(); 
    		List<String>  mensagens3 = new ArrayList<String>(); 
    		for (Notificacao notificacao : mensagens) {
    			if (notificacao.getNivel() == 1) {
    				mensagens1.add(notificacao.getMensagem());
    			} 
    			else if (notificacao.getNivel() == 2) {
    				mensagens2.add(notificacao.getMensagem());
    			} 
    			else if (notificacao.getNivel() == 3) {
    				mensagens3.add(notificacao.getMensagem());
    			}
			}    	

			if (mensagens1.size() > 0) {
				Notificacao not = new Notificacao(1, String.join("<br>", mensagens1));
				not.setNivel(1);
				not.setUsuario(usuario);
				not.setTitulo("Integracao robô");
				this.notificacaoService.save(not);
			} 
			
			if (mensagens2.size() > 0) {
				Notificacao not = new Notificacao(2, String.join("<br>", mensagens2));
				not.setNivel(2);
				not.setUsuario(usuario);
				not.setTitulo("Integracao robô");
				this.notificacaoService.save(not);
			} 
			
			if (mensagens3.size() > 0) {
				Notificacao not = new Notificacao(3, String.join("<br>", mensagens3));
				not.setNivel(3);
				not.setUsuario(usuario);
				not.setTitulo("Integracao robô");
				this.notificacaoService.save(not);
			}
    		
       } catch (Exception e) {
            throw new RuntimeException(e);
      }    
    }

	public List<Notificacao> integraDadosRobo() throws FileNotFoundException, IOException {
        
		List<Notificacao> mensagens = new ArrayList<Notificacao>();
		FileInputStream fis = null;
		HSSFWorkbook workbook = null;       
		HSSFSheet sheet = null;
		int quantidadeRegistros = 1;
		int quantidadeRegistrosValidos = 1;
		LocalDateTime data = null;
		
        try {
        	
			fis = new FileInputStream(new File(nomeCompleto));
	
			workbook = new HSSFWorkbook(fis);			
			sheet = workbook.getSheet("Detalhado");
			if (sheet == null) {
				mensagens.add(new Notificacao(3, "Excel incompatível"));
				return mensagens;
			}
			
			Iterator<Row> rowInterator = sheet.iterator();
	
			Map<String, ProjetoEscalaPrestador> prestadores = new HashMap<String, ProjetoEscalaPrestador>();

			List<HoraTrabalhada> horasTrabalhadas = new ArrayList<HoraTrabalhada>();
			
			String ramalRobo = "";
			while (rowInterator.hasNext()) {
					
				boolean encontrado = true;
				quantidadeRegistros++;
				Row row = rowInterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				ProjetoEscalaPrestador prestador = null;
				LocalDateTime dataHoraInicio = null;
				LocalDateTime dataHoraFim = null;
				int tipo = 0;
				String motivo = "";
				
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getColumnIndex()) {
						case 0:
							if (quantidadeRegistros == 2) {	
								String val = cell.getStringCellValue();
								if (!val.equals("usuário"))
									if (sheet == null) {
										mensagens.add(new Notificacao(3, "A coluna " + cell.getColumnIndex() + "  (" + val + ") deve estar configurada com o valor 'usuário'"));
										return mensagens;
								}
							}
							else {
								ramalRobo = cell.getStringCellValue();
								if (prestadores.containsKey(ramalRobo)) {
									prestador = prestadores.get(ramalRobo);
								}
								else {
									List<ProjetoEscalaPrestador> prest = this.projetoEscalaPrestadorService.findAllByRamalIntegracaoRobo(ramalRobo);
									if (prest == null || prest.size() == 0) {
										encontrado = false;
										prestadores.put(ramalRobo, null);
										mensagens.add(new Notificacao(2, "Não encontrado o ramal de integração com robô '" + ramalRobo + "' nos prestadores cadastrados"));
										break;
									}
									else {
										prestadores.put(ramalRobo, prest.get(0));
										prestador = prestadores.get(ramalRobo);
									}
								}
							}
							
							break;
						case 1:
							if (quantidadeRegistros == 2) {		
								String val = cell.getStringCellValue();
								if (!val.equals("data inicial")) {
									mensagens.add(new Notificacao(3, "A coluna " + cell.getColumnIndex() + "  (" + val + ") deve estar configurada com o valor 'data inicial'"));
									return mensagens;
								}
							}
							else {								
								dataHoraInicio = Utilities.stringToDateTime(cell.getStringCellValue().substring(0, 19));
								if (data == null) {
									data = dataHoraInicio;
								} else if (data.getYear() != dataHoraInicio.getYear() || data.getMonthValue() != dataHoraInicio.getMonthValue()) {
									mensagens.add(new Notificacao(3, "O arquivo deve se referir a apontamentos de apenas um mês"));		
									return mensagens;
								}
							}
							break;
						case 2:
							if (quantidadeRegistros == 2) {		
								String val = cell.getStringCellValue();
								if (!val.equals("atividade")) {
									mensagens.add(new Notificacao(3, "A coluna " + cell.getColumnIndex() + "  (" + val + ") deve estar configurada com o valor 'atividade'"));
									return mensagens;
								}
							}
							else {
								if (cell.getStringCellValue() == "Login") {
									tipo = 1;
								}
								else if (cell.getStringCellValue() == "Pausar") {
									tipo = 2;
								}
								else {
									encontrado = false;
									break;
								}
							}
							break;
						case 3:
							if (quantidadeRegistros == 2) {	
								String val = cell.getStringCellValue();
								if (!val.equals("data final")) {
									mensagens.add(new Notificacao(3, "A coluna " + cell.getColumnIndex() + "  (" + val + ") deve estar configurada com o valor ''data final'"));
									return mensagens;
								}
							}
							else {
								dataHoraFim = Utilities.stringToDateTime(cell.getStringCellValue().substring(0, 19));
							}
							break;
						case 5:
							if (quantidadeRegistros == 2) {
								String val = cell.getStringCellValue();
								if (!val.equals("complemento")) {
									mensagens.add(new Notificacao(1, "A coluna " + cell.getColumnIndex() + "  (" + val + ") deve estar configurada com o valor 'complemento'"));
									return mensagens;
								}
							}
							else {
								motivo = cell.getStringCellValue();
							}
							break;
					}
				}

				
				if (encontrado) {
					if (prestador != null &&
						dataHoraInicio != null && 
						dataHoraInicio != dataHoraFim && 
						tipo != 0) {
						
						HoraTrabalhada hora = new HoraTrabalhada();
						HoraAprovacao horaAprovacao = this.horaAprovacaoService.findLastByPrestadorIdOrInsert(prestador.getPrestador().getId(), dataHoraInicio.getYear(), dataHoraInicio.getMonthValue());
						
						if (horaAprovacao.getAceiteAprovador() != 0 || horaAprovacao.getAceitePrestador() != 0 || horaAprovacao.getHorasTrabalhadas().stream().anyMatch(x->x.getAprovadoResponsavel() != 0)) {
							mensagens.add(new Notificacao(3, "A aprovação de horas para este item processo (" + horaAprovacao.getId() + ") já esta em andamento"));
							return mensagens;
						}
						
						if (horaAprovacao != null) {
							
							hora.setHoraAprovacao(horaAprovacao);
							hora.setProjetoEscala(prestador.getProjetoEscala());
							hora.setDataHoraInicio(dataHoraInicio);
							hora.setDataHoraFim(dataHoraFim);
							hora.setTipoAcao(tipo);
							hora.setIncluidoRobo(true);
							hora.setUsuarioCriacao(usuario);
							hora.setMotivoPausa(motivo);
							horasTrabalhadas.add(hora);
							quantidadeRegistrosValidos++;
						}
					}
				}
			}

		/*	if (horasTrabalhadas.stream().anyMatch(x->x.getAprovadoResponsavel() != 0 || x.getHoraAprovacao().getAceitePrestador() != 0 || x.getHoraAprovacao().getAceiteAprovador() != 0)) {
				
			}*/

			this.horaTrabalhadaService.deleteAllByMonthYear(data.getMonthValue(), data.getYear());	
			
			for (HoraTrabalhada horaTrabalhada : horasTrabalhadas) {
				this.horaTrabalhadaService.save(horaTrabalhada, null);				
			}
			
			mensagens.add(new Notificacao(1, "Integrado " + 
        			(quantidadeRegistrosValidos-1) + 
        			" registro(s) para " + 
        			prestadores.size() + 
        			" prestador(es)" + 
        			(data == null ? "" : " na data de " + data.format(DateTimeFormatter.ofPattern("MM/yyyy")))));
        	
        } catch (Exception e) {
        	mensagens.add(new Notificacao(3,  e.getMessage()));		
        }
        finally {
			
        	sheet = null;    
        	workbook = null;
        	if (fis != null) {
        		fis.close();   
        	}
			
		}
        
        return mensagens;
	}

	public void setFile(MultipartFile file) {
		this.file = file;		
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setNome(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
		
	}
}
