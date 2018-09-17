package com.packageIxia.sistemaControleEscala.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packageIxia.sistemaControleEscala.helpers.GeradorCsv;
import com.packageIxia.sistemaControleEscala.models.projeto.DadosAcessoAprovacaoHoras;
import com.packageIxia.sistemaControleEscala.models.projeto.FuncaoConfiguracao;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraAprovacao;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraTrabalhada;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.referencias.Banco;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;
import com.packageIxia.sistemaControleEscala.services.projetos.FuncaoConfiguracaoService;
import com.packageIxia.sistemaControleEscala.services.projetos.HoraAprovacaoService;
import com.packageIxia.sistemaControleEscala.services.projetos.HoraTrabalhadaService;
import com.packageIxia.sistemaControleEscala.services.projetos.IntegracaoRoboService;
import com.packageIxia.sistemaControleEscala.services.projetos.ProjetoEscalaPrestadorService;
import com.packageIxia.sistemaControleEscala.services.projetos.ProjetoEscalaService;
import com.packageIxia.sistemaControleEscala.services.referencias.NotificacaoService;
import com.packageIxia.sistemaControleEscala.services.referencias.ReferenciasService;
import com.packageIxia.sistemaControleEscala.services.storage.StorageFileNotFoundException;
import com.packageIxia.sistemaControleEscala.services.storage.StorageService;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HoraTrabalhadaAprovacaoController {

	private ModelAndView horaAprovacaoView = new ModelAndView("projeto/horasAprovacoesView");
	private ModelAndView horaTrabalhadaView = new ModelAndView("projeto/horasTrabalhadasView");
	private ProjetoEscalaService projetoEscalaService;
	private HoraAprovacaoService horaAprovacaoService;
	private HoraTrabalhadaService horaTrabalhadaService;
	private HttpSession session;
	private List<HoraAprovacao> aprovacaoHoras;
	private List<ProjetoEscala> projetoEscalas;
	private List<HoraTrabalhada> horasTrabalhadas;
	private HoraAprovacao aprovacaoHora;
	private FuncaoConfiguracaoService funcaoConfiguracaoService;
	private StorageService storageService;
	private ReferenciasService referenciasService;
	private int banco;
	private int ano;
	private int mes;
	private NotificacaoService notificacaoService;
	private IntegracaoRoboService integracaoRoboService;
	private ProjetoEscalaPrestadorService projetoEscalaPrestadorService;
    
	public HoraTrabalhadaAprovacaoController(
			ProjetoEscalaService projetoEscalaService,
			HoraAprovacaoService horaAprovacaoService,
			HoraTrabalhadaService horaTrabalhadaService,
			FuncaoConfiguracaoService funcaoConfiguracaoService,
			ReferenciasService referenciasService,
			HttpSession session,
			StorageService storageService,
			NotificacaoService notificacaoService,
			IntegracaoRoboService integracaoRoboService,
			ProjetoEscalaPrestadorService projetoEscalaPrestadorService) {
		this.projetoEscalaService = projetoEscalaService;
		this.horaAprovacaoService = horaAprovacaoService;
		this.horaTrabalhadaService = horaTrabalhadaService;
		this.funcaoConfiguracaoService = funcaoConfiguracaoService;
		this.session = session;
        this.storageService = storageService;
		this.referenciasService = referenciasService;
		this.notificacaoService = notificacaoService;
		this.integracaoRoboService = integracaoRoboService;
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
	}

    @GetMapping("nota/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("id") long id) {
    	HoraAprovacao aprovacao;
    	if (aprovacaoHora != null && aprovacaoHora.getId() == id) {
    		aprovacao = aprovacaoHora;
    	}
    	else {
    		aprovacao = horaAprovacaoService.findById(id);
    	}
    	
    	if (aprovacao == null || aprovacao.getArquivoNota() == null || aprovacao.getArquivoNota().isEmpty()) {
    		return null;
    	}
    	
        Resource file = storageService.loadAsResource(aprovacao.getArquivoNota());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + aprovacao.getArquivoNota() + "\"").body(file);
    }
    
    @PostMapping("upload/{id}")
    public ModelAndView handleFileUpload(
    		@PathVariable("id") long id,
    		@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
    	
    	if (id == 0) {
    		return null;
    	}
    	
    	HoraAprovacao aprovacao;
    	if (aprovacaoHora != null && aprovacaoHora.getId() == id) {
    		aprovacao = aprovacaoHora;
    	}
    	else {
    		aprovacao = horaAprovacaoService.findById(id);
    	}
    	
    	if (aprovacao == null) {
    		return null;
    	}
    	
        String name = storageService.store(file, String.valueOf(aprovacao.getId()));
        horaAprovacaoService.uploadNota(aprovacaoHora.getId(), name);
        redirectAttributes.addFlashAttribute("message",
                "A nota foi anexada com sucesso");

		return new ModelAndView("redirect:/horasTrabalhadas/"+id);
    }
    
    @PostMapping("integracaoRobo")
    public ModelAndView handleRoboUpload(
    		@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) throws IOException {
    	ModelAndView modelAndView = new ModelAndView("redirect:/aprovacaoHoras");
		Usuario usuarioLogado = ((Usuario)session.getAttribute("usuarioLogado"));	
		if (usuarioLogado.getFuncaoId() != FuncaoEnum.administracao.getFuncao().getId()) {
			redirectAttributes.addFlashAttribute("message", "Operação não permitida para este usuário");
			return modelAndView;			
		}
        
        //this.horaTrabalhadaService.integraDadosRobo(file);

		IntegracaoRoboService job = new IntegracaoRoboService(
				this.projetoEscalaPrestadorService, 
				this.horaTrabalhadaService, 
				this.horaAprovacaoService,
				this.notificacaoService,
				this.storageService);

		
        String fileName = "integracao_robo_usuario" + usuarioLogado.getId() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss"));
        String nome = storageService.store(file, fileName);
        String nomeCompleto = storageService.getRootPath() + "\\" + nome;
        
        job.setUsuario(usuarioLogado);
        job.setNome(nomeCompleto);
        job.start();
		
        redirectAttributes.addFlashAttribute("message", "Iniciado processo, aguarde notificação de status");
		return modelAndView;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
	@GetMapping("aprovacaoHoras")
	public ModelAndView index(
			@RequestParam(value = "ano", defaultValue = "0") int ano,
			@RequestParam(value = "mes", defaultValue = "0") int mes,
			@RequestParam(value = "banco", defaultValue = "0") int banco) throws Exception {

		this.projetoEscalas = this.projetoEscalaService.findAllByPermissao();
		this.horaAprovacaoView.addObject("escalas", this.projetoEscalas);

		if (ano == 0 || mes == 0) {
			ano = LocalDate.now().getMonthValue() == 1 ? LocalDate.now().getYear() - 1: LocalDate.now().getYear();
			mes = LocalDate.now().getMonthValue() == 1 ? 12: LocalDate.now().getMonthValue()-1;
		}
		
		this.ano = ano;
		this.mes = mes;
		this.banco = banco;
		
		this.horaAprovacaoView.addObject("anoBase", LocalDate.now().getYear());
		this.horaAprovacaoView.addObject("ano", ano);
		this.horaAprovacaoView.addObject("mes", mes);
		this.horaAprovacaoView.addObject("bancoId", banco);
		this.banco = banco;

		Usuario usuarioLogado = ((Usuario)session.getAttribute("usuarioLogado"));
		this.aprovacaoHoras = this.horaAprovacaoService.findAll(ano, mes);
		if (banco == -1) {
			this.aprovacaoHoras = this.horaAprovacaoService.findAll(ano, mes).stream().filter(x->x.getPrestador().getBancoId() == 0).collect(Collectors.toList());
		}
		else if (banco > 0) {
			this.aprovacaoHoras = this.horaAprovacaoService.findAll(ano, mes).stream().filter(x->x.getPrestador().getBancoId() == banco).collect(Collectors.toList());
		}
		
		List<FuncaoConfiguracao> funcaoConfiguracao = funcaoConfiguracaoService.findAll();
		for (HoraAprovacao horaAprovacao : aprovacaoHoras) {
			horaAprovacao.setDadosAcessoAprovacaoHoras(new DadosAcessoAprovacaoHoras(horaAprovacao, usuarioLogado, funcaoConfiguracao));
			horaAprovacao.setTotalHoras(horaAprovacao.getDadosAcessoAprovacaoHoras().getTotalHoras());
			horaAprovacao.setTotalValor(horaAprovacao.getDadosAcessoAprovacaoHoras().getTotalValor());
		}
		
		this.horaAprovacaoView.addObject("aprovacaoHoras", this.aprovacaoHoras);
		
		return this.horaAprovacaoView;
	}
	
	@GetMapping("horasTrabalhadas/{id}")
	public ModelAndView horaTrabalhada(@PathVariable("id") long id) {

		horaTrabalhadaView.addObject("result", null);
		horaTrabalhadaView.addObject("errorMessage", null);
		horaTrabalhadaView.addObject("isDisableCampos", false); 		

		Usuario usuarioLogado = ((Usuario)session.getAttribute("usuarioLogado"));
		this.aprovacaoHora = this.horaAprovacaoService.findById(id);
		
		boolean isMonitoriaGerencia = 
			(usuarioLogado.getFuncaoId() == FuncaoEnum.monitoramento.funcao.getId() &&
				this.aprovacaoHora.getHorasTrabalhadas().stream().anyMatch(x->x.getProjetoEscala().getMonitor().getId() == usuarioLogado.getId())) || 
			(usuarioLogado.getFuncaoId() == FuncaoEnum.gerencia.funcao.getId() &&
				this.aprovacaoHora.getHorasTrabalhadas().stream().anyMatch(x->x.getProjetoEscala().getProjeto().getGerente().getId() == usuarioLogado.getId()));
		
    	if (usuarioLogado.getFuncaoId() != FuncaoEnum.administracao.funcao.getId() &&
   			usuarioLogado.getFuncaoId() != FuncaoEnum.financeiro.funcao.getId() &&
			usuarioLogado.getId() != this.aprovacaoHora.getPrestador().getId() && 
			!isMonitoriaGerencia) {
    		ModelAndView erroModelView = new ModelAndView("redirect:/error");
    		erroModelView.addObject("errorMessage", "Não permitido acesso a tela de horas trabalhadas");
            return erroModelView;
    	}

		horaTrabalhadaView.addObject("isDisableInsertCampos", true);   
    	this.aprovacaoHora.setDadosAcessoAprovacaoHoras(new DadosAcessoAprovacaoHoras(this.aprovacaoHora, usuarioLogado, funcaoConfiguracaoService.findAll()));
    	if ((LocalDate.now().getYear() + LocalDate.now().getMonthValue()) <= (this.aprovacaoHora.getData().getYear() + this.aprovacaoHora.getData().getMonthValue()) ||
    			!this.aprovacaoHora.getDadosAcessoAprovacaoHoras().getDadosAcesso()) {
    		horaTrabalhadaView.addObject("isDisableCampos", true);    		
    	}  
    	
    	this.aprovacaoHora.setTotalHoras(this.aprovacaoHora.getDadosAcessoAprovacaoHoras().getTotalHoras());
    	this.aprovacaoHora.setTotalValor(this.aprovacaoHora.getDadosAcessoAprovacaoHoras().getTotalValor());
		
		this.projetoEscalas = this.projetoEscalas != null ? this.projetoEscalas : this.projetoEscalaService.findAllByPermissao();
		this.horaTrabalhadaView.addObject("escalas", projetoEscalas);

		//this.horaAprovacaoId = id;
		
		this.horasTrabalhadas = this.horaTrabalhadaService.findByHoraAprovacaoId(id);	
		this.horaTrabalhadaView.addObject("aprovacaoHora", this.aprovacaoHora);		
		this.horaTrabalhadaView.addObject("horaTrabalhada", new HoraTrabalhada());		
		this.horaTrabalhadaView.addObject("horasTrabalhadas", horasTrabalhadas);	
		return this.horaTrabalhadaView;
	}
	
	@PostMapping("horasTrabalhadas/{id}")
	public ModelAndView horaTrabalhada(
			@Valid @ModelAttribute("horaTrabalhada") HoraTrabalhada horaTrabalhada,
			BindingResult result) {

		System.out.println("submit hora trabalhada"); 
		horaTrabalhadaView.addObject("isDisableInsertCampos", false);    	
    	
		this.horaTrabalhadaView.addObject("escalas", this.projetoEscalas);
		this.horaTrabalhadaView.addObject("aprovacaoHora", this.aprovacaoHora);	
		this.horaTrabalhadaView.addObject("horaTrabalhada", horaTrabalhada);		
		this.horaTrabalhadaView.addObject("horasTrabalhadas", this.horasTrabalhadas);	

		//this.horaAprovacaoId = id; 

		horaTrabalhadaView.addObject("errorMessage", null);
		boolean hasErrors = result.hasErrors();
		System.out.println(hasErrors);  
		horaTrabalhadaView.addObject("result", result);
		
    	if(hasErrors) {
            return horaTrabalhadaView;
        }    		
    	
    	horaTrabalhada.setHoraAprovacao(this.aprovacaoHora);
    	if (horaTrabalhada.getId() == -1) {
    		horaTrabalhada.setId(0);
    	}
    	
    	String error = this.horaTrabalhadaService.setAndSave(horaTrabalhada, horaTrabalhada.getId() == 0 ? null : this.horasTrabalhadas.stream().filter(x -> x.getId() == horaTrabalhada.getId()).findFirst().orElseGet(null));
    	if(!error.isEmpty()) {
        	if (horaTrabalhada.getId() == 0) {
        		horaTrabalhada.setId(-1);
        	}
    		horaTrabalhadaView.addObject("errorMessage", error);
            return horaTrabalhadaView;
        }    		
    	
		return new ModelAndView("redirect:/horasTrabalhadas/"+ horaTrabalhada.getHoraAprovacao().getId());
	}
	
	@GetMapping("horas/aprovar/{id}")
	public ModelAndView horaAprovar(
			@PathVariable("id") long id,
			@RequestParam(value = "aprovar", defaultValue = "true") boolean aprovar,
			@RequestParam(value = "motivo", defaultValue = "") String motivo) throws Exception {
		
		
		this.horaAprovacaoService.updateAprovacao(aprovar, id, motivo, this.aprovacaoHora);

		return new ModelAndView("redirect:/horasTrabalhadas/"+id);
	}
	
	@GetMapping("horas/gerarCsv")
    public ResponseEntity<Resource> downloadCSV(
    		@RequestParam("itens") String itens,
			HttpServletResponse response) throws IOException {

			if (itens == null || itens.trim().isEmpty()) {
				return null;
			}
			
		 	Path pasta = storageService.getTempPath();
            Files.createDirectories(pasta);

    		Usuario usuarioLogado = ((Usuario)session.getAttribute("usuarioLogado"));
	        List<Banco> bancos =  this.referenciasService.getBancos();
	        String bancoNumero = bancos.stream().filter(x->x.getId() == this.banco).findFirst().get().getNumero();
	        
            String fileName = "csv-financeiro_banco" + bancoNumero + "_usuario" + usuarioLogado.getId() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss")) + ".csv";
            String fullFileName = pasta.toAbsolutePath() + "\\" + fileName;
            
            FileWriter writer = new FileWriter(fullFileName);	 
	        
	        GeradorCsv.writeLine(writer, Arrays.asList("Tipo", "Banco", "Agencia", "Conta", "Digito",  "Nome", "CPF", "Data", "Valor"));
	        	            		
			List<Long> itensLong = new ArrayList<Long>();
			for (String str : itens.split(",")) {
				
				HoraAprovacao horaAprovacao = this.horaAprovacaoService.findById(Long.valueOf(str));				
				if (horaAprovacao != null) {
					itensLong.add(Long.valueOf(str));				
			        GeradorCsv.writeLine(writer, 
			        		Arrays.asList(
			        				"1", 
			        				bancos.stream().filter(x->x.getId() == horaAprovacao.getPrestador().getBancoId()).findFirst().get().getNumero(),
			        				horaAprovacao.getPrestador().getAgencia(),
			        				horaAprovacao.getPrestador().getConta(),
			        				horaAprovacao.getPrestador().getDigito(),
			        				horaAprovacao.getPrestador().getNomeCompletoCadastradoBanco(),
			        				horaAprovacao.getPrestador().getCpf(),
			        				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
			        				String.valueOf(horaAprovacao.getTotalValor())));
				}
			}			

	        writer.flush();
	        writer.close();
	        

			if (itensLong.isEmpty()) {
				return null;
			}
	        
	        this.horaAprovacaoService.updateCsvGerado(itensLong);
	        
	        Resource file = storageService.loadAsResource(fullFileName);	        
	        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
	                "attachment; filename=\"" + fileName + "\"").body(file);
	 }
    
    @ModelAttribute("bancos")
    public List<Banco> bancos() {
       return this.referenciasService.getBancos();
    }
}
