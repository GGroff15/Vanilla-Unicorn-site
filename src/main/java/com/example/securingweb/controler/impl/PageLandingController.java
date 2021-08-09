package com.example.securingweb.controler.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.securingweb.controler.AbstractController;

@Controller
public class PageLandingController extends AbstractController {

	@GetMapping("/")
	public String carregarPagina(Model model) {
		return "pageLanding";
	}
	
}
