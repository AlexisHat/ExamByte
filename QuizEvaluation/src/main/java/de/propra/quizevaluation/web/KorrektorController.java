package de.propra.quizevaluation.web;

import de.propra.quizevaluation.config.annotations.KorrektorOnly;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@KorrektorOnly
public class KorrektorController {

    @GetMapping("/")
    public String index() {
        return "Hello Korrektor!";
    }
}
