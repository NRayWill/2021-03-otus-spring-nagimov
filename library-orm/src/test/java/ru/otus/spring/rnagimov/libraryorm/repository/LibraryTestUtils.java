package ru.otus.spring.rnagimov.libraryorm.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class LibraryTestUtils {

    public static final long EXISTING_BOOK_ID = 1001L;
    public static final long EXISTING_AUTHOR_ID = 2001L;
    public static final long EXISTING_GENRE_ID = 3001L;
    public static final long EXISTING_COMMENT_ID = 4001L;

    public static <T> long getTemCount(TestEntityManager tem, Class<T> entityClass) {
        return tem.getEntityManager().createQuery("select  count(a) from " + entityClass.getName() + " a", Long.class).getSingleResult();
    }

    public static <T> T getTemItemById(TestEntityManager tem, Long id, Class<T> entityClass) {
        T item = tem.getEntityManager().find(entityClass, id);
        tem.detach(item); // Отделяем сущность для того, чтобы дальше в тесте результат не менялся
        return item;
    }
}
