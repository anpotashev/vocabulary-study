package ru.net.arh.vocabulary.bh.service.businesslogic.repository.calls

import org.springframework.jdbc.core.SqlParameter
import org.springframework.stereotype.Component
import java.sql.Types
import javax.sql.DataSource

@Component
class SaveQuestResultCall(
    dataSource: DataSource
) : AbstractJdbcCall(
    dataSource = dataSource,
    functionName = "save_quest_result"
) {

    init {
        declareParameters(
            SqlParameter("p_history_id", Types.BIGINT),
            SqlParameter("p_success", Types.BOOLEAN),
        )
    }

    fun exec(historyId: Long, success: Boolean?) {
        val params = hashMapOf(
            "p_history_id" to historyId,
            "p_success" to success
        )
        val out = execute(params)
        checkForErrors(out)
    }

}
