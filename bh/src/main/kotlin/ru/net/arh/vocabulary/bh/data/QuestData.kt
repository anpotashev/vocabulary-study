package ru.net.arh.vocabulary.bh.data

/**
 * quest data
 */
data class QuestData(

    /**
     * Quest history identifier (value from quest_history)
     * Used to skip duplicate answers
     */
    val historyId: Long,
    /**
     * word's identifier
     */
    val id: Long,
    /**
     * original word or expression
     */
    val original: String,
    /**
     * translated word or expression
     */
    val translated: String
)
