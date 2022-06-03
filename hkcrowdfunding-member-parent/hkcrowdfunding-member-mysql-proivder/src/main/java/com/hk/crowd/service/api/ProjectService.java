package com.hk.crowd.service.api;

import com.hk.crowd.entity.vo.DetailProjectVO;
import com.hk.crowd.entity.vo.PortalTypeVO;
import com.hk.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
public interface ProjectService {
    void saveProject(ProjectVO projectVO,Integer memberId);
    List<PortalTypeVO> getPortalTypeVO();
    DetailProjectVO getDetailProjectVO(Integer projectId);
}
