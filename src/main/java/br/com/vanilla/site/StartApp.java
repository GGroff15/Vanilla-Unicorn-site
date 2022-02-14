package br.com.vanilla.site;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.client.RestTemplate;

import br.com.vanilla.site.connector.IntegradorConector;
import br.com.vanilla.site.controler.impl.CadastroController;
import br.com.vanilla.site.controler.impl.CompararController;
import br.com.vanilla.site.controler.impl.ConfiguracaoController;
import br.com.vanilla.site.controler.impl.ConsultaController;
import br.com.vanilla.site.entity.ConsumoViewData;
import br.com.vanilla.site.entity.LeituraDTO;
import br.com.vanilla.site.model.adapter.DatasAdapter;
import br.com.vanilla.site.service.CadastroService;
import br.com.vanilla.site.service.ConsumoService;
import br.com.vanilla.site.service.facade.ConsumoFacade;

@SpringBootApplication
public class StartApp {

	@Autowired
	private DatasAdapter datasAdapter;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private IntegradorConector integradorConnector;

	public static void main(String[] args) {
		SpringApplication.run(StartApp.class, args);
	}

	@SuppressWarnings("unused")
	@PostConstruct
	public void startup() {
		RestTemplate restTemplate = new RestTemplate();

		List<LeituraDTO> leiturasPeriodo = new ArrayList<>();
		ConsumoService consumoService = new ConsumoService(integradorConnector, leiturasPeriodo);
		ConsumoViewData consumo = new ConsumoViewData();
		ConsumoFacade consumoFacade = new ConsumoFacade(consumoService, consumo);
		ConsultaController consultaController = new ConsultaController(datasAdapter, consumoFacade);

		CadastroService cadastroService = new CadastroService(integradorConnector);
		ConfiguracaoController configuracaoController = new ConfiguracaoController(cadastroService);

		CompararController compararController = new CompararController(datasAdapter, consumoService);

		CadastroController cadastroController = new CadastroController(eventPublisher, cadastroService);
	}

}
