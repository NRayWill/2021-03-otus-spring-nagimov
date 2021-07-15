INSERT INTO AUTHOR (id, `name`, `middlename`, `surname`) values (2001, 'Габриэль',  'Гарсиа',   'Маркес');
INSERT INTO AUTHOR (id, `name`, `middlename`, `surname`) values (2002, 'Стивен',    'Уильям',   'Хокинг');
INSERT INTO AUTHOR (id, `name`, `middlename`, `surname`) values (2003, 'Роберт',    'Сесил',    'Мартин');
INSERT INTO AUTHOR (id, `name`, `middlename`, `surname`) values (2004, 'Эрих',      'Мария',    'Ремарк');
INSERT INTO AUTHOR (id, `name`,               `surname`) values (2005, 'Джордж',                'Оруэлл');
INSERT INTO AUTHOR (id, `name`, `middlename`, `surname`) values (2006, 'Дуглас',    'Ноэль',    'Адамс');

INSERT INTO GENRE (id, `name`) values (3001, 'Роман');
INSERT INTO GENRE (id, `name`) values (3002, 'Биография');
INSERT INTO GENRE (id, `name`) values (3003, 'Научно-популярная литература');
INSERT INTO GENRE (id, `name`) values (3004, 'Программирование');
INSERT INTO GENRE (id, `name`) values (3005, 'Фантастика');

INSERT INTO BOOK (id, `title`, `author_id`, `genre_id`) values (1001, 'Сто лет одиночества',                        2001, 3001);
INSERT INTO BOOK (id, `title`, `author_id`, `genre_id`) values (1002, 'Моя краткая история',                        2002, 3002);
INSERT INTO BOOK (id, `title`, `author_id`, `genre_id`) values (1003, 'Краткая история времени',                    2002, 3003);
INSERT INTO BOOK (id, `title`, `author_id`, `genre_id`) values (1004, 'Чистый код. Создание, анализ и рефакторинг', 2003, 3004);
INSERT INTO BOOK (id, `title`, `author_id`, `genre_id`) values (1005, 'На Западном фронте без перемен',             2005, 3001);
INSERT INTO BOOK (id, `title`, `author_id`, `genre_id`) values (1006, 'Автостопом по галактике',                    2006, 3005);