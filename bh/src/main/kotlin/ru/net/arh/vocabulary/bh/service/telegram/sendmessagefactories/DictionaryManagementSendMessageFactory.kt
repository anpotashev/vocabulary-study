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
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.CreateDictionaryCallbackHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.ProcessSelectDictionaryToManageCallbackHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.escape

@Service
class DictionaryManagementSendMessageFactory(
        private val messageTemplateProvider: MessageTemplateProvider,
        private val userProfileService: UserProfileService,
        private val callbackUtils: CallbackUtils,
) {
    fun getInstance(chatId: Long, dictionaries: List<Dictionary>): SendMessage {
        val userProfile = userProfileService.getUserProfile(chatId)
        val msg = if (dictionaries.isEmpty()) {
            messageTemplateProvider.getMessage(userProfile.locale, MessageCodes.MESSAGE_NO_DICTIONARIES)
        } else {
            messageTemplateProvider.getMessage(userProfile.locale, MessageCodes.MESSAGE_YOUR_DICTIONARIES)
        }
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg.escape())
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(
                        InlineKeyboardMarkup.builder()
                                .keyboard(
                                        dictionaries
                                                .map {
                                                    InlineKeyboardButton.builder()
                                                            .text(it.name)
                                                            .callbackData(callbackUtils.saveCallbackDataString(
                                                                    ProcessSelectDictionaryToManageCallbackHandlerImpl.NAME,
                                                                    mapOf("dict_id" to it.id)
                                                            ))
                                                            .build()
                                                }
                                                .chunked(2)
                                                .toMutableList()
                                                .apply {
                                                    add(listOf(
                                                            InlineKeyboardButton.builder()
                                                                    .text(messageTemplateProvider.getMessage(userProfile.locale, MessageCodes.CAPTION_CREATE))
                                                                    .callbackData(callbackUtils.saveCallbackDataString(
                                                                            CreateDictionaryCallbackHandlerImpl.NAME, emptyMap()
                                                                    ))
                                                                    .build()
                                                    ))
                                                }
                                )
                                .build()
                )
                .build()
    }
}