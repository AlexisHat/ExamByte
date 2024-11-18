package de.propra.exam.quizcore;

import java.util.ArrayList;
import java.util.List;


public class Quiz {

    String quizName;
    List<Question> fragen = new ArrayList<>();

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public List<Question> getFragen() {
        return fragen;
    }
    public void addFrage(Question question){
        fragen.add(question);
    }
    public void setFragen(List<Question> fragen) {
        this.fragen = fragen;
    }
}
