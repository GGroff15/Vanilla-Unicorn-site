package br.com.vanilla.site;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.client.RestTemplate;

import br.com.vanilla.site.data.connector.IntegradorConector;
import br.com.vanilla.site.domain.adapter.DatasAdapter;
import br.com.vanilla.site.domain.entity.ConsumoViewData;
import br.com.vanilla.site.domain.entity.LeituraDTO;
import br.com.vanilla.site.domain.service.CadastroService;
import br.com.vanilla.site.domain.service.ConsumoService;
import br.com.vanilla.site.domain.service.facade.ConsumoFacade;
import br.com.vanilla.site.view.controler.impl.CadastroController;
import br.com.vanilla.site.view.controler.impl.CompararController;
import br.com.vanilla.site.view.controler.impl.ConfiguracaoController;
import br.com.vanilla.site.view.controler.impl.ConsultaController;

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
