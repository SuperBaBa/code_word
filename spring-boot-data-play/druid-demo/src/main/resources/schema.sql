-- 初始化内嵌数据库
CREATE TABLE T_STUDENTS
(
    ID          INT IDENTITY,
    STUDENT_NUM INT,
    NAME        VARCHAR(64),
    SCORE       DOUBLE
);
