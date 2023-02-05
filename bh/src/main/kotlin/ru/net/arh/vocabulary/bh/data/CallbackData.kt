package ru.net.arh.vocabulary.bh.data

data class CallbackData(
    /**
     * callback handler's name
     */
    val name: String,
    /**
     * data used by callback handler
     */
    val params: Map<String, Any?>
)