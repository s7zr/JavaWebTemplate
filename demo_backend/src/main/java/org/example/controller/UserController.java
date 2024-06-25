package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.example.pojo.entity.SysUser;
import org.example.service.UserService;
import org.example.vo.req.LoginReqVo;
import org.example.vo.resp.LoginRespVo;
import org.example.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author mmj
 * @Description test
 * @create 2024-06-11 10:14
 */
@Api(value = "/api", tags = {"test"})
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "userName", value = "", required = true)
    })
    @ApiOperation(value = "根据用户名查询用户信息", notes = "根据用户名查询用户信息", httpMethod = "GET")
    @GetMapping("/user/{userName}")
    public R<SysUser> getUserByUserName(@PathVariable("userName") String userName){
        return userService.getUserByUserName(userName);
    }
//    /**
//     * 用户登录功能实现
//     * @param vo
//     * @return
//     */
//    @ApiOperation("用户登录功能接口")
//    @PostMapping("/login")
//    public R<LoginRespVo> login(@RequestBody LoginReqVo vo){
//        R<LoginRespVo> r= userService.login(vo);
//        return r;
//    }

    /**
     * 生成登录校验码的访问接口
     * @return
     */
    @ApiOperation("获取验证码接口")
    @GetMapping("/captcha")
    public R<Map> getCaptchaCode(){
        return userService.getCaptchaCode();
    }

}
