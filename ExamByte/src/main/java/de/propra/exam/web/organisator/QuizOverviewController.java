package de.propra.exam.web.organisator;

import de.propra.exam.application.service.QuizService;
import de.propra.exam.application.service.annotations.OrganisatorOnly;
import de.propra.exam.domain.model.quiz.Quiz;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuizOverviewController {

    QuizService quizService;

    public QuizOverviewController(QuizService quizService) {
        this.quizService = quizService;
    }

    @OrganisatorOnly
    @GetMapping("/showall-quizzes")
    public String showAllQuizzes(Model model) {
        List<Quiz> allQuiz = quizService.findAllQuiz();
        model.addAttribute("allQuiz", allQuiz);
        return "quiz/showall-quizes";
    }

    @OrganisatorOnly
    @GetMapping("/quiz/{quizid}")
    public String showQuiz(Model model, @PathVariable long quizid) {
        Quiz quiz = quizService.findQuizById(quizid);
        model.addAttribute("quiz", quiz);
        return "quiz/show-quiz";
    }
}
