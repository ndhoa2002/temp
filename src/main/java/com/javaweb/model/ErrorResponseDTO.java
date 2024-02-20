package com.javaweb.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseDTO {
	private String error;
	private List<String> detaiList = new ArrayList<>();
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<String> getDetaiList() {
		return detaiList;
	}
	public void setDetaiList(List<String> detaiList) {
		this.detaiList = detaiList;
	}

	
}
