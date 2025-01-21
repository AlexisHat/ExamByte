package de.propra.quizevaluation.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.HashSet;
import java.util.Set;

public class Korrektor {
    @Id
    private Long id;
    private String githubId;
    private Set<Answer> textAufgaben;


    @PersistenceCreator
    public Korrektor(Long id, String githubId, Set<Answer> textAufgaben) {
        this.id = id;
        this.githubId = githubId;
        this.textAufgaben = textAufgaben;
    }

    public static Korrektor createDummy() {
        return new Korrektor(1L, "dummy", new HashSet<>());
    }

    public Korrektor(String githubId) {
        this.githubId = githubId;
    }


    public void addTextAufgaben(Answer answer) {
        textAufgaben.add(answer);
    }

    public int textAufgabenSize() {
        return textAufgaben.size();
    }
}
