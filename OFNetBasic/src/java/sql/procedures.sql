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

