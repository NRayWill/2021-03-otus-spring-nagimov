package ru.otus.spring.rnagimov.studenttestingboot.facade;

public interface LocalizedMessageFacade {
    void printLocalizedMessageFromBundle(String messageId);
    void printLocalizedMessageFromBundle(String messageId, Object... args);
}
