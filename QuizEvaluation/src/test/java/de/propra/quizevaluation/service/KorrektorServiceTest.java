package de.propra.quizevaluation.service;

import de.propra.quizevaluation.domain.Answer;
import de.propra.quizevaluation.domain.Korrektor;
import de.propra.quizevaluation.domain.service.KorrektorRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class KorrektorServiceTest {

    @Test
    @DisplayName("wenn ein Korrektor im System ist, werden ihm alle Fragen zugeteilt")
    void test_01() {
        KorrektorRepo mockRepo = mock(KorrektorRepo.class);
        Korrektor dummy = Korrektor.createDummy();
        when(mockRepo.findAll()).thenReturn(new ArrayList<>(List.of(dummy))); // Modifizierbare Liste

        KorrektorService service = new KorrektorService(mockRepo);

        List<Answer> answers = new ArrayList<>();
        answers.add(Answer.createDummyAnswer());
        answers.add(Answer.createDummyAnswer());

        service.distributeTextAnswers(answers);

        assertThat(dummy.textAufgabenSize()).isEqualTo(2);
    }
}