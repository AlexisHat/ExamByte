package de.propra.exam.controllers;

import de.propra.exam.application.service.QuizService;
import de.propra.exam.domain.model.quizcore.MultipleChoiceQuestion;
import de.propra.exam.domain.model.quizcore.Question;
import de.propra.exam.domain.model.quizcore.Quiz;
import de.propra.exam.domain.model.quizcore.TextQuestion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/create-test")
    public String showCreateTestPage() {
        return "quiz/test-erstellen";
    }

    @PostMapping("/create-test")
    public String createTest(@ModelAttribute("quiz") Quiz quiz) {
        return "redirect:/add-questions";
    }

    @GetMapping("/add-questions")
    public String showAddQuestionsPage(@ModelAttribute("quiz") Quiz quiz, Model model) {
        model.addAttribute("quiz", quiz);
        return "quiz/add-questions";
    }

    @PostMapping("/add-questions")
    public String addQuestion(@ModelAttribute("quiz") Quiz quiz,
                              @RequestParam String questionText,
                              @RequestParam String questionTitel,
                              @RequestParam(required = false) List<String> options) {

        quizService.createNewQuestionInQuiz(quiz, options, questionTitel, questionText);
        return "redirect:/add-questions";
    }

    @PostMapping("/finalize-test")
    public String finalizeTest(@ModelAttribute("quiz") Quiz quiz) {
        // TODO: Quiz speichern, wenn eine Datenbank verfügbar ist
        return "redirect:/success";
    }
}