package de.propra.exam.domain.model.quizcore;

public abstract class Question {
    String titel;

    String text;

    public String getTitel(){
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String questionType){
    }
}
