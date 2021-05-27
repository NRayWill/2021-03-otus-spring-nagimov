package ru.otus.spring.rnagimov.studenttestingshell.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.studenttestingshell.config.AppProperties;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final AppProperties appProperties;

    public MessageServiceImpl(MessageSource messageSource, AppProperties appProperties) {
        this.messageSource = messageSource;
        this.appProperties = appProperties;
    }

    @Override
    public String getMessage(String key) throws NoSuchMessageException {
        return getMessage(key, (Object) null);
    }

    @Override
    public String getMessage(String key, Object... args) throws NoSuchMessageException {
        return messageSource.getMessage(key, args, appProperties.getLocale().equals("default") ? Locale.getDefault() : Locale.forLanguageTag(appProperties.getLocale()));
    }
}
