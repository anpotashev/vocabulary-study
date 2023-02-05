package ru.net.arh.vocabulary.bh.service.businesslogic

import org.springframework.stereotype.Service
import ru.net.arh.vocabulary.bh.data.QuestData
import ru.net.arh.vocabulary.bh.service.businesslogic.repository.QuestRepository

@Service
class QuestServiceImpl(
    private val questRepository: QuestRepository
) : QuestService {
    override fun questNewWord(chatId: Long): QuestData {
        return questRepository.questNewWord(chatId)
    }

    override fun saveQuestResult(historyId: Long, success: Boolean?) {
        questRepository.saveResult(historyId, success)
    }
}