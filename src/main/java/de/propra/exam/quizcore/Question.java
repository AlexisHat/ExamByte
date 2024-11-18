package de.propra.exam.quizcore;

public class Question {

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
