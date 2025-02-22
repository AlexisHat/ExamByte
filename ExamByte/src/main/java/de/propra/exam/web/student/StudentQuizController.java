package de.propra.exam.web.student;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.DTO.QuizOverviewDTO;
import de.propra.exam.application.service.*;
import de.propra.exam.application.service.annotations.StudentOnly;
import de.propra.exam.application.service.events.QuizSubmitEventService;
import de.propra.exam.domain.model.quiz.question.Question;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.model.quizattempt.answer.Answer;
import de.propra.exam.domain.model.quizattempt.answer.MultipleChoiceAnswer;
import de.propra.exam.domain.model.quizattempt.answer.TextAnswer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentQuizController {

    private final QuizService quizService;
    private final TestExecutionService testExecutionService;
    private final QuizOverviewService quizOverviewService;
    private final StudentService studentService;
    private final QuizValidationService quizValidationService;
    private final QuizSubmitEventService quizSubmitEventService;

    public StudentQuizController(QuizService quizService, QuizOverviewService quizOverviewService, TestExecutionService testExecutionService, StudentService studentService, QuizValidationService quizValidationService, QuizSubmitEventService quizSubmitEventService) {
        this.quizService = quizService;
        this.quizOverviewService = quizOverviewService;
        this.testExecutionService = testExecutionService;
        this.studentService = studentService;
        this.quizValidationService = quizValidationService;
        this.quizSubmitEventService = quizSubmitEventService;
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
    @GetMapping("/quiz/{id}/overview/{questionIndex}")
    public String seeQuizOverview(@PathVariable Long id,@PathVariable Integer questionIndex,
                                  Model model, @AuthenticationPrincipal OAuth2User principal) {
        getQuizExecutionContent(id, questionIndex, model, principal);
        return "student/overview";
    }



    @StudentOnly
    @GetMapping("/quiz/{id}/answer-question/{questionIndex}")
    public String editQuiz(@PathVariable Long id, @PathVariable Integer questionIndex, Model model, @AuthenticationPrincipal OAuth2User principal) {
        quizValidationService.validateQuizStartedAndNotEnded(quizService.findQuizById(id));

        getQuizExecutionContent(id, questionIndex, model, principal);

        return "student/attempt";
    }

    private void getQuizExecutionContent(@PathVariable Long id, @PathVariable Integer questionIndex, Model model, @AuthenticationPrincipal OAuth2User principal) {
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

    @StudentOnly
    @GetMapping("/quiz/{id}/complete")
    public String completeAttempt(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal) {
        QuizAttempt attempt = testExecutionService.findOrCreateQuizAttempt(id, getStudentId(principal));
        attempt.setAbgeschlossen(true);
        quizSubmitEventService.addSubmittedEvent(id,getStudentId(principal),attempt.getAntworten());
            return "redirect:/student";
    }

    private Long getStudentId(OAuth2User principal) {
        Object idO = principal.getAttribute("id");
        String id = String.valueOf(idO);
        return studentService.findStudentIdByGitHubId(id);
    }

}
