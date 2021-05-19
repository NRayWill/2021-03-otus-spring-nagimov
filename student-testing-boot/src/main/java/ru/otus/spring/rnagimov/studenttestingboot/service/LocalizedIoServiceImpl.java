package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

/**
 * @see LocalizedIoService
 */
@Service
public class LocalizedIoServiceImpl implements LocalizedIoService {

    private final IoService io;
    private final MessageService messageService;

    public LocalizedIoServiceImpl(IoService io, MessageService messageService) {
        this.io = io;
        this.messageService = messageService;
    }

    @Override
    public void printLocalizedMessage(String messageId) {
        io.printLn(messageService.getMessage(messageId));
    }

    @Override
    public void printLocalizedMessage(String messageId, Object... args) {
        io.printLn(messageService.getMessage(messageId, args));
    }

    @Override
    public String getMessage(String key) throws NoSuchMessageException {
        return messageService.getMessage(key, (Object) null);
    }
}
