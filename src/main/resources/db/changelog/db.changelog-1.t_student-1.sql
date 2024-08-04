--liquibase formatted sql

--changeset anas:1.t_student-1-1
CREATE TABLE PUBLIC.T_STUDENT (
	ID UUID NOT NULL,
  IC_NO VARCHAR(20) NOT NULL,
	NM VARCHAR(100) NOT NULL,
  IS_DEL BOOLEAN DEFAULT FALSE DEFAULT ON NULL NOT NULL,
  VER INTEGER NOT NULL,
  CREATE_BY VARCHAR(20) NOT NULL,
  CREATE_DT TIMESTAMP WITH TIME ZONE NOT NULL,
  UPDATE_BY VARCHAR(20) NOT NULL,
  UPDATE_DT TIMESTAMP WITH TIME ZONE NOT NULL,
	CONSTRAINT PK_STUDENT PRIMARY KEY(ID),
  CONSTRAINT UQ_STUDENT_IC_NO_IS_DEL UNIQUE(IC_NO, IS_DEL)
);
--rollbank DROP TABLE T_STUDENT;

--changeset anas:1.t_student-1-2
CREATE INDEX IX_STUDENT_IC_NO ON PUBLIC.T_STUDENT(IC_NO);
--rollback DROP INDEX IX_STUDENT_IC_NO;
