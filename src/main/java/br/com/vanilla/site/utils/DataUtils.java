package br.com.vanilla.site.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.vanilla.site.entity.IntervaloDTO;

public final class DataUtils {

	private DataUtils() {
	}

	public static Date dataAtual() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	public static Date trintaDiasAntes(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.roll(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	public static String formatarData(int dia, int mes, int ano) {
		return dia + "/" + mes + "/" + ano;
	}

	public static Date getDataPrimeiroDiaMes(int mes) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, mes);
		return calendar.getTime();
	}

	public static Date getDataUltimoDiaMes(int mes) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, mes);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static String formatar(String padrao, long dateInicial) {
		return new SimpleDateFormat(padrao).format(dateInicial);
	}

	public static IntervaloDTO obterIntervaloUltimosTrintaDias() {
		Date fim = DataUtils.dataAtual();
		Date inicio = DataUtils.trintaDiasAntes(fim);
		return new IntervaloDTO(inicio, fim);
	}

	public static Date converter(String data) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.parse(data);
	}

}
