package edu.idat.pe.project.security.dto;

public class MessageDto {

    private String message;
    private String picture;

    public MessageDto(String message, String picture) {
        this.message = message;
        this.picture = picture;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
