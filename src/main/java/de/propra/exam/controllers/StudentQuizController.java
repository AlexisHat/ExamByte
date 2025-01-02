package de.propra.exam.controllers;

import de.propra.exam.DTO.QuizOverviewDTO;
import de.propra.exam.DTO.QuizStatus;
import de.propra.exam.application.service.QuizOverviewService;
import de.propra.exam.application.service.QuizService;
import de.propra.exam.domain.model.quiz.Quiz;
import de.propra.exam.domain.model.quizattempt.QuizAttempt;
import de.propra.exam.domain.service.AttemptRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class StudentQuizController {

    QuizOverviewService quizOverviewService;

    public StudentQuizController(QuizOverviewService quizOverviewService) {
        this.quizOverviewService = quizOverviewService;
    }

    @GetMapping("/student")
    public String getStudentTests(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Object id = principal.getAttribute("id");

        Long studentId = id != null ? Long.valueOf(id.toString()) : null;

        List<QuizOverviewDTO> overviewDTOs = quizOverviewService.getStudentQuizOverviews(studentId);

        model.addAttribute("quizs", overviewDTOs);
        return "student/landing";
    }
}
