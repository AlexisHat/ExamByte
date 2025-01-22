package de.propra.exam.controllers.organisator;

import de.propra.exam.DTO.QuestionDTO;
import de.propra.exam.application.service.QuizService;
import de.propra.exam.application.service.annotations.OrganisatorOnly;
import de.propra.exam.application.service.events.QuizCreateEventService;
import de.propra.exam.domain.model.quiz.Quiz;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("quiz")
public class CreateQuizController {

    private final QuizService quizService;
    private final QuizCreateEventService quizCreateEventService;

    public CreateQuizController(QuizService quizService, QuizCreateEventService quizCreateEventService) {
        this.quizService = quizService;
        this.quizCreateEventService = quizCreateEventService;
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
        model.addAttribute("questionDTO", new QuestionDTO());
        model.addAttribute("quiz", quiz);
        return "quiz/add-questions";
    }

    @OrganisatorOnly
    @PostMapping("/add-questions")
    public String addQuestion(@ModelAttribute("quiz") Quiz quiz, QuestionDTO questionDTO) {
        quizService.createNewQuestionInQuiz(quiz, questionDTO);
        return "redirect:/add-questions";
    }

    @OrganisatorOnly
    @PostMapping("/finalize-quiz")
    public String finalizeTest(@ModelAttribute("quiz") Quiz quiz) {
        quizService.addQuiz(quiz);
        return "redirect:/success";
    }

    @OrganisatorOnly
    @GetMapping("/success")
    public String showSuccessPage() {
        return "quiz/success";
    }
}