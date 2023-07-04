package com.neu.controller;

import com.neu.vo.Result;
import com.neu.pojo.ExMessage;
import com.neu.pojo.PublicSupervisor;
import com.neu.service.ExMessageService;
import com.neu.service.PublicSupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/supervisor")
public class PublicSupervisorController {

    @Autowired
    private PublicSupervisorService publicSupervisorService;

    @Resource
    private ExMessageService exMessageService;
    @Resource
    private HttpServletRequest request;

    //监督员登录功能
    @PostMapping("/login")
    public Result<?> login(@RequestBody PublicSupervisor publicSupervisor){
        Map<String,Object> data = publicSupervisorService.login(publicSupervisor);
        if (data == null){
            return Result.fail(20001,"用户名或密码错误");
        }
        return Result.success(data);
    }

    //获取个人信息
    @GetMapping("/info")
    public Result<?> getInfo(){
        //根据token获取个人信息
        String token = request.getHeader("Token");
        System.out.println("==================================="+ token);
        PublicSupervisor publicSupervisor = publicSupervisorService.getInfo(token);
        if (publicSupervisor != null){
            return Result.success(publicSupervisor);
        }
        return Result.fail(20001,"登录信息无效，请重新登录");
    }

    //注册功能
    @PostMapping("/register")
    public Result<?> register(@RequestBody PublicSupervisor publicSupervisor){
        publicSupervisorService.register(publicSupervisor);
        return Result.success("注册成功");
    }

    //”举报“功能
    @PostMapping("/report")
    public Result<?> report(@RequestBody ExMessage exMessage){
        exMessageService.report(exMessage);
        return Result.success("填报信息完成，我们会尽快处理");
    }

    //获取本人提交的举报信息
    @GetMapping("/all")
    public Result<?> getMessage(){
        List<ExMessage> messages = exMessageService.getMessage();
        if (messages != null){
            return Result.success(messages);
        }
        return Result.success("还未提交过数据");
    }

}
