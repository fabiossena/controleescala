package com.packageIxia.sistemaControleEscala.controllers.referencia;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	private ModelAndView modelView = new ModelAndView("errorView");
	
	@GetMapping(value = "error")
	public ModelAndView error() {
		return modelView;
	}
}
