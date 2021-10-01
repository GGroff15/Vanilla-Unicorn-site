package com.example.securingweb.model.entity;

import java.io.Serializable;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.example.securingweb.model.constants.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ConsumoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1665652184757036842L;

	@Id
	private ObjectId id;
	private Double agua;
	private Double energia;
	private Long data;
	private int dia;
	private int mes;
	private int ano;
	private int tempoUso;

	@Override
	public String toString() {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();

		node.put(Constants.ID, getId().toString());
		node.put(Constants.CONSUMO_AGUA, Objects.toString(this.agua, "NaN"));
		node.put(Constants.CONSUMO_ENERGIA, Objects.toString(this.energia, "NaN"));
		node.put(Constants.CONSUMO_DATA, this.data);
		node.put(Constants.CONSUMO_DIA, this.dia);
		node.put(Constants.CONSUMO_MES, this.mes);
		node.put(Constants.CONSUMO_ANO, this.ano);
		node.put(Constants.CONSUMO_TEMPO_USO, this.tempoUso);

		String json = "";
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Double getAgua() {
		return agua;
	}

	public void setAgua(Double agua) {
		this.agua = agua;
	}

	public Double getEnergia() {
		return energia;
	}

	public void setEnergia(Double energia) {
		this.energia = energia;
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getTempoUso() {
		return tempoUso;
	}

	public void setTempoUso(int tempoUso) {
		this.tempoUso = tempoUso;
	}

}
