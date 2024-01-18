package de.iu.herotozero.herotozero_backend.util;

public class StringResponse {
    private String nachricht;

    public StringResponse(String nachricht) {
        this.nachricht = nachricht;
    }

    // Getter und Setter
    public String getNachricht() {
        return nachricht;
    }

    public void setNachricht(String nachricht) {
        this.nachricht = nachricht;
    }
}
