package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.LocaleService
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.telegram.CallbackUtils
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.SetUserLocaleCallbackHandlerImpl

@Service
class ChooseLocaleSendmessageFactory(
        private val localeService: LocaleService,
        private val callbackUtils: CallbackUtils,
        private val messageTemplateProvider: MessageTemplateProvider
) {

    fun getInstance(chatId: Long): SendMessage {
        val chooseLocaleButtons = localeService.getAvailableLocales()
                .map {
                    it.getDisplayName(it) to callbackUtils.saveCallbackDataString(
                          SetUserLocaleCallbackHandlerImpl.NAME,
                            hashMapOf(
                                    "locale" to it
                            )
                    )
                }
                .map {
                    InlineKeyboardButton.builder()
                        .text(it.first)
                        .callbackData(it.second)
                        .build()
                }
        val msg = messageTemplateProvider.getMessage(chatId, MessageCodes.MESSAGE_CHOOSE_LANGUAGE)
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(chooseLocaleButtons.chunked(2))
                        .build())
                .build()
    }
}