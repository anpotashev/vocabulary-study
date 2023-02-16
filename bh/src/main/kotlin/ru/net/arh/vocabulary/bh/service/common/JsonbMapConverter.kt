package ru.net.arh.vocabulary.bh.service.common

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * Convertor for Map<String, Any?> field  jsonb field.
 */
@Converter(autoApply = true)
class JsonbMapConverter: AttributeConverter<Map<String, Any?>, String> {
    val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(data: Map<String, Any?>): String {
        return objectMapper.writeValueAsString(data)
    }

    override fun convertToEntityAttribute(data: String): Map<String, Any?> {
        val typeRef = object :
                TypeReference<HashMap<String, Any?>>() {}
        return objectMapper.readValue(data, typeRef)
    }
}