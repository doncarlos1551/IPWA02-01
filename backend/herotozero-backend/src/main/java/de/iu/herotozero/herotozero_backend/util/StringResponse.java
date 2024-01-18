package de.iu.herotozero.herotozero_backend.util;

public class StringResponse {
    private String nachricht;

    public StringResponse(String message) {
        this.nachricht = message;
    }

    // Getter und Setter
    public String getMessage() {
        return nachricht;
    }

    public void setMessage(String message) {
        this.nachricht = message;
    }
}
