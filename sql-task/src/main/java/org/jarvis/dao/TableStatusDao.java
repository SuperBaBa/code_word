package org.jarvis.dao;


import org.jarvis.entity.TableStatus;

import java.util.Date;
import java.util.List;

public interface TableStatusDao {
    int save(TableStatus tableStatus);

    List<TableStatus> queryListByTime(Date startTime, Date endTime);

    List<TableStatus> queryAll();

}
