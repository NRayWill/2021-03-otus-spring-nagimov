package ru.otus.spring.rnagimov.studenttestingshell.service;

import org.springframework.context.NoSuchMessageException;

/**
 * Фасад для вывода локальзованных сообщений
 */
public interface LocalizedIoService {
    void printLocalizedMessage(String messageId);
    void printLocalizedMessage(String messageId, Object... args);
    String getMessage(String var1) throws NoSuchMessageException;
}
