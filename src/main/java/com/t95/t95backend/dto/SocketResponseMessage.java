package com.t95.t95backend.dto;

public class SocketResponseMessage {
	private String content;
	
	public SocketResponseMessage() {		
	}
	
	public SocketResponseMessage(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
