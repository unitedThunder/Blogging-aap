package com.hemant.blog.payloads;

public class ImageResponse {

	private String fileName;
	private String message;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ImageResponse(String fileName, String message) {
		super();
		this.fileName = fileName;
		this.message = message;
	}
	
}
