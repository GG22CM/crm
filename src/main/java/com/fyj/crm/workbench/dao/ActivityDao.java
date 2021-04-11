package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int save(Activity activity);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int deleteByIds(String[] ids);

    Activity getActivityById(String id);

    int update(Activity activity);

    Activity getActivityAndOwnerById(String id);

    List<Activity> getActivityListByClueId(String id);

    List<Activity> getActivityListByNameAndNotByClueId(@Param("clueid") String clueid,@Param("name") String name);

    List<Activity> getActivityListByName(String name);
}
