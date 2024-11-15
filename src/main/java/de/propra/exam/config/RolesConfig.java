package de.propra.exam.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "roles")
public class RolesConfig {

    private List<String> student;
    private List<String> korrektor;
    private List<String> organisator;

    public List<String> getStudent() {
        return student;
    }

    public void setStudent(List<String> student) {
        this.student = student;
    }

    public List<String> getKorrektor() {
        return korrektor;
    }

    public void setKorrektor(List<String> korrektor) {
        this.korrektor = korrektor;
    }

    public List<String> getOrganisator() {
        return organisator;
    }

    public void setOrganisator(List<String> organisator) {
        this.organisator = organisator;
    }


}


