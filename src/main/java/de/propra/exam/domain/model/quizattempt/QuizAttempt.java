package de.propra.exam.domain.model.quizattempt;

import de.propra.exam.domain.exceptions.QuizAlreadyEndedException;
import de.propra.exam.domain.exceptions.QuizNotStartedException;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.Quiz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class QuizAttempt {
    private final Long quizAttemptId;
    private final Long quizId;
    private final Long studentId;
    private final List<Antwort> antworten;
    boolean abgeschlossen;

    public QuizAttempt(Long quizAttemptId, Long quizId, Long studentId) {
        this.quizAttemptId = quizAttemptId;
        this.quizId = quizId;
        this.studentId = studentId;
        this.antworten = new ArrayList<>();
        this.abgeschlossen = false;
    }


    public void addOrUpdateAnswer(Long questionId, Antwort neueAntwort, Quiz quiz, LocalDateTime now) {
        if (abgeschlossen) {
            throw new IllegalStateException("Versuch ist bereits abgeschlossen.");
        }
        if (!quiz.isGestartet(now)) {
            throw new QuizNotStartedException("Das Quiz hat noch nicht begonnen.");
        }
        if (quiz.isBeendet(now)) {
            throw new QuizAlreadyEndedException("Das Quiz ist beendet");
        }
        Question frage = quiz.findeFrage(questionId);
        if (frage == null) {
            throw new IllegalArgumentException("Frage gehört nicht zu diesem Quiz.");
        }
        Antwort bestehende = findAnswerByFrageId(questionId);
        if (bestehende == null) {
            antworten.add(neueAntwort);
        } else {
            aktualisiereAntwort(bestehende, neueAntwort, now);
        }
    }

    void aktualisiereAntwort(Antwort bestehende, Antwort neueAntwort, LocalDateTime now) {
        if (bestehende instanceof FreitextAntwort bestehendeTextAntwort
                && neueAntwort instanceof FreitextAntwort neuTextAntwort) {
            bestehendeTextAntwort.setText(neuTextAntwort.getText(), now);
        } else if (bestehende instanceof MultipleChoiceAntwort bestehendeMultipleAntwort
                && neueAntwort instanceof MultipleChoiceAntwort neuMultipleAntwort) {
            bestehendeMultipleAntwort.setAusgewaehlteOptionen(neuMultipleAntwort.getAusgewaehlteOptionen(), now);
        } else {
            throw new IllegalStateException("Antwortentyp stimmt nicht überein.");
        }

    }

    public boolean isAbgeschlossen() {
        return abgeschlossen;
    }

    public Antwort findAnswerByFrageId(Long frageId) {
        return antworten.stream()
                .filter(antwort -> antwort.getFrageId().equals(frageId))
                .findFirst()
                .orElse(null);
    }

    public List<Antwort> getAntworten() {
        return new ArrayList<>(antworten);
    }

    public Long getQuizAttemptId() {
        return quizAttemptId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public Long getStudentId() {
        return studentId;
    }
}