package ru.net.arh.vocabulary.bh.aop

/**
 * Method annotation.
 * When method annotated with this annotation has a Long parameter named chatId,
 * then before this method will be called method UserProfileService.clearSimpleMessageData(chatId)
 * @see ru.net.arh.vocabulary.bh.service.common.UserProfileService.clearSimpleMessageData
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClearSimpleMessageBeforeRunThisMethod()
