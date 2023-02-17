package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.data.Word
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.CallbackUtils
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.EditWordCallbackHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.DeleteWordCallbackHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.escape
import java.text.MessageFormat
import java.util.*

@Service
class ManageWordSendMessageFactory(
        private val messageTemplateProvider: MessageTemplateProvider,
        private val callbackUtils: CallbackUtils,
        private val userProfileService: UserProfileService,
){
    fun getInstance(chatId: Long, word: Word): SendMessage {
        val userProfile = userProfileService.getUserProfile(chatId)
        val msg = MessageFormat(messageTemplateProvider.getMessage(userProfile.locale, MessageCodes.MESSAGE_YOU_MANAGE_WORD))
                .format(arrayOf(word.original.escape(), word.translated.escape()))
        val deleteButton = inlineButton(word.id!!, MessageCodes.CAPTION_DELETE, DeleteWordCallbackHandlerImpl.NAME, userProfile.locale)
        val editButton = inlineButton(word.id!!, MessageCodes.CAPTION_EDIT, EditWordCallbackHandlerImpl.NAME, userProfile.locale)
        val buttons = listOf(listOf(deleteButton, editButton))
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(
                        InlineKeyboardMarkup.builder()
                                .keyboard(buttons)
                                .build())
                .build()
    }

    private fun inlineButton(wordId: Long, caption: MessageCodes, callbackBeanName: String, locale: Locale): InlineKeyboardButton {
        return InlineKeyboardButton.builder()
                .text(messageTemplateProvider.getMessage(locale, caption))
                .callbackData(callbackUtils.saveCallbackDataString(
                        callbackName = callbackBeanName,
                        params = hashMapOf(
                                "word_id" to wordId
                        )
                ))
                .build()
    }
}