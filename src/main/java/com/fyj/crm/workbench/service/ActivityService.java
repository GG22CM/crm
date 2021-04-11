package com.fyj.crm.workbench.service;

import com.fyj.crm.settings.domain.User;
import com.fyj.crm.vo.PaginationVO;
import com.fyj.crm.workbench.domain.Activity;
import com.fyj.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {


    int save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    Boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByActivityId(String id);

    boolean deleteRemark(String id);


    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByClueId(String clueid);

    List<Activity> getActivityListByNameAndNotByClueId(String clueid, String name);

    List<Activity> getActivityListByName(String name);
}
