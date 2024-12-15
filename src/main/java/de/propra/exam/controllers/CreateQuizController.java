package de.propra.exam.controllers;

import de.propra.exam.application.service.QuizService;
import de.propra.exam.application.service.annotations.OrganisatorOnly;
import de.propra.exam.domain.model.quizcore.Quiz;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("quiz")
public class CreateQuizController {
    private final QuizService quizService;

    public CreateQuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @ModelAttribute("quiz")
    public Quiz getQuiz() {
        return quizService.createQuiz();
    }

    @OrganisatorOnly
    @GetMapping("/create-test")
    public String showCreateTestPage() {
        return "quiz/create-test";
    }

    @OrganisatorOnly
    @PostMapping("/create-test")
    public String createTest(@ModelAttribute("quiz") Quiz quiz) {
        return "redirect:/add-questions";
    }

    @OrganisatorOnly
    @GetMapping("/add-questions")
    public String showAddQuestionsPage(@ModelAttribute("quiz") Quiz quiz, Model model) {
        model.addAttribute("quiz", quiz);
        return "quiz/add-questions";
    }

    @OrganisatorOnly
    @PostMapping("/add-questions")
    public String addQuestion(@ModelAttribute("quiz") Quiz quiz,
                              @RequestParam String questionTitel,
                              @RequestParam String questionText,
                              @RequestParam Integer questionPoints,
                              @RequestParam String questionSolution
    ) {
        quizService.createNewQuestionInQuiz(quiz, questionTitel, questionText, questionPoints, questionSolution);
        return "redirect:/add-questions";
    }

    @OrganisatorOnly
    @PostMapping("/finalize-test")
    public String finalizeTest(@ModelAttribute("quiz") Quiz quiz) {
        quizService.addQuiz(quiz);
        // TODO: Quiz speichern, wenn eine Datenbank verfügbar ist
        return "redirect:/success";
    }
}