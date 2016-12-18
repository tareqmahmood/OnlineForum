/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  HP
 * Created: Dec 18, 2016
 */

alter table users
add constraint pass_const check(password like '________%');

alter table users
add constraint email_const check(email like '_%@_%._%');


alter table users
add constraint username_const check(username like '___%');