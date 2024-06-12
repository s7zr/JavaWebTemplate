package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.pojo.entity.SysUser;

/**
* @author mmj
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-06-11 09:37:06
* @Entity org.example.pojo.entity.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    /**
     * 根据用户名称查询用户信息
     * @param userName 用户名称
     * @return
     */
    SysUser findByUserName(String userName);
    SysUser selectByPrimaryKey1(@Param("id") Long id);
}
