/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  HP
 * Created: Dec 13, 2016
 */

CREATE OR REPLACE PROCEDURE ADD_POST_VOTE (PID IN NUMBER, UID IN NUMBER, VT IN NUMBER) IS
BEGIN
	INSERT INTO POST_VOTES VALUES(PID, UID, VT);
	DBMS_OUTPUT.PUT_LINE('DB : successful insertion of vote of post ' || PID);
EXCEPTION
	WHEN DUP_VAL_ON_INDEX THEN
		UPDATE POST_VOTES
		SET VOTE = VT
		WHERE POST_ID = PID AND USER_ID = UID;
		DBMS_OUTPUT.PUT_LINE('DB : post vote overwrite of post ' || PID);
	WHEN OTHERS THEN
		DBMS_OUTPUT.PUT_LINE('DB : error while inserting vote of post ' || PID) ; 
END;
/


CREATE OR REPLACE PROCEDURE ADD_POST_COMMENT (PID IN NUMBER, UID IN NUMBER, STR IN VARCHAR2) IS
	CID NUMBER;
BEGIN
	SELECT COMMENT_ID_SEQ.NEXTVAL INTO CID FROM DUAL;
	INSERT INTO COMMENTS VALUES(CID, UID, STR, SYSDATE);
	INSERT INTO POST_COMMENT VALUES(PID, CID);
	DBMS_OUTPUT.PUT_LINE('DB : successful insertion of comment ' || PID);
EXCEPTION
	WHEN OTHERS THEN
		DBMS_OUTPUT.PUT_LINE('DB : error while inserting comment of post ' || PID) ; 
END;
/




CREATE OR REPLACE PROCEDURE ADD_COMMENT_REPLY (CID IN NUMBER, UID IN NUMBER, STR IN VARCHAR2) IS
	RID NUMBER;
BEGIN
	SELECT COMMENT_ID_SEQ.NEXTVAL INTO RID FROM DUAL;
	INSERT INTO COMMENTS VALUES(RID, UID, STR, SYSDATE);
	INSERT INTO COMMENT_REPLY VALUES(CID, RID);
	DBMS_OUTPUT.PUT_LINE('DB : successful insertion of reply ' || CID);
EXCEPTION
	WHEN OTHERS THEN
		DBMS_OUTPUT.PUT_LINE('DB : error while inserting reply of comment ' || CID) ; 
END;
/

