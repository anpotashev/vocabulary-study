package ru.net.arh.vocabulary.bh.service.businesslogic.repository.calls

import com.fasterxml.jackson.databind.ObjectMapper
import org.postgresql.util.PGobject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.SqlOutParameter
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import java.sql.Types
import javax.sql.DataSource

abstract class AbstractJdbcCall(
    dataSource: DataSource,
    functionName: String
) : SimpleJdbcCall(dataSource) {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    init {
        withFunctionName(functionName)
        withoutProcedureColumnMetaDataAccess()
        declareParameters(
            SqlOutParameter("p_err_code", Types.VARCHAR)
        )
    }

    fun checkForErrors(out: Map<String, Any>) {
        val errorCode = out["p_err_code"] as String? ?: return
        VocabularyErrorCode.values().firstOrNull { it.name == errorCode } ?: VocabularyErrorCode.UNKNOWN_ERROR
        throw VocabularyBhException(errorCode = VocabularyErrorCode.valueOf(errorCode))
    }

    /**
     * Extracts JSON object from response param.
     * @param out - execution result
     * @param paramName - parameter's name
     * @param clazz - mapping result class
     */
    fun <T> getJsonbValue(out: Map<String, Any>, paramName: String, clazz: Class<T>): T {
        return objectMapper.readValue((out.get(paramName) as PGobject).value, clazz)
    }
}