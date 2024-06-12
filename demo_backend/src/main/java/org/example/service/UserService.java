package org.example.service;

import org.example.pojo.entity.SysUser;
import org.example.vo.resp.R;

/**
 * @author mmj
 * @Description
 * @create 2024-06-11 10:07
 */
public interface UserService{
    /**
     * 根据用户查询用户信息
     * @param userName 用户名称
     * @return
     */
    R<SysUser> getUserByUserName(String userName);
}
