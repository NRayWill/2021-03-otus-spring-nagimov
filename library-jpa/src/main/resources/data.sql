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

INSERT INTO COMMENT (id, `book_id`, `comment_author`, `text`, `created`) values (4001, 1001, 'Wikipedia', 'Одно из наиболее характерных и популярных произведений в направлении магического реализма.', CURRENT_DATE());
INSERT INTO COMMENT (id, `book_id`, `comment_author`, `text`, `created`) values (4002, 1002, 'Google',    'Искренне и человечно написанная книга, в которой Стивен Хокинг, один из самых блестящих ученых нашего времени, откровенно рассказывает об основных вехах своей такой неординарной жизни и о тех исследовательских проблемах, в сфере которых он сделал поистине великие открытия.', CURRENT_DATE());
INSERT INTO COMMENT (id, `book_id`, `comment_author`, `text`, `created`) values (4003, 1003, 'Wikipedia', 'В книге рассказывается о появлении Вселенной, о природе пространства и времени, чёрных дырах, теории суперструн и о некоторых математических проблемах, однако на страницах издания можно встретить лишь одну формулу E=mc².', CURRENT_DATE());
INSERT INTO COMMENT (id, `book_id`, `comment_author`, `text`, `created`) values (4004, 1004, 'Labitint',  'Вы узнаете много нового о коде. Более того, научитесь отличать хороший код от плохого, узнаете, как писать хороший код и как преобразовать плохой код в хороший.', CURRENT_DATE());
INSERT INTO COMMENT (id, `book_id`, `comment_author`, `text`, `created`) values (4005, 1005, 'Wikipedia', 'Антивоенный роман повествует о всем пережитом, увиденном на фронте молодым солдатом Паулем Боймером, а также его фронтовыми товарищами в Первой мировой войне.', CURRENT_DATE());
INSERT INTO COMMENT (id, `book_id`, `comment_author`, `text`, `created`) values (4006, 1006, 'Wikipedia', 'Юмористический фантастический роман английского писателя Дугласа Адамса. Первая книга одноимённой серии.', CURRENT_DATE());