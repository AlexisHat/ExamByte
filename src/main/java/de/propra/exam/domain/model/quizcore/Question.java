package de.propra.exam.domain.model.quizcore;

public abstract class Question {
    String titel;
    String aufgabenstellung;
    Integer punktzahl;

    public String getTitel(){
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAufgabenstellung() {
        return aufgabenstellung;
    }

    public void setAufgabenstellung(String aufgabenstellung) {
        this.aufgabenstellung = aufgabenstellung;
    }
}
