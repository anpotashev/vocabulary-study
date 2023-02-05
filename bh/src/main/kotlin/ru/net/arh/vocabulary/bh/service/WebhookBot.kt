package ru.net.arh.vocabulary.bh.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.objects.Update

@Service
@Profile("webhook")
class WebhookBot(
    private val updateHandler: UpdateHandler
) : TelegramWebhookBot() {

    @Value("\${telegram.botName}")
    private val botName: String = ""

    @Value("\${telegram.token}")
    private val token: String = ""

    @Value("\${telegram.botPath}")
    private val botPath: String = ""

    override fun getBotToken() = token

    override fun getBotUsername() = botName

    override fun getBotPath() = botPath

    override fun onWebhookUpdateReceived(update: Update) =
        updateHandler.onUpdate(update)

}