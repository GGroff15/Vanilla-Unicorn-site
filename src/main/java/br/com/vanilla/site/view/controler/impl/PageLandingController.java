package br.com.vanilla.site.view.controler.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageLandingController {

	@GetMapping("/")
	public String carregarPagina(Model model) {
		return "pageLanding";
	}

}
