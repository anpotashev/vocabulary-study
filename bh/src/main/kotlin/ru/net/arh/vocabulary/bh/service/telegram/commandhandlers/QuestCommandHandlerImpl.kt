package ru.net.arh.vocabulary.bh.service.telegram.commandhandlers

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.service.businesslogic.QuestService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.QuestSendMessageFactory

@Service("quest")
class QuestCommandHandlerImpl(
    private val questService: QuestService,
    private val questSendMessageFactory: QuestSendMessageFactory
) : CommandHandler {

    override fun onUpdate(params: String, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        val questData = questService.questNewWord(chatId)
        return questSendMessageFactory.getInstance(chatId, questData)
    }
}