package de.propra.exam.controllers;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.DTO.QuizOverviewDTO;
import de.propra.exam.DTO.QuizStatus;
import de.propra.exam.application.service.QuizOverviewService;
import de.propra.exam.application.service.QuizService;
import de.propra.exam.application.service.TestExecutionService;
import de.propra.exam.application.service.annotations.StudentOnly;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quiz.question.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quiz.question.TextQuestion;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
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

@Controller
public class StudentQuizController {

    private final QuizService quizService;
    private final TestExecutionService testExecutionService;
    private final QuizOverviewService quizOverviewService;

    public StudentQuizController(QuizService quizService, QuizOverviewService quizOverviewService, TestExecutionService testExecutionService) {
        this.quizService = quizService;
        this.quizOverviewService = quizOverviewService;
        this.testExecutionService = testExecutionService;
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
    public String editQuiz(@PathVariable Long id, @PathVariable Integer questionIndex, Model model) {
        Question question = quizService.getQuestion(id, questionIndex);
        int questionListLength = quizService.getQuestionListLength(id);
        QuestionDTO questionDTO = QuestionDTO.ofQuestion(question, questionIndex);

        model.addAttribute("question", questionDTO);
        model.addAttribute("quizID", id);
        model.addAttribute("totalQuestions", questionListLength);

        return "student/attempt";
    }

//    @StudentOnly
//    @PostMapping("/quiz/submit/{id}/{questionIndex}")
//    public String submitAnswer(@PathVariable Long id, @PathVariable Integer questionIndex,
//                               @RequestParam String answer, @AuthenticationPrincipal OAuth2User principal) {
//        Long studentId = getStudentId(principal);
//
//        QuizAttempt attempt = testExecutionService.findOrCreateQuizAttempt(id, studentId);
//        attempt.addAnswer(questionIndex, answer);
//
//        testExecutionService.saveQuizAttempt(attempt);
//
//        return "redirect:/quiz/edit/" + id + "/" + (questionIndex + 1);
//    }

    private Long getStudentId(OAuth2User principal) {
        Object id = principal.getAttribute("id");
        return id != null ? Long.valueOf(id.toString()) : null;
    }

}
