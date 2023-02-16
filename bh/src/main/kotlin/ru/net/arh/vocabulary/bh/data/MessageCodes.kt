package ru.net.arh.vocabulary.bh.data

/**
 * Message codes for localized messages
 */
enum class MessageCodes(val messageCode: String) {
    MESSAGE_QUEST_WORD("message.quest"),
    MESSAGE_NO_DICTIONARIES("message.no_dictionaries"),
    MESSAGE_YOUR_DICTIONARIES("message.your_dictionaries"),
    MESSAGE_YOU_MANAGE_DICTIONARY("message.you_manage_dictionary"),
    MESSAGE_DICTIONARY_DELETED("message.dictionary_deleted"),
    MESSAGE_CONFIRM_DELETE_NON_EMPTY_DICTIONARY("message.confirm_delete_non_empty_dictionary"),
    MESSAGE_OPERATION_CANCELED("message.operation_canceled"),
    MESSAGE_ENTER_NEW_DICTIONARY_NAME("message.enter_new_dictionary_name"),
    MESSAGE_DICTIONARY_RENAMED("message.dictionary_renamed"),
    MESSAGE_CREATING_DICTIONARY("message.creating_dictionary"),
    MESSAGE_DICTIONARY_CREATED("message.dictionary_created"),
    MESSAGE_PING("message.ping"),
    MESSAGE_DICTIONARY_SET_ACTIVE("message.dictionary_set_active"),
    MESSAGE_CHOOSE_LANGUAGE("message.choose_language"),
    MESSAGE_LOCALE_SET("message.locale_set"),

    CAPTION_OK("caption.ok"),
    CAPTION_ERROR("caption.error"),
    CAPTION_SKIP("caption.skip"),
    CAPTION_CREATE("caption.create"),
    CAPTION_RENAME("caption.rename"),
    CAPTION_DELETE("caption.delete"),
    CAPTION_SET_ACTIVE("caption.set_active");

    override fun toString(): String {
        return messageCode
    }
}
