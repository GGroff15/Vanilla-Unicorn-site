package br.com.vanilla.site.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.vanilla.site.entity.IntervaloDTO;

public final class DataUtils {

	private DataUtils() {
	}

	private static final long TRINTA_DIAS_MILISEGUNDOS = 2592000000L;

	public static long dataAtual() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}

	public static long trintaDiasAntes(long data) {
		return data - TRINTA_DIAS_MILISEGUNDOS;
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

	public static String formatarData(String padrao, long dateInicial) {
		return new SimpleDateFormat(padrao).format(dateInicial);
	}

	public static IntervaloDTO obterIntervaloUltimosTrintaDias() {
		IntervaloDTO intervalo = new IntervaloDTO();
		Long dataAtual = DataUtils.dataAtual();
		Long trintaDiasAntes = DataUtils.trintaDiasAntes(dataAtual);
		intervalo.setInicio(new Date(trintaDiasAntes));
		intervalo.setFim(new Date(dataAtual));
		return intervalo;
	}

}
