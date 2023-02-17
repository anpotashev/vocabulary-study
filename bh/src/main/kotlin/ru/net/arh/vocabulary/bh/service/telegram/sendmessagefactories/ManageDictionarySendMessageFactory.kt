package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.net.arh.vocabulary.bh.data.Dictionary
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.CallbackUtils
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.ProcessManageDictionaryActionCallbackHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.ProcessManageDictionaryActionCallbackHandlerImpl.Actions.*
import ru.net.arh.vocabulary.bh.service.telegram.escape
import java.text.MessageFormat
import java.util.*

@Service
class ManageDictionarySendMessageFactory(
        private val callbackUtils: CallbackUtils,
        private val userProfileService: UserProfileService,
        private val messageTemplateProvider: MessageTemplateProvider
) {

    fun getInstance(chatId: Long, dictionary: Dictionary): SendMessage? {
        val userProfile = userProfileService.getUserProfile(chatId)
        val dictId = dictionary.id!!
        val renameButton = inlineKeyboardButton(userProfile.locale, dictId, RENAME, MessageCodes.CAPTION_RENAME)
        val deleteButton = inlineKeyboardButton(userProfile.locale, dictId, DELETE, MessageCodes.CAPTION_DELETE)
        val setActiveButton = if (userProfile.activeDictionary?.id != dictionary.id) {
            inlineKeyboardButton(userProfile.locale, dictId, SET_ACTIVE, MessageCodes.CAPTION_SET_ACTIVE)
        } else null
        val manageWordsButton = inlineKeyboardButton(userProfile.locale, dictId, ADD_WORDS, MessageCodes.CAPTION_ADD_WORDS)
        val buttons = listOf(renameButton, deleteButton, setActiveButton, manageWordsButton).filterNotNull()
        val msg = MessageFormat(messageTemplateProvider.getMessage(userProfile.locale, MessageCodes.MESSAGE_YOU_MANAGE_DICTIONARY))
                .format(arrayOf(dictionary.name.escape()))
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(
                        InlineKeyboardMarkup.builder()
                                .keyboard(listOf(buttons))
                                .build()
                )
                .build()
    }

    private fun inlineKeyboardButton(locale: Locale, dictionaryId: Long, action: ProcessManageDictionaryActionCallbackHandlerImpl.Actions, messageCode: MessageCodes) =
            InlineKeyboardButton.builder()
                    .text(messageTemplateProvider.getMessage(locale, messageCode))
                    .callbackData(getCallbackData(dictionaryId, action))
                    .build()

    fun getCallbackData(dictId: Long, action: ProcessManageDictionaryActionCallbackHandlerImpl.Actions) =
            callbackUtils.saveCallbackDataString(
                    ProcessManageDictionaryActionCallbackHandlerImpl.NAME,
                    mapOf(
                            "dict_id" to dictId,
                            "action" to action
                    )
            )
}