package de.propra.exam.application.service.clock;

import de.propra.exam.application.service.repository.Clock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClockImpl implements Clock {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
