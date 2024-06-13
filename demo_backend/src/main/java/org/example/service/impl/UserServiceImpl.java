package org.example.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.StockConstant;
import org.example.mapper.SysUserMapper;
import org.example.pojo.entity.SysUser;
import org.example.service.UserService;
import org.example.utils.IdWorker;
import org.example.vo.resp.R;
import org.example.vo.resp.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author mmj
 * @Description
 * @create 2024-06-11 10:07
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;
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

    @Override
    public R<Map> getCaptchaCode() {
        //参数分别是宽、高、验证码长度、干扰线数量
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        //设置背景颜色清灰
        captcha.setBackground(Color.lightGray);
        //获取图片中的验证码，默认生成的校验码包含文字和数字，长度为4
        String checkCode = captcha.getCode();
        log.info("生成校验码:{}",checkCode);
        //生成sessionId,因为sessionId会传递给前端，因此使用String，避免精度丢失
        String sessionId = String.valueOf(idWorker.nextId());
        //将sessionId和校验码保存在redis下，并设置缓存中数据存活时间一分钟
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX +sessionId,checkCode,1, TimeUnit.MINUTES);
        //组装响应数据
        HashMap<String, String> info = new HashMap<>();
        info.put("sessionId",sessionId);
        info.put("imageData",captcha.getImageBase64());//获取base64格式的图片数据
        //设置响应数据格式
        return R.ok(info);
    }
}
