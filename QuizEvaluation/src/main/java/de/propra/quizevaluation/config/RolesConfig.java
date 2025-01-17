package de.propra.quizevaluation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "roles")
public class RolesConfig {

    private List<String> korrektor;

    private List<String> organisator;

    public List<String> getOrganisator() {
        return organisator;
    }

    public void setOrganisator(List<String> organisator) {
        this.organisator = organisator;
    }

    public List<String> getKorrektor() {
        return korrektor;
    }

    public void setKorrektor(List<String> korrektor) {
        this.korrektor = korrektor;
    }
}
