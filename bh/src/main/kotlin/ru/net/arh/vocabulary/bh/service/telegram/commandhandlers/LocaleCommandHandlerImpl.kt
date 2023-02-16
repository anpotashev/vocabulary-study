package ru.net.arh.vocabulary.bh.service.telegram.commandhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.ChooseLocaleSendmessageFactory

/**
 * Handler for /lang command
 */
@Service("lang")
class LocaleCommandHandlerImpl(
        private val chooseLocaleSendmessageFactory: ChooseLocaleSendmessageFactory
): CommandHandler {

    companion object {
        val log = LoggerFactory.getLogger(LocaleCommandHandlerImpl::class.java)
    }

    override fun onUpdate(params: String, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        log.debug("Starting processing 'lang' command. ChatId={}", chatId)
        return chooseLocaleSendmessageFactory.getInstance(chatId)
    }
}