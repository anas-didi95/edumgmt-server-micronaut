--liquibase formatted sql

--changeset anas:2.t_attendance-1-1
CREATE TABLE PUBLIC.T_ATTENDANCE (
	ID UUID NOT NULL,
  DT DATE NOT NULL,
  IS_DEL BOOLEAN DEFAULT FALSE DEFAULT ON NULL NOT NULL,
  VER INTEGER NOT NULL,
  CREATE_BY VARCHAR(20) NOT NULL,
  CREATE_DT TIMESTAMP WITH TIME ZONE NOT NULL,
  UPDATE_BY VARCHAR(20) NOT NULL,
  UPDATE_DT TIMESTAMP WITH TIME ZONE NOT NULL,
	CONSTRAINT PK_ATTENDANCE PRIMARY KEY(ID),
  CONSTRAINT UQ_ATTENDANCE_DT_IS_DEL UNIQUE(DT, IS_DEL)
);
--rollbank DROP TABLE T_STUDENT;

--changeset anas:2.t_attendance-1-2
CREATE INDEX IX_ATTENDANCE_DT ON PUBLIC.T_ATTENDANCE(DT);
--rollback DROP INDEX IX_ATTENDANCE_DT;
