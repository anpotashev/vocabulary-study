package ru.net.arh.vocabulary.bh.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Service
import ru.net.arh.vocabulary.bh.service.common.UserProfileService

@Aspect
@Service
class AOPService(
        private val userProfileService: UserProfileService
) {

    /**
     * AOP pointcut
     * Methods, annotated ClearSimpleMessageBeforeRunThisMethod
     * @see ClearSimpleMessageBeforeRunThisMethod
     */
    @Pointcut("@annotation(ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod)")
    fun clearSimpleMessageBeforeRunThisMethod() {
    }


    /**
     * AOP advice
     * If method has a Long parameter called chatId,
     * before this method runs UserProfileService.clearSimpleMessageData
     * @see UserProfileService.clearSimpleMessageData
     */
    @Around("clearSimpleMessageBeforeRunThisMethod()")
    fun clearSimpleMessageBefore(pjp: ProceedingJoinPoint): Any? {
        val parameterNames = (pjp.signature as MethodSignature).parameterNames
        var chatId: Long? = null
        for ((index, arg) in pjp.args.withIndex()) {
            if (parameterNames.get(index) == "chatId") {
                chatId = arg as? Long
            }
        }
        if (chatId != null) userProfileService.clearSimpleMessageData(chatId)
        return pjp.proceed()
    }

}