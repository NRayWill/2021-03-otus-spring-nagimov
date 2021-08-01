package ru.otus.spring.rnagimov.libraryorm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Date;

public class LibraryTestUtils {

    public static final long EXISTING_BOOK_ID = 1001L;
    public static final long EXISTING_AUTHOR_ID = 2001L;
    public static final long EXISTING_GENRE_ID = 3001L;
    public static final long EXISTING_COMMENT_ID = 4001L;

    public static final String EXISTING_COMMENT_AUTHOR = "Wikipedia";
    public static final String EXISTING_COMMENT_TEXT = "Одно из наиболее характерных и популярных произведений в направлении магического реализма.";
    public static Date EXISTING_COMMENT_DATE = Timestamp.valueOf("2021-07-30 17:25:00");

    public static <T> long getTemCount(TestEntityManager tem, Class<T> entityClass) {
        return tem.getEntityManager().createQuery("select  count(a) from " + entityClass.getName() + " a", Long.class).getSingleResult();
    }

    public static <T> T getTemItemById(TestEntityManager tem, Long id, Class<T> entityClass) {
        TypedQuery<T> checkQuery = tem.getEntityManager().createQuery("select a from " + entityClass.getSimpleName() + " a where a.id = :id", entityClass);
        checkQuery.setParameter("id", id);
        T item = checkQuery.getSingleResult();
        tem.detach(item);
        return item;
    }
}
