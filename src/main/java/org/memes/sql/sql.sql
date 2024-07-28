CREATE DATABASE IF NOT EXISTS bot;
USE bot;
CREATE TABLE users(
    id BIGINT PRIMARY KEY,
    nickname TEXT,
    started BOOLEAN,
    ifNicknameRequired BOOLEAN,
    ifListenerRequired BOOLEAN,
    ifRmListenerRequired BOOLEAN,
    ifStreamOpened BOOLEAN
);

DROP TABLE IF EXISTS users;

CREATE TABLE usersChats(
    id UUID PRIMARY KEY ,
    userId BIGINT NOT NULL ,
    chatId BIGINT NOT NULL ,
    FOREIGN KEY (userId) REFERENCES users(id)
);