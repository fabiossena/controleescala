package com.packageIxia.sistemaControleEscala.services.referencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.referencias.BancoDao;
import com.packageIxia.sistemaControleEscala.daos.referencias.CidadeDao;
import com.packageIxia.sistemaControleEscala.daos.referencias.EstadoDao;
import com.packageIxia.sistemaControleEscala.daos.referencias.MotivoAusenciaDao;
import com.packageIxia.sistemaControleEscala.daos.referencias.PaisDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.referencias.Banco;
import com.packageIxia.sistemaControleEscala.models.referencias.Cidade;
import com.packageIxia.sistemaControleEscala.models.referencias.DadoGenerico;
import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemana;
import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemanaEnum;
import com.packageIxia.sistemaControleEscala.models.referencias.Estado;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.referencias.MotivoAusencia;
import com.packageIxia.sistemaControleEscala.models.referencias.Pais;
import com.packageIxia.sistemaControleEscala.models.referencias.TipoApontamentoHoras;
import com.packageIxia.sistemaControleEscala.models.referencias.TipoApontamentoHorasEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

@Service
public class ReferenciasService {

    private BancoDao bancoDao;
	private CidadeDao cidadeDao;
	private PaisDao paisDao;
	private EstadoDao estadoDao;
	private MotivoAusenciaDao motivoAusenciaDao; 
	private HttpSession session;

	@Autowired
    public ReferenciasService (
    		BancoDao bancoDao,
    		CidadeDao cidadeDao,
    		PaisDao paisDao,
    		EstadoDao estadoDao, 
    		MotivoAusenciaDao motivoAusenciaDao, 
    		HttpSession session) {
    	
    	this.bancoDao = bancoDao;
    	this.cidadeDao = cidadeDao;
    	this.paisDao = paisDao;
    	this.estadoDao = estadoDao;  
		this.motivoAusenciaDao = motivoAusenciaDao;
    	this.session = session;   	
    }
    
	public List<Banco> getBancos() {
    	
    	List<Banco> list = null;
    	
    	Object object = session.getAttribute("bancos");
    	if (object != null) {
    		return Utilities.converterObjetoParaArrayTipo(session.getAttribute("bancos"), new Banco());
    	}
    	
    	list = Utilities.toList(this.bancoDao.findAll());
    	if (list == null || list.isEmpty()) {
    		return new ArrayList<Banco>();
    	}
    	
    	session.setAttribute("bancos", list);
    	return list;
    }
	
    public List<Funcao> getFuncoes() {
    	return Arrays.stream(FuncaoEnum.values()).map(FuncaoEnum::getFuncao).collect(Collectors.toList());
    }
    
    public List<Funcao> getFuncoes(int tipo) {
    	return Arrays.stream(FuncaoEnum.values()).map(FuncaoEnum::getFuncao).filter(x->x.getTipo() == tipo).collect(Collectors.toList());
    }
    
    public List<Pais> getPaises() {
    	
    	List<Pais> list = null;
    	
    	Object object = session.getAttribute("paises");
    	if (object != null) {
    		return Utilities.converterObjetoParaArrayTipo(session.getAttribute("paises"), new Pais());
    	}
    	
    	list = Utilities.toList(this.paisDao.findAll());

    	if (list == null || list.isEmpty()) {
    		return new ArrayList<Pais>();
    	}
    	
    	session.setAttribute("paises", list);
    	return list;
    }
    
    public List<Estado> getEstado(int idPais) {
    	
    	List<Estado> list = getEstados();
    	if (list == null || list.isEmpty()) {	  
    		return new ArrayList<Estado>();    		
    	}

    	return list.stream().filter(p-> p.getId() == idPais).collect(Collectors.toList());
    }
    
    public List<Estado> getEstados() {
    	
    	List<Estado> list = null;
    	
    	Object object = session.getAttribute("estados");
    	if (object != null) {
    		list = Utilities.converterObjetoParaArrayTipo(session.getAttribute("estados"), new Estado());
    	}
    	else {
    		list = Utilities.toList(this.estadoDao.findAll());
        	if (list == null || list.isEmpty()) {	  
        		return new ArrayList<Estado>();
        	}  	
        	
    		session.setAttribute("estados", list); 
    	}
    	
    	return list;
    }
    
    public List<Cidade> getCidade(int idEstado) {
    	
    	List<Cidade> list = getCidades();
    	if (list == null || list.isEmpty()) {	  
    		return new ArrayList<Cidade>();    		
    	}
    	
    	return list.stream().filter(p-> p.getId() == idEstado).collect(Collectors.toList());
    }
    
    public List<Cidade> getCidades() {
    	
    	List<Cidade> list = null;    	
    	Object object = session.getAttribute("cidades");
    	
    	if (object != null) {
    		list = Utilities.converterObjetoParaArrayTipo(session.getAttribute("cidades"), new Cidade());
    	}
    	else {
    		list = Utilities.toList(this.cidadeDao.findAll());
        	if (list == null || list.isEmpty()) {	  
        		return new ArrayList<Cidade>();
        	}  	
        	
    		session.setAttribute("cidades", list); 
    	}
    	
    	return list;
    }

	public List<DadoGenerico> getPeriodos() {
		List<DadoGenerico> list = new ArrayList<DadoGenerico>();
		list.add(new DadoGenerico(1, "Manhã"));
		list.add(new DadoGenerico(2, "Tarde"));
		return list;
	}

	public List<DiaSemana> getDiasSemana() {
    	return Arrays.stream(DiaSemanaEnum.values()).map(DiaSemanaEnum::getDiaSemana).collect(Collectors.toList());
	}
    
    public List<MotivoAusencia> getMotivosAusencia(int tipo) {
    	return Utilities.toList(this.motivoAusenciaDao.findAllByTipo(tipo));
    }
    
    public List<MotivoAusencia> getMotivosAusencia() {    	
    	List<MotivoAusencia> list = null;
    	
    	Object object = session.getAttribute("motivoAusencia");
    	if (object != null) {
    		list = Utilities.converterObjetoParaArrayTipo(session.getAttribute("motivoAusencia"), new MotivoAusencia());
    	}
    	else {
    		list = Utilities.toList(this.motivoAusenciaDao.findAll());
        	if (list == null || list.isEmpty()) {	  
        		return new ArrayList<MotivoAusencia>();
        	}  	
        	
    		session.setAttribute("motivoAusencia", list); 
    	}
    	
    	return list;
    }

	public List<TipoApontamentoHoras> getTipoApontamentoHoras() {
    	return Arrays.stream(TipoApontamentoHorasEnum.values()).map(TipoApontamentoHorasEnum::getTipoApontamentoHoras).collect(Collectors.toList());
	}

	public List<DadoGenerico> getPrioridades() {
		List<DadoGenerico> list = new ArrayList<DadoGenerico>();
		list.add(new DadoGenerico(1, "Média alta"));
		list.add(new DadoGenerico(2, "Alta"));
		list.add(new DadoGenerico(3, "Muito alta"));
		return list;
	}
}