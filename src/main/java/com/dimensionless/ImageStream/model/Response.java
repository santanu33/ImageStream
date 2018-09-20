package com.dimensionless.ImageStream.model;


public class Response {

    private String content;
    private String isFirstImage;
    public String getIsFirstImage() {
		return isFirstImage;
	}

	public void setIsFirstImage(String isFirstImage) {
		this.isFirstImage = isFirstImage;
	}

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

	public Response() {
    }

    public Response(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}