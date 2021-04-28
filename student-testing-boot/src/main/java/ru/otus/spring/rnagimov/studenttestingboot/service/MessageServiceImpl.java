package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final String locale;

    public MessageServiceImpl(MessageSource messageSource, @Value("${locale}") String locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String getMessage(String key) throws NoSuchMessageException {
        return getMessage(key, (Object) null);
    }

    @Override
    public String getMessage(String key, Object... args) throws NoSuchMessageException {
        return messageSource.getMessage(key, args, locale.equals("default") ? Locale.getDefault() : Locale.forLanguageTag(locale));
    }
}
