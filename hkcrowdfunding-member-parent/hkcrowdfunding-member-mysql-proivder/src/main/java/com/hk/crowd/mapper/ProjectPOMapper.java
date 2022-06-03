package com.hk.crowd.mapper;


import com.hk.crowd.entity.po.ProjectPO;
import com.hk.crowd.entity.po.ProjectPOExample;
import com.hk.crowd.entity.vo.DetailProjectVO;
import com.hk.crowd.entity.vo.DetailReturnVO;
import com.hk.crowd.entity.vo.PortalProjectVO;
import com.hk.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPOMapper {
    long countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertTypeRelationship(@Param("projectPOId") Integer projectPOId,@Param("typeIdList") List<Integer> typeIdList);

    void insertTagRelationship(@Param("projectPOId") Integer projectPOId, @Param("tagIdList") List<Integer> tagIdList);

    List<PortalTypeVO> selectPortalTypeVOList();

    List<PortalProjectVO> selectPortalProjectVOList(Integer id);

    List<DetailReturnVO>selectDeatailReturnVO(Integer id);

    List<String> selectDetailPicturePath(Integer id);

    DetailProjectVO selectDetailProjectVO(Integer id);
}