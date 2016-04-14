--------------------------------------------------------
--  File created - Вторник-Март-29-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ACCOUNTS
--------------------------------------------------------

  CREATE TABLE "ACCOUNTS" 
   (	"ID" NUMBER, 
	"ACCOUNT_NO" VARCHAR2(20 BYTE), 
	"USER_ID" NUMBER, 
	"AMOUNT" NUMBER, 
	"CURRENCY" VARCHAR2(3 BYTE), 
	"CREATED_BY" NUMBER
   ); 
--------------------------------------------------------
--  DDL for Table OPERATIONS
--------------------------------------------------------

  CREATE TABLE "OPERATIONS" 
   (	"ID" NUMBER, 
	"ACCOUNT_ID" NUMBER, 
	"OPERATION_TYPE" VARCHAR2(10 BYTE), 
	"AMOUNT" NUMBER, 
	"CURRENCY" VARCHAR2(3 BYTE), 
	"USER_ID" NUMBER
   );
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE "USERS" 
   (	"ID" NUMBER, 
	"USERNAME" VARCHAR2(20 BYTE), 
	"PASSWORD" VARCHAR2(50 BYTE), 
	"ROLE" VARCHAR2(20 BYTE)
   ); 
--------------------------------------------------------
--  DDL for Index SYS_C007452
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C007452" ON "ACCOUNTS" ("ID");
--------------------------------------------------------
--  DDL for Index SYS_C007454
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C007454" ON "OPERATIONS" ("ID"); 
 
--------------------------------------------------------
--  DDL for Index SYS_C007453
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C007453" ON "USERS" ("ID") 
  
--------------------------------------------------------
--  Constraints for Table ACCOUNTS
--------------------------------------------------------

  ALTER TABLE "ACCOUNTS" ADD PRIMARY KEY ("ID");
  
--------------------------------------------------------
--  Constraints for Table OPERATIONS
--------------------------------------------------------

  ALTER TABLE "OPERATIONS" ADD PRIMARY KEY ("ID");
  
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "USERS" ADD PRIMARY KEY ("ID");
  
--------------------------------------------------------
--  Ref Constraints for Table ACCOUNTS
--------------------------------------------------------

  ALTER TABLE "ACCOUNTS" ADD CONSTRAINT "ACCOUNT_CREATED_BY" FOREIGN KEY ("CREATED_BY")
	  REFERENCES "USERS" ("ID") ENABLE;
  ALTER TABLE "ACCOUNTS" ADD CONSTRAINT "ACCOUNT_USER" FOREIGN KEY ("USER_ID")
	  REFERENCES "USERS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table OPERATIONS
--------------------------------------------------------

  ALTER TABLE "OPERATIONS" ADD CONSTRAINT "OPERATION_ACCOUNT" FOREIGN KEY ("ACCOUNT_ID")
	  REFERENCES "ACCOUNTS" ("ID") ENABLE;
  ALTER TABLE "OPERATIONS" ADD CONSTRAINT "OPERATION_USER" FOREIGN KEY ("USER_ID")
	  REFERENCES "USERS" ("ID") ENABLE;
