package de.propra.exam.controllers;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.DTO.QuizOverviewDTO;
import de.propra.exam.DTO.QuizStatus;
import de.propra.exam.application.service.QuizOverviewService;
import de.propra.exam.application.service.QuizService;
import de.propra.exam.application.service.StudentService;
import de.propra.exam.application.service.TestExecutionService;
import de.propra.exam.application.service.annotations.StudentOnly;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
import de.propra.exam.domain.service.AttemptRepository;
import de.propra.exam.persistence.repositories.impl.AttemptRepositoryImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class StudentQuizController {

    private final QuizService quizService;
    private final TestExecutionService testExecutionService;
    private final QuizOverviewService quizOverviewService;
    private final StudentService studentService;

    public StudentQuizController(QuizService quizService, QuizOverviewService quizOverviewService, TestExecutionService testExecutionService, StudentService studentService) {
        this.quizService = quizService;
        this.quizOverviewService = quizOverviewService;
        this.testExecutionService = testExecutionService;
        this.studentService = studentService;
    }

    @StudentOnly
    @GetMapping("/student")
    public String getStudentTests(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Long studentId = getStudentId(principal);

        List<QuizOverviewDTO> overviewDTOs = quizOverviewService.getStudentQuizOverviews(studentId);

        model.addAttribute("quizs", overviewDTOs);
        return "student/landing";
    }

    @StudentOnly
    @GetMapping("/quiz/{id}/answer-question/{questionIndex}")
    public String editQuiz(@PathVariable Long id, @PathVariable Integer questionIndex, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Question question = quizService.getQuestion(id, questionIndex);
        int questionListLength = quizService.getQuestionListLength(id);
        QuestionDTO questionDTO = QuestionDTO.ofQuestion(question, questionIndex);

        Long studentId = getStudentId(principal);
        QuizAttempt quizAttempt = testExecutionService.findOrCreateQuizAttempt(id, studentId);
        Optional<Answer> existingAnswerOptional = quizAttempt.getAnswerByQuestionId(question.getQuestionId());

        existingAnswerOptional.ifPresent(existingAnswer -> {
            if (existingAnswer instanceof TextAnswer) {
                model.addAttribute("existingAnswer",
                        ((TextAnswer) existingAnswer).getText());
            } else if (existingAnswer instanceof MultipleChoiceAnswer) {
                model.addAttribute("existingAnswer", String.join(",",
                        ((MultipleChoiceAnswer) existingAnswer).getAusgewaehlteOptionen()));
            }
        });


        model.addAttribute("question", questionDTO);
        model.addAttribute("quizID", id);
        model.addAttribute("totalQuestions", questionListLength);

        return "student/attempt";
    }

    @StudentOnly
    @PostMapping("/quiz/submit/text/{id}/{questionIndex}")
    public String submitAnswerText(@PathVariable Long id, @PathVariable Integer questionIndex,
                               @RequestParam String answer, @AuthenticationPrincipal OAuth2User principal) {
        Long studentId = getStudentId(principal);
        Question question = quizService.getQuestion(id, questionIndex);
        Long questionId = question.getQuestionId();

        testExecutionService.submitAnswer(id,studentId,questionId,answer);

        int questionListLength = quizService.getQuestionListLength(id);

        int urlQuestionIndex = (questionIndex < questionListLength) ? (questionIndex + 1) : questionIndex;


        return "redirect:/quiz/" + id + "/answer-question/" + urlQuestionIndex;
    }

    @StudentOnly
    @PostMapping("/quiz/submit/multiple/{id}/{questionIndex}")
    public String submitMultiple(@PathVariable Long id, @PathVariable Integer questionIndex,
            @RequestParam(required = false) List<String> answer, @AuthenticationPrincipal OAuth2User principal) {
        Long studentId = getStudentId(principal);
        Question question = quizService.getQuestion(id, questionIndex);
        Long questionId = question.getQuestionId();

        String answerContent = answer != null ? String.join(",", answer) : "";

        testExecutionService.submitAnswer(id, studentId, questionId, answerContent);

        int questionListLength = quizService.getQuestionListLength(id);

        int urlQuestionIndex = (questionIndex < questionListLength ) ? (questionIndex + 1) : questionIndex;

        return "redirect:/quiz/" + id + "/answer-question/" + urlQuestionIndex;
    }

    private Long getStudentId(OAuth2User principal) {
        Object idO = principal.getAttribute("id");
        String id = String.valueOf(idO);
        return studentService.findStudentIdByGitHubId(id);
    }
}
