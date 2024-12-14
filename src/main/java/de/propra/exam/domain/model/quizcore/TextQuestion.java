package de.propra.exam.domain.model.quizcore;

public class TextQuestion extends Question{
    public String getMusterloesung() {
        return musterloesung;
    }

    public void setMusterloesung(String musterloesung) {
        this.musterloesung = musterloesung;
    }

    String musterloesung;
}
