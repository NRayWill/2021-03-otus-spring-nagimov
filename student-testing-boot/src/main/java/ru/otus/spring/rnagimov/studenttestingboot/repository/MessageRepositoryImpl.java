package ru.otus.spring.rnagimov.studenttestingboot.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageRepositoryImpl implements MessageRepository {

    private final MessageSource messageSource;
    private final String locale;

    public MessageRepositoryImpl(MessageSource messageSource, @Value("${locale}") String locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String getMessage(String key) throws NoSuchMessageException {
        return getMessage(key, null);
    }

    @Override
    public String getMessage(String key, Object[] args) throws NoSuchMessageException {
        return messageSource.getMessage(key, args, locale.equals("default") ? Locale.getDefault() : Locale.forLanguageTag(locale));
    }
}
