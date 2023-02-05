package ru.net.arh.vocabulary.bh.service.businesslogic.repository.calls

import org.springframework.jdbc.core.SqlOutParameter
import org.springframework.jdbc.core.SqlParameter
import org.springframework.stereotype.Component
import ru.net.arh.vocabulary.bh.data.QuestData
import java.sql.Types
import javax.sql.DataSource

@Component
class QuestNewWordCall(
    dataSource: DataSource
) : AbstractJdbcCall(
    dataSource = dataSource,
    functionName = "quest_new_word"
) {

    init {
        declareParameters(
            SqlOutParameter("p_out", Types.OTHER),
            SqlParameter("p_chat_id", Types.BIGINT)
        )
    }

    fun exec(chatId: Long): QuestData {
        val params = hashMapOf(
            "p_chat_id" to chatId
        )
        val out = execute(params)
        checkForErrors(out)
        return getJsonbValue(out, "p_out", QuestData::class.java)
    }
}