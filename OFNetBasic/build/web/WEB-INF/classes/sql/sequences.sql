/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  HP
 * Created: Dec 13, 2016
 */

CREATE SEQUENCE USER_ID_SEQ
INCREMENT BY 1
START WITH 110000
MAXVALUE 999999
NOCYCLE ;


CREATE SEQUENCE POST_ID_SEQ
INCREMENT BY 1
START WITH 100000
MAXVALUE 999999
NOCYCLE ;

--DROP SEQUENCE POST_ID_SEQ;


CREATE SEQUENCE CATEGORY_ID_SEQ
INCREMENT BY 1
START WITH 110000
MAXVALUE 999999
NOCYCLE ;



CREATE SEQUENCE COMMENT_ID_SEQ
INCREMENT BY 1
START WITH 100000
MAXVALUE 999999
NOCYCLE ;


CREATE SEQUENCE MESSAGE_ID_SEQ
INCREMENT BY 1
START WITH 100000
MAXVALUE 999999
NOCYCLE ;


