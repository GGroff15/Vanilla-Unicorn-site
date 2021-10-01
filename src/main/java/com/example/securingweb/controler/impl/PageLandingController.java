package com.example.securingweb.controler.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.securingweb.controler.IController;

@Controller
public class PageLandingController implements IController {

	@Override
	@GetMapping("/")
	public String carregarPagina(Model model) {
		return "pageLanding";
	}

}
