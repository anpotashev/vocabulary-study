package ru.net.arh.vocabulary.bh.service.businesslogic.repository

import ru.net.arh.vocabulary.bh.data.QuestData

interface QuestRepository {
    /**
     * quest new word
     */
    fun questNewWord(chatId: Long): QuestData

    /**
     * save user's result
     */
    fun saveResult(historyId: Long, success: Boolean?)
}