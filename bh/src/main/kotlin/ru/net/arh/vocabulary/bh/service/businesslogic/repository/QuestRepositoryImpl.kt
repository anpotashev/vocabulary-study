package ru.net.arh.vocabulary.bh.service.businesslogic.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.net.arh.vocabulary.bh.data.QuestData
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.calls.QuestNewWordCall
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.calls.SaveQuestResultCall

@Repository
@Transactional
class QuestRepositoryImpl(
    private val questNewWordCall: QuestNewWordCall,
    private val saveQuestResultCall: SaveQuestResultCall,
) : QuestRepository {
    override fun questNewWord(chatId: Long): QuestData {
        return questNewWordCall.exec(chatId)
    }

    override fun saveResult(historyId: Long, success: Boolean?) {
        saveQuestResultCall.exec(historyId, success)
    }
}