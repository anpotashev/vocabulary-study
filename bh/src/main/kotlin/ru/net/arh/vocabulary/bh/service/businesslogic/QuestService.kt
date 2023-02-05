package ru.net.arh.vocabulary.bh.service.businesslogic

import ru.net.arh.vocabulary.bh.data.QuestData

interface QuestService {
    /**
     * Get data for new "quest"
     * @param chatId - telegram chat's identifier
     * @return data for "quest"
     */
    fun questNewWord(chatId: Long): QuestData

    /**
     * Save quest results
     * @param historyId - "quest" history identifier (value from quest_history)
     * Used to skip duplicate answers
     * success - quest's result: true - success, false - fail, null - skip quest
     */
    fun saveQuestResult(historyId: Long, success: Boolean?)
}