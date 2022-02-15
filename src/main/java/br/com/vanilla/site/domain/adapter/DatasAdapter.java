package br.com.vanilla.site.domain.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import br.com.vanilla.site.domain.entity.DatasPesquisaVO;
import br.com.vanilla.site.domain.entity.IntervaloDTO;

/**
 * Metodos de conversao da classe VO DatasPesquisaVO para IntervaloDatasVO e
 * vice-versa
 * 
 * @author guilh
 *
 */
@Component
public class DatasAdapter {

	DateFormat dateFormat;

	public DatasAdapter() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	public IntervaloDTO converterDatasPesquisaVoParaIntervaloDatasVo(DatasPesquisaVO datasPesquisa)
			throws ParseException {
		Date inicio = dateFormat.parse(datasPesquisa.getDataInicial());
		Date fim = dateFormat.parse(datasPesquisa.getDataFinal());
		return new IntervaloDTO(inicio, fim);
	}

	public DatasPesquisaVO converterIntervaloDatasVoParaDatasPesquisaVo(IntervaloDTO intervalo) {
		String dataInicial = dateFormat.format(intervalo.getInicio());
		String dataFinal = dateFormat.format(intervalo.getFim());
		return new DatasPesquisaVO(dataInicial, dataFinal);
	}

}
