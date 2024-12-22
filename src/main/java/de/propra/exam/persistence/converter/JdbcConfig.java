package de.propra.exam.persistence.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;

import java.util.Arrays;

//Die Klasse ist aus dem internet und mit ChatGPT geschrieben,
// da ich keinen besseren weg gefunden habe um Effektiv listen aus Strings bzw integer in der tabelle zuspeichern

@Configuration
public class JdbcConfig {

    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(Arrays.asList(
                new ListToJsonConverter<>(), // Writing any List to JSON
                Converters.jsonToStringListConverter(), // Reading JSON to List<String>
                Converters.jsonToIntegerListConverter() // Reading JSON to List<Integer>
        ));
    }
}
