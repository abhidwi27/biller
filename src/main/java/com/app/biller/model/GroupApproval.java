package com.app.biller.model;

import org.springframework.stereotype.Component;

@Component
public class GroupApproval {

	private int dmApprvoal;
	private int bamApproval;
	private int srBamApproval;
	private int pmoApproval;

	public int getDmApprvoal() {
		return dmApprvoal;
	}

	public void setDmApprvoal(int dmApprvoal) {
		this.dmApprvoal = dmApprvoal;
	}

	public int getBamApproval() {
		return bamApproval;
	}

	public void setBamApproval(int bamApproval) {
		this.bamApproval = bamApproval;
	}

	public int getSrBamApproval() {
		return srBamApproval;
	}

	public void setSrBamApproval(int srBamApproval) {
		this.srBamApproval = srBamApproval;
	}

	public int getPmoApproval() {
		return pmoApproval;
	}

	public void setPmoApproval(int pmoApproval) {
		this.pmoApproval = pmoApproval;
	}

}
