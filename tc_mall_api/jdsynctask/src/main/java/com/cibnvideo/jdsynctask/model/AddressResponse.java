package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;
import java.util.HashMap;

public class AddressResponse implements Serializable {
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public HashMap<String, Integer> getResult() {
		return result;
	}

	public void setResult(HashMap<String, Integer> result) {
		this.result = result;
	}

	private boolean success;
	private String resultMessage;
	private String resultCode;
	private HashMap<String, Integer> result;
}
