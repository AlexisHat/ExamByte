package de.propra.exam.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

//Die Klasse ist aus dem internet und mit ChatGPT geschrieben,
// da ich keinen besseren weg gefunden habe um Effektiv listen aus Strings bzw integer in der tabelle zuspeichern

public class Converters {
    public static JsonToListConverter<String> jsonToStringListConverter() {
        return new JsonToListConverter<>(new TypeReference<List<String>>() {});
    }

    public static JsonToListConverter<Integer> jsonToIntegerListConverter() {
        return new JsonToListConverter<>(new TypeReference<List<Integer>>() {});
    }
}
