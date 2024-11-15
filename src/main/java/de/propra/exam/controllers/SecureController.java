package de.propra.exam.controllers;

import de.propra.exam.config.annotations.KorrektorOnly;
import de.propra.exam.config.annotations.OrganisatorOnly;
import de.propra.exam.config.annotations.StudentOnly;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SecureController {

    @GetMapping("/sec")
    public String getSecIndex() {
        return "secIndex";
    }

    @StudentOnly
    @GetMapping("/student")
    public String getStudent() {
        return "student";
    }

    @KorrektorOnly
    @GetMapping("/korrektor")
    public String getKorrektor() {
        return "korrektor";
    }

    @OrganisatorOnly
    @GetMapping("/organisator")
    public String getOrganisator() {
        return "organisator";
    }

}
