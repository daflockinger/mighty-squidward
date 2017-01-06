package com.flockinger.squidward.model;


public class Sender {
	private Long id;
	private String result;
	private String status;
	private String deadletter;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeadletter() {
		return deadletter;
	}
	public void setDeadletter(String deadletter) {
		this.deadletter = deadletter;
	}
}
