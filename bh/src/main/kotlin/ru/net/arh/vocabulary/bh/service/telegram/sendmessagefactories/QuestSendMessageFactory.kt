package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.data.QuestData
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.CallbackUtils
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.ManageWordCallbackHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.ProcessQuestAnswerCallbackHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.escape
import java.text.MessageFormat
import java.util.*

@Service
class QuestSendMessageFactory(
    private val messageTemplateProvider: MessageTemplateProvider,
    private val userProfileService: UserProfileService,
    private val callbackUtils: CallbackUtils
) {

    fun getInstance(chatId: Long, questData: QuestData): SendMessage {
        val locale = userProfileService.getUserProfile(chatId).locale
        val msg = MessageFormat(messageTemplateProvider.getMessage(locale, MessageCodes.MESSAGE_QUEST_WORD))
            .format(arrayOf(questData.original.escape(), questData.translated.escape()))
        val okCallbackButton = inlineKeyboardButton(locale, questData.historyId, true, MessageCodes.CAPTION_OK)
        val skipCallbackButton = inlineKeyboardButton(locale, questData.historyId, null, MessageCodes.CAPTION_SKIP)
        val errorCallbackButton = inlineKeyboardButton(locale, questData.historyId, false, MessageCodes.CAPTION_ERROR)
        val manageWordCallbackButton = InlineKeyboardButton.builder()
                .text(messageTemplateProvider.getMessage(locale, MessageCodes.CAPTION_MANAGE_WORD))
                .callbackData(callbackUtils.saveCallbackDataString(
                        callbackName = ManageWordCallbackHandlerImpl.NAME,
                        params = hashMapOf(
                                "word_id" to questData.id
                        )
                ))
                .build()
        val buttons = listOf(listOf(okCallbackButton, skipCallbackButton, errorCallbackButton),
                listOf(manageWordCallbackButton))
        return SendMessage.builder()
            .chatId(chatId.toString())
            .text(msg)
            .parseMode(ParseMode.MARKDOWNV2)
            .replyMarkup(
                InlineKeyboardMarkup.builder()
                    .keyboard(buttons)
                    .build()
            )
            .build()
    }

    private fun inlineKeyboardButton(locale: Locale, historyId: Long, success: Boolean?, messageCode: MessageCodes) =
            InlineKeyboardButton.builder()
                    .text(messageTemplateProvider.getMessage(locale, messageCode))
                    .callbackData(callbackData(historyId, success))
                    .build()

    private fun callbackData(historyId: Long, success: Boolean?) = callbackUtils.saveCallbackDataString(
            ProcessQuestAnswerCallbackHandlerImpl.NAME,
            mapOf("success" to success, "historyId" to historyId)
    )
}