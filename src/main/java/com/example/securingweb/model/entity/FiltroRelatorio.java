package com.example.securingweb.model.entity;

public class FiltroRelatorio {

	private IntervaloDatasVO intervaloDatasVO = new IntervaloDatasVO();
	private String username;

	public IntervaloDatasVO getIntervaloDatasVO() {
		return intervaloDatasVO;
	}

	public void setIntervaloDatasVO(IntervaloDatasVO intervaloDatasVO) {
		this.intervaloDatasVO = intervaloDatasVO;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
