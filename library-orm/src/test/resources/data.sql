INSERT INTO AUTHOR (id, `name`, `middlename`, `surname`) values (2001, 'Габриэль', 'Гарсиа', 'Маркес');

INSERT INTO GENRE (id, `name`) values (3001, 'Роман');

INSERT INTO BOOK (id, `title`, `author_id`, `genre_id`) values (1001, 'Сто лет одиночества', 2001, 3001);

INSERT INTO COMMENT (id, `book_id`, `comment_author`, `text`, `created`) values (4001, 1001, 'Wikipedia', 'Одно из наиболее характерных и популярных произведений в направлении магического реализма.', '2021-07-30 17:25:00');