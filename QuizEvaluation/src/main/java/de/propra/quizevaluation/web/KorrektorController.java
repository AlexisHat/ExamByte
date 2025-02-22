package de.propra.quizevaluation.web;

import de.propra.quizevaluation.config.annotations.KorrektorOnly;
import de.propra.quizevaluation.domain.Attempt.Answer;
import de.propra.quizevaluation.service.KorrektorService;
import de.propra.quizevaluation.web.DTO.TextAnswerCorrectionDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Controller
@KorrektorOnly
public class KorrektorController {

    private final KorrektorService korrektorService;

    public KorrektorController(KorrektorService korrektorService) {
        this.korrektorService = korrektorService;
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Long korrektorId = getKorrektorId(principal);
        List<Answer> answers = korrektorService.getAnswersForKorrektorWithId(korrektorId);

        List<TextAnswerCorrectionDTO> dto = korrektorService.createDtoFromAnswer(answers);
        model.addAttribute("answers", answers);
        return "index";
    }


    private Long getKorrektorId(OAuth2User principal) {
        Object idO = principal.getAttribute("id");
        String id = String.valueOf(idO);
        return korrektorService.findKorrektorIdByGitHubId(id);
    }
}
