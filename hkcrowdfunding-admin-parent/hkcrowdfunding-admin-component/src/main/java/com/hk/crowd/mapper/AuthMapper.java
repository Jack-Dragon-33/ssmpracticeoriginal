package com.hk.crowd.mapper;

import com.hk.crowd.entity.Auth;
import com.hk.crowd.entity.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    void saveRoleAuth(@Param("roleId") Integer roleId,@Param("list") List<Integer> list);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

   List<String> getAllAuths(Integer adminId);

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    void deleteByRoleId(Integer roleId);
}