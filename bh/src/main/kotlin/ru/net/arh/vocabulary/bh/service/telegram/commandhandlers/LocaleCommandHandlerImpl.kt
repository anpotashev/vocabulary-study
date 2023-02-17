package ru.net.arh.vocabulary.bh.service.telegram.commandhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.ChooseLocaleSendMessageFactory

/**
 * Handler for /lang command
 */
@Service("lang")
class LocaleCommandHandlerImpl(
        private val chooseLocaleSendmessageFactory: ChooseLocaleSendMessageFactory
): CommandHandler {

    companion object {
        val log = LoggerFactory.getLogger(LocaleCommandHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(params: String, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        log.debug("Starting processing 'lang' command. ChatId={}", chatId)
        return chooseLocaleSendmessageFactory.getInstance(chatId)
    }
}