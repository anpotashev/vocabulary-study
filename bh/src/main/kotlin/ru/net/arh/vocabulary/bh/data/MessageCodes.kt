package ru.net.arh.vocabulary.bh.data

/**
 * Message codes for localized messages
 */
enum class MessageCodes(val messageCode: String) {
    QUEST_WORD("message.quest"),
    CAPTION_OK("caption.ok"),
    CAPTION_ERROR("caption.error"),
    CAPTION_SKIP("caption.skip"),
    PING("message.ping")
    ;

    override fun toString(): String {
        return messageCode
    }
}