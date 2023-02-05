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
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.ProcessQuestAnswerCallbackHandlerImpl
import java.text.MessageFormat

@Service
class QuestSendMessageFactory(
    private val messageTemplateProvider: MessageTemplateProvider,
    private val userProfileService: UserProfileService,
    private val callbackUtils: CallbackUtils
) {

    fun getInstance(chatId: Long, questData: QuestData): SendMessage {
        val locale = userProfileService.getUserProfile(chatId).locale
        val msg = MessageFormat(messageTemplateProvider.getMessage(locale, MessageCodes.QUEST_WORD))
            .format(arrayOf(questData.original, questData.translated))
        val okCallbackString = callbackUtils.saveCallbackDataString(
            ProcessQuestAnswerCallbackHandlerImpl.NAME,
            mapOf("success" to true, "historyId" to questData.historyId)
        )
        val skipCallbackString = callbackUtils.saveCallbackDataString(
            ProcessQuestAnswerCallbackHandlerImpl.NAME,
            mapOf("success" to null, "historyId" to questData.historyId)
        )
        val errorCallbackString = callbackUtils.saveCallbackDataString(
            ProcessQuestAnswerCallbackHandlerImpl.NAME,
            mapOf("success" to false, "historyId" to questData.historyId)
        )
        return SendMessage.builder()
            .chatId(chatId.toString())
            .text(msg)
            .parseMode(ParseMode.MARKDOWNV2)
            .replyMarkup(
                InlineKeyboardMarkup.builder()
                    .keyboard(
                        listOf(
                            listOf(
                                InlineKeyboardButton.builder()
                                    .text(messageTemplateProvider.getMessage(locale, MessageCodes.CAPTION_OK))
                                    .callbackData(okCallbackString)
                                    .build(),
                                InlineKeyboardButton.builder()
                                    .text(messageTemplateProvider.getMessage(locale, MessageCodes.CAPTION_SKIP))
                                    .callbackData(skipCallbackString)
                                    .build(),
                                InlineKeyboardButton.builder()
                                    .text(messageTemplateProvider.getMessage(locale, MessageCodes.CAPTION_ERROR))
                                    .callbackData(errorCallbackString)
                                    .build()
                            )
                        )
                    )
                    .build()
            )
            .build()
    }
}