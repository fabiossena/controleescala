package com.packageIxia.sistemaControleEscala.controllers.referencia;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.packageIxia.sistemaControleEscala.interfaces.referencias.IReferencias;
import com.packageIxia.sistemaControleEscala.models.referencias.Cidade;
import com.packageIxia.sistemaControleEscala.models.referencias.Estado;

@Controller
public class ReferenciasController {

	@Autowired
	public IReferencias referenciasRepository;
	
	@RequestMapping(value = "/referencias/estados", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Estado> estado(HttpServletRequest request) {
		System.out.println("estados");
        return referenciasRepository.getEstados();
	}
	
	@RequestMapping(value = "/referencias/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Cidade> cidade(HttpServletRequest request) {
		System.out.println("cidades");
        return referenciasRepository.getCidades();
	}
}
