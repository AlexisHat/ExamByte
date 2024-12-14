package de.propra.exam.domain.model.quizcore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Quiz {
    private Long id;
    String quizName;
    LocalDateTime startzeit;
    LocalDateTime endzeit;
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

    public Long getId() {
        return id;
    }
}
