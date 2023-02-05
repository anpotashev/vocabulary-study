package ru.net.arh.vocabulary.bh.endpoint

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.objects.Update

@RestController
@Profile("webhook")
class WebHookController(
    val bot: TelegramWebhookBot
) {
    @PostMapping("/")
    fun webhook(@RequestBody update: Update) =
        bot.onWebhookUpdateReceived(update)
}