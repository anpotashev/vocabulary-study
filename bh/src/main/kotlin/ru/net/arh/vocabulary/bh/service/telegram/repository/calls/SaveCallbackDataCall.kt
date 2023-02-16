package ru.net.arh.vocabulary.bh.service.telegram.repository.calls

import org.springframework.jdbc.core.SqlParameter
import org.springframework.jdbc.`object`.SqlUpdate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import ru.net.arh.vocabulary.bh.service.common.JsonbSerializer
import java.sql.Types
import javax.sql.DataSource

@Component
class SaveCallbackDataCall(
    dataSource: DataSource,
    private val jsonbSerializer: JsonbSerializer
) : SqlUpdate() {

    init {
        setDataSource(dataSource)
        val sql = """
            insert into callback_data(callback_name, callback_data) 
            values (:p_name, :p_data)
        """
        setSql(sql)

        setGeneratedKeysColumnNames("id")
        declareParameter(SqlParameter("p_name", Types.VARCHAR))
        declareParameter(SqlParameter("p_data", Types.OTHER))
    }

    fun exec(name: String, data: Map<String, Any?>): Long {
        val params = hashMapOf(
            "p_name" to name,
            "p_data" to jsonbSerializer.jsonb(data)
        )
        val keyHolder = GeneratedKeyHolder()
        updateByNamedParam(params, keyHolder)
        return keyHolder.key as Long
    }
}
