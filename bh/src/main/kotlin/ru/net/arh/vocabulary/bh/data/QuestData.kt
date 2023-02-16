package ru.net.arh.vocabulary.bh.data

/**
 * Quest data.
 * Data retrieving from db for one quest
 */
data class QuestData(

    /**
     * word identifier
     */
    val id: Long,

    /**
     * Quest history identifier (value from quest_history)
     * Used to skip duplicate answers
     */
    val historyId: Long,

    /**
     * original word or expression
     */
    val original: String,

    /**
     * translated word or expression
     */
    val translated: String
)
