INSERT INTO ROLE (ID, NAME)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO ROLE (ID, NAME)
VALUES (2, 'ROLE_USER');

INSERT INTO ROLE (ID, NAME)
VALUES (3, 'ROLE_AUTHOR');

-- $2a$10$7i8CJNnqZAFcjd98AxXsUeNGqV2h/3VMscDOKXcAa5x0bYlbed5pO (unencrypted) : password
INSERT INTO USER (ID, NAME, EMAIL, PASSWORD) VALUES (1, 'Vasya Pupkin', 'v.pupkin@mail.ru', '$2a$10$7i8CJNnqZAFcjd98AxXsUeNGqV2h/3VMscDOKXcAa5x0bYlbed5pO');
INSERT INTO USER (ID, NAME, EMAIL, PASSWORD) VALUES (2, 'Pupka Vaskin', 'p.vaskin@mail.ru', '$2a$10$7i8CJNnqZAFcjd98AxXsUeNGqV2h/3VMscDOKXcAa5x0bYlbed5pO');
INSERT INTO USER (ID, NAME, EMAIL, PASSWORD) VALUES (3, 'Stephen King', 's.king@mail.ru', '$2a$10$7i8CJNnqZAFcjd98AxXsUeNGqV2h/3VMscDOKXcAa5x0bYlbed5pO');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 2);
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (2, 2);
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (3, 3);

INSERT INTO ARTICLE(ID, TITLE, AUTHOR_ID, CONTENT, PUBLISH_DATE) VALUES (1, 'Carrie', 3, CAST('Lorem Ipsum...' AS CLOB), TO_DATE('2021-02-10', 'YYYY-MM-DD'));
INSERT INTO ARTICLE(ID, TITLE, AUTHOR_ID, CONTENT, PUBLISH_DATE) VALUES (2, 'The Shining', 3, CAST('Lorem Ipsum...' AS CLOB), TO_DATE('2021-02-25', 'YYYY-MM-DD'));
INSERT INTO ARTICLE(ID, TITLE, AUTHOR_ID, CONTENT, PUBLISH_DATE) VALUES (3, 'The Stand', 3, CAST('Lorem Ipsum...' AS CLOB), TO_DATE('2021-02-26', 'YYYY-MM-DD'));
INSERT INTO ARTICLE(ID, TITLE, AUTHOR_ID, CONTENT, PUBLISH_DATE) VALUES (4, 'The Dark Tower: The Gunslinger', 3, CAST('Lorem Ipsum...' AS CLOB), TO_DATE('2021-02-26', 'YYYY-MM-DD'));
INSERT INTO ARTICLE(ID, TITLE, AUTHOR_ID, CONTENT, PUBLISH_DATE) VALUES (5, 'Misery', 3, CAST('Lorem Ipsum...' AS CLOB), TO_DATE('2021-02-27', 'YYYY-MM-DD'));
INSERT INTO ARTICLE(ID, TITLE, AUTHOR_ID, CONTENT, PUBLISH_DATE) VALUES (6, 'Dreamcatcher', 3, CAST('Lorem Ipsum...' AS CLOB), TO_DATE('2021-02-28', 'YYYY-MM-DD'));
INSERT INTO ARTICLE(ID, TITLE, AUTHOR_ID, CONTENT, PUBLISH_DATE) VALUES (7, 'Sleeping Beauties', 3, CAST('Lorem Ipsum...' AS CLOB), TO_DATE('2021-03-02', 'YYYY-MM-DD'));
