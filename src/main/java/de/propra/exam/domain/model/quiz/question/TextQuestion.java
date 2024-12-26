package de.propra.exam.domain.model.quiz.question;

public class TextQuestion extends Question {
    String musterLoesung;
    public String getMusterLoesung() {
        return musterLoesung;
    }
    public void setMusterLoesung(String musterLoesung) {
        this.musterLoesung = musterLoesung;
    }
}
