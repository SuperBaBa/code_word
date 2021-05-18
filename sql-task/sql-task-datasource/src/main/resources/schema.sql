CREATE TABLE `db_status` (
  `id` INT IDENTITY ,
  `table_name` VARCHAR(64)  ,
  `cost_time` INT ,
  `table_count` INT  NOT NULL ,
  `create_time` TIMESTAMP NOT NULL ,
  `modify_time` TIMESTAMP NOT NULL
);

CREATE TABLE `db_cron`  (
  `id` INT IDENTITY,
  `send_time` TIMESTAMP NOT NULL ,
  `status` INT,
  `cron_str` VARCHAR(64)
)