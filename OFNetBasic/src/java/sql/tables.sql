/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  HP
 * Created: Dec 13, 2016
 */

CREATE TABLE USERS
(
USER_ID		NUMBER,
USERNAME 	VARCHAR2(30) 	UNIQUE	NOT NULL,
FIRST_NAME	VARCHAR2(50)	NOT NULL,
LAST_NAME	VARCHAR2(50)	NOT NULL,
PASSWORD	VARCHAR2(50)	NOT NULL,
EMAIL		VARCHAR2(50)	NOT NULL,
ROLE		VARCHAR2(10),
PAID_STATUS	VARCHAR2(3),
POINT		NUMBER(10),


PRIMARY KEY(USER_ID)
);

--DROP TABLE USERS;




CREATE TABLE POSTS
(
POST_ID		NUMBER,
USER_ID		NUMBER,
CONTENT		VARCHAR2(1000)	NOT NULL,
TIME 		DATE DEFAULT SYSDATE,
TITLE		VARCHAR2(50)	NOT NULL,

PRIMARY KEY(POST_ID),
FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE
);



CREATE TABLE CATEGORY
(
CATEGORY_ID	NUMBER,
PARENT_CATEGORY	NUMBER,
CATEGORY_NAME VARCHAR2(50)	UNIQUE,

PRIMARY KEY(CATEGORY_ID),
FOREIGN KEY(PARENT_CATEGORY) REFERENCES CATEGORY(CATEGORY_ID) ON DELETE CASCADE
);




CREATE TABLE COMMENTS
(
COMMENT_ID	NUMBER,
USER_ID		NUMBER,
CONTENT 	VARCHAR2(500)	NOT NULL,
TIME 		DATE DEFAULT SYSDATE,

PRIMARY KEY(COMMENT_ID),
FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE
);


CREATE TABLE POST_COMMENT
(
POST_ID		NUMBER,
COMMENT_ID	NUMBER,
PRIMARY KEY(POST_ID, COMMENT_ID),
FOREIGN KEY(POST_ID) REFERENCES POSTS(POST_ID) ON DELETE CASCADE,
FOREIGN KEY(COMMENT_ID) REFERENCES COMMENTS(COMMENT_ID) ON DELETE CASCADE
);


CREATE TABLE COMMENT_REPLY
(
COMMENT_ID	NUMBER,
REPLY_ID	NUMBER,
PRIMARY KEY(COMMENT_ID, REPLY_ID),
FOREIGN KEY(COMMENT_ID) REFERENCES COMMENTS(COMMENT_ID) ON DELETE CASCADE,
FOREIGN KEY(REPLY_ID) REFERENCES COMMENTS(COMMENT_ID) ON DELETE CASCADE
);



CREATE TABLE POST_VOTES
(
POST_ID		NUMBER,
USER_ID		NUMBER,
VOTE 		NUMBER,

PRIMARY KEY(POST_ID, USER_ID),
FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE,
FOREIGN KEY(POST_ID) REFERENCES POSTS(POST_ID) ON DELETE CASCADE
);




CREATE TABLE MESSAGES
(
MSG_ID		NUMBER,
SENDER_ID	NUMBER,
RECEIVER_ID	NUMBER,
CONTENT		VARCHAR2(500) 	NOT NULL,
TIME 		DATE DEFAULT SYSDATE,

PRIMARY KEY(MSG_ID),
FOREIGN KEY(SENDER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE,
FOREIGN KEY(RECEIVER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE
);




CREATE TABLE FAVOURITES
(
USER_ID		NUMBER,
CATEGORY_ID	NUMBER,

PRIMARY KEY(USER_ID, CATEGORY_ID),
FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE,
FOREIGN KEY(CATEGORY_ID) REFERENCES CATEGORY(CATEGORY_ID) ON DELETE CASCADE
);


CREATE TABLE FILES
(
FILE_ID		NUMBER,
USER_ID		NUMBER,
FILENAME 	VARCHAR2(30)	NOT NULL,
FILESIZE 	NUMBER,
FILE_DATA 	BLOB,
PRIMARY KEY(USER_ID, FILE_ID),
FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE
);



CREATE TABLE POST_CATEGORY
(
POST_ID		NUMBER,
CATEGORY_ID	NUMBER,

PRIMARY KEY(POST_ID, CATEGORY_ID),
FOREIGN KEY(POST_ID) REFERENCES POSTS(POST_ID) ON DELETE CASCADE,
FOREIGN KEY(CATEGORY_ID) REFERENCES CATEGORY(CATEGORY_ID) ON DELETE CASCADE
);


CREATE TABLE COMMENT_VOTES
(
COMMENT_ID		NUMBER,
USER_ID		NUMBER,
VOTE 		NUMBER,

PRIMARY KEY(COMMENT_ID, USER_ID),
FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE,
FOREIGN KEY(COMMENT_ID) REFERENCES COMMENTS(COMMENT_ID) ON DELETE CASCADE
);