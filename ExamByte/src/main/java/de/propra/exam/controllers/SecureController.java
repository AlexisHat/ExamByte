package de.propra.exam.controllers;

import de.propra.exam.application.service.annotations.AtLeastKorrektor;
import de.propra.exam.application.service.annotations.AtLeastStudent;
import de.propra.exam.application.service.annotations.OrganisatorOnly;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SecureController {

    @GetMapping("/sec")
    public String getSecIndex() {
        return "role_test_sites/secIndex";
    }

    @AtLeastStudent
    @GetMapping("/studi")
    public String getStudent() {
        return "role_test_sites/student";
    }

    @AtLeastKorrektor
    @GetMapping("/korrektor")
    public String getKorrektor() {
        return "role_test_sites/korrektor";
    }

    @OrganisatorOnly
    @GetMapping("/organisator")
    public String getOrganisator() {
        return "role_test_sites/organisator";
    }

}
