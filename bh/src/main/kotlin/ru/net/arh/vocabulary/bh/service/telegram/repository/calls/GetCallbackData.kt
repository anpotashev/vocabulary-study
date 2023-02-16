package ru.net.arh.vocabulary.bh.service.telegram.repository.calls

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.SqlParameter
import org.springframework.jdbc.`object`.GenericSqlQuery
import org.springframework.stereotype.Component
import ru.net.arh.vocabulary.bh.data.CallbackData
import java.sql.Types
import javax.sql.DataSource

@Component
class GetCallbackData(
    dataSource: DataSource,
    private val objectMapper: ObjectMapper
) : GenericSqlQuery<String>() {

    init {
        setDataSource(dataSource)
        val sql = """
            select 
            jsonb_build_object('name', callback_name) ||
            jsonb_build_object('params', callback_data) as p_out
            from callback_data
            where id = :p_id
        """
        setSql(sql)
        declareParameter(SqlParameter("p_id", Types.BIGINT))
        setRowMapper({ r, _ -> r.getString("p_out") })
    }

    fun exec(id: Long): CallbackData? {
        val params = hashMapOf(
            "p_id" to id
        )
        val out = findObjectByNamedParam(params)
        return objectMapper.readValue(out, CallbackData::class.java)
    }

}
