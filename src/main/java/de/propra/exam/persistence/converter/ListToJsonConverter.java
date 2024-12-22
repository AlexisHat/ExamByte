package de.propra.exam.persistence.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.List;


//Die Klasse ist aus dem internet und mit ChatGPT geschrieben,
// da ich keinen besseren weg gefunden habe um Effektiv listen aus Strings bzw integer in der tabelle zuspeichern

@WritingConverter
public class ListToJsonConverter<T> implements Converter<List<T>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(List<T> source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (Exception e) {
            throw new RuntimeException("Error converting List to JSON", e);
        }
    }
}
