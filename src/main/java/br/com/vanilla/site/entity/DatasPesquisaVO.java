package br.com.vanilla.site.entity;

public class DatasPesquisaVO {

	String dataInicial;
	String dataFinal;

	public DatasPesquisaVO() {
	}

	public DatasPesquisaVO(String dataInicial, String dataFinal) {
		super();
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

}
