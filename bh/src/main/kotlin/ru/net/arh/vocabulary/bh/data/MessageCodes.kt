package ru.net.arh.vocabulary.bh.data

/**
 * Message codes for localized messages
 */
enum class MessageCodes(val messageCode: String) {
    PING("message.ping")
    ;

    override fun toString(): String {
        return messageCode
    }}