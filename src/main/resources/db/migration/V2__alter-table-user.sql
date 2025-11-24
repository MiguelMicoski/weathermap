alter table `user`
RENAME COLUMN username TO login;


alter table `user`
 ADD COLUMN password varchar (200);
