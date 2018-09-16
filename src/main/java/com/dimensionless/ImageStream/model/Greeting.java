package com.dimensionless.ImageStream.model;


public class Greeting {

    private String content;

    private byte[] bytes;
    
    
    public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}