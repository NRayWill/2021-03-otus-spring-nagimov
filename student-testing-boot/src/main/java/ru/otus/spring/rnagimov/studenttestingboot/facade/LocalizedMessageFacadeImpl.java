package ru.otus.spring.rnagimov.studenttestingboot.facade;

import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.studenttestingboot.service.IoService;
import ru.otus.spring.rnagimov.studenttestingboot.service.MessageService;

@Service
public class LocalizedMessageFacadeImpl implements LocalizedMessageFacade {

    private final IoService io;
    private final MessageService messageService;

    public LocalizedMessageFacadeImpl(IoService io, MessageService messageService) {
        this.io = io;
        this.messageService = messageService;
    }

    @Override
    public void printLocalizedMessageFromBundle(String messageId) {
        io.printLn(messageService.getMessage(messageId));
    }

    @Override
    public void printLocalizedMessageFromBundle(String messageId, Object... args) {
        io.printLn(messageService.getMessage(messageId, args));
    }
}
