package de.propra.exam.domain.model.quizattempt;

import de.propra.exam.domain.exceptions.QuizAlreadyEndedException;
import de.propra.exam.domain.exceptions.QuizNotStartedException;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class QuizAttempt {
    private final Long quizAttemptId;
    private final Long quizId;
    private final Long studentId;
    private List<Answer> antworten;
    boolean abgeschlossen;

    public QuizAttempt(Long quizAttemptId, Long quizId, Long studentId) {
        this.quizAttemptId = quizAttemptId;
        this.quizId = quizId;
        this.studentId = studentId;
        this.antworten = new ArrayList<>();
        this.abgeschlossen = false;
    }

    public QuizAttempt(Long quizAttemptId, Long quizId, Long studentId, List<Answer> antworten) {
        this.quizAttemptId = quizAttemptId;
        this.quizId = quizId;
        this.studentId = studentId;
        this.antworten = new ArrayList<>(antworten != null ? antworten : new ArrayList<>());
        this.abgeschlossen = false;
    }


    public void addOrUpdateAnswer(Long questionId, Answer newAnswer, Quiz quiz, LocalDateTime now) {
        if (abgeschlossen) {
            throw new IllegalStateException("Versuch ist bereits abgeschlossen.");
        }
        if (!quiz.isGestartet(now)) {
            throw new QuizNotStartedException("Das Quiz hat noch nicht begonnen.");
        }
        if (quiz.isBeendet(now)) {
            throw new QuizAlreadyEndedException("Das Quiz ist beendet");
        }
        Question frage = quiz.findQuestionById(questionId);
        if (frage == null) {
            throw new IllegalArgumentException("Frage gehört nicht zu diesem Quiz.");
        }
        Answer bestehende = findAnswerByFrageId(questionId);
        if (bestehende == null) {
            antworten.add(newAnswer);
        } else {
            aktualisiereAntwort(bestehende, newAnswer, now);
        }
    }

    void aktualisiereAntwort(Answer bestehende, Answer newAnswer, LocalDateTime now) {
        if (bestehende instanceof TextAnswer bestehendeTextAntwort
                && newAnswer instanceof TextAnswer neuTextAntwort) {
            bestehendeTextAntwort.setText(neuTextAntwort.getText(), now);
        } else if (bestehende instanceof MultipleChoiceAnswer bestehendeMultipleAntwort
                && newAnswer instanceof MultipleChoiceAnswer neuMultipleAntwort) {
            bestehendeMultipleAntwort.setAusgewaehlteOptionen(neuMultipleAntwort.getAusgewaehlteOptionen(), now);
        } else {
            throw new IllegalStateException("Antwortentyp stimmt nicht überein.");
        }

    }

    public boolean isAbgeschlossen() {
        return abgeschlossen;
    }

    public Answer findAnswerByFrageId(Long frageId) {
        return antworten.stream()
                .filter(answer -> answer.getFrageId().equals(frageId))
                .findFirst()
                .orElse(null);
    }

    public List<Answer> getAntworten() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QuizAttempt that = (QuizAttempt) o;
        return Objects.equals(quizAttemptId, that.quizAttemptId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(quizAttemptId);
    }

    public double getScore() {
        return 0.0;
    }

    public Optional<Answer> getAnswerByQuestionId(Long questionId) {
        return antworten.stream().filter(answer -> answer.getFrageId().equals(questionId)).findFirst();
    }
}