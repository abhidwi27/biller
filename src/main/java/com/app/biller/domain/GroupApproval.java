package com.app.biller.domain;

import org.springframework.stereotype.Component;

@Component
public class GroupApproval {

	private int dmApproval;
	private int bamApproval;
	private int srBamApproval;
	private int pmoApproval;

	public int getDmApproval() {
		return dmApproval;
	}

	public void setDmApproval(int dmApproval) {
		this.dmApproval = dmApproval;
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
