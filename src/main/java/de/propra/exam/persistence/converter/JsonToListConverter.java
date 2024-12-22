package de.propra.exam.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.List;

//Die Klasse ist aus dem internet und mit ChatGPT geschrieben,
// da ich keinen besseren weg gefunden habe um Effektiv listen aus Strings bzw integer in der tabelle zuspeichern

@ReadingConverter
public class JsonToListConverter<T> implements Converter<String, List<T>> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<List<T>> typeReference;

    public JsonToListConverter(TypeReference<List<T>> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public List<T> convert(String source) {
        try {
            return objectMapper.readValue(source, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to List", e);
        }
    }
}