package ru.net.arh.vocabulary.bh.service.common

import com.fasterxml.jackson.databind.ObjectMapper
import org.postgresql.util.PGobject
import org.springframework.stereotype.Component

@Component
class JsonbSerializerImpl(
    private val objectMapper: ObjectMapper
) : JsonbSerializer {

    override fun jsonb(value: Any?): PGobject =
        PGobject().apply {
            type = "jsonb"
            this.value = objectMapper.writeValueAsString(value)
        }

}
