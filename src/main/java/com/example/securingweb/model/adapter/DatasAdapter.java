package com.example.securingweb.model.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.securingweb.model.entity.DatasPesquisaVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.utils.DataUtils;

/**
 * Metodos de conversao da classe VO DatasPesquisaVO para IntervaloDatasVO e
 * vice-versa
 * 
 * @author guilh
 *
 */
@Component
public class DatasAdapter {

	public IntervaloDatasVO converterDatasPesquisaVoParaIntervaloDatasVo(DatasPesquisaVO datasPesquisa) {

		IntervaloDatasVO intervaloDatasVO = new IntervaloDatasVO();

		intervaloDatasVO.setDataInicial(DataUtils.converterStringParaLong(datasPesquisa.getDataInicial()));
		intervaloDatasVO.setDataFinal(DataUtils.converterStringParaLong(datasPesquisa.getDataFinal()));

		return intervaloDatasVO;
	}

	public DatasPesquisaVO converterIntervaloDatasVoParaDatasPesquisaVo(IntervaloDatasVO datasPesquisa) {
		DatasPesquisaVO datasPesquisaVO = new DatasPesquisaVO();

		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		calendar.setTimeInMillis(datasPesquisa.getDataInicial());
		Date dateInicial = calendar.getTime();
		datasPesquisaVO.setDataInicial(dateFormat.format(dateInicial));

		calendar.setTimeInMillis(datasPesquisa.getDataFinal());
		Date dateFinal = calendar.getTime();
		datasPesquisaVO.setDataFinal(dateFormat.format(dateFinal));

		return datasPesquisaVO;
	}

}
