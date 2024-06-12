package org.example.service.impl;

import org.example.mapper.SysUserMapper;
import org.example.pojo.entity.SysUser;
import org.example.service.UserService;
import org.example.vo.resp.R;
import org.example.vo.resp.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mmj
 * @Description
 * @create 2024-06-11 10:07
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名称查询用户信息
     *
     * @param userName 用户名称
     * @return
     */
    @Override
    public R<SysUser> getUserByUserName(String userName) {
        SysUser user=sysUserMapper.findByUserName(userName);
        if(user == null) return R.error(ResponseCode.USERNAME_NOT_EXISTS);
        return R.ok(user);
    }
}
