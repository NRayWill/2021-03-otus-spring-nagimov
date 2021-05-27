package ru.otus.spring.rnagimov.studenttestingshell.service;

import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;

public interface MessageService {
    String getMessage(String var1) throws NoSuchMessageException;
    String getMessage(String var1, @Nullable Object... var2) throws NoSuchMessageException;
}
