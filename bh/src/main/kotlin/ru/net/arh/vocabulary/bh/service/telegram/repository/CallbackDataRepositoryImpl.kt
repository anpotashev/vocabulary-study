package ru.net.arh.vocabulary.bh.service.telegram.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.net.arh.vocabulary.bh.data.CallbackData
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.telegram.repository.calls.GetCallbackData
import ru.net.arh.vocabulary.bh.service.telegram.repository.calls.SaveCallbackDataCall

@Repository
@Transactional
class CallbackDataRepositoryImpl(
    private val saveCallbackDataCall: SaveCallbackDataCall,
    private val getCallbackDataCall: GetCallbackData
) : CallbackDataRepository {

    override fun saveCallbackData(callbackData: CallbackData): Long {
        return saveCallbackDataCall.exec(callbackData.name, callbackData.params)
    }

    override fun getCallbackData(id: Long): CallbackData {
        return getCallbackDataCall.exec(id) ?: throw VocabularyBhException(VocabularyErrorCode.UNKNOWN_ERROR)
    }
}