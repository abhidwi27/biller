package com.app.biller.domain;

import org.springframework.stereotype.Component;

@Component
public class Tower {

	private int towerID;
	private String towerName;

	public int getTowerID() {
		return towerID;
	}

	public void setTowerID(int towerID) {
		this.towerID = towerID;
	}

	public String getTowerName() {
		return towerName;
	}

	public void setTowerName(String towerName) {
		this.towerName = towerName;
	}

}
