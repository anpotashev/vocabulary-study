package ru.net.arh.vocabulary.bh.service.businesslogic.repository

import ru.net.arh.vocabulary.bh.data.QuestData

/**
 * Quest repository
 */
interface QuestRepository {
    /**
     * quest new word
     * @param chatId - telegram chat identifier
     * @return questData
     * @see ru.net.arh.vocabulary.bh.data.QuestData
     */
    fun questNewWord(chatId: Long): QuestData

    /**
     * save user quest result.
     * @param historyId - quest history identifier. Is uses to avoid repetitions.
     * @param success - quest result: true - test passed, false - quest failed, null - quest skipped
     */
    fun saveResult(historyId: Long, success: Boolean?)
}