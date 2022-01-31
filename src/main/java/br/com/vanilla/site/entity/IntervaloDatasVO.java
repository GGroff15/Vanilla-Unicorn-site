package br.com.vanilla.site.entity;

import java.util.Calendar;

public class IntervaloDatasVO {

	private long dataInicial;
	private long dataFinal;

	public long getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(long dataInicial) {
		this.dataInicial = dataInicial;
	}

	public long getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(long dataFinal) {
		this.dataFinal = dataFinal;
	}

	@Override
	public String toString() {

		Calendar calendarInicial = Calendar.getInstance();
		calendarInicial.setTimeInMillis(dataInicial);

		Calendar calendarFinal = Calendar.getInstance();
		calendarFinal.setTimeInMillis(dataFinal);

		StringBuilder builder = new StringBuilder();

		builder.append("Data inicial: ");
		builder.append(calendarInicial.get(Calendar.DAY_OF_MONTH));
		builder.append("/");
		builder.append(calendarInicial.get(Calendar.MONTH));
		builder.append("/");
		builder.append(calendarInicial.get(Calendar.YEAR));
		builder.append(" ");
		builder.append("Data final: ");
		builder.append(calendarFinal.get(Calendar.DAY_OF_MONTH));
		builder.append("/");
		builder.append(calendarFinal.get(Calendar.MONTH));
		builder.append("/");
		builder.append(calendarFinal.get(Calendar.YEAR));

		return builder.toString();
	}
}
