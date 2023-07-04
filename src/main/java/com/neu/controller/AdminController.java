package com.neu.controller;

import com.neu.vo.Result;
import com.neu.dto.ExMessageDto;
import com.neu.dto.TestingDto;
import com.neu.pojo.Admin;
import com.neu.pojo.AqiDetectionStaff;
import com.neu.service.AdminService;
import com.neu.service.AqiDetectionStaffService;
import com.neu.service.AssignService;
import com.neu.service.ExMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ExMessageService exMessageService;

    @Autowired
    private AqiDetectionStaffService aqiDetectionStaffService;

    @Autowired
    private AssignService assignService;
    @Resource
    private HttpServletRequest request;


    //登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin){
        Map<String,Object> data = adminService.login(admin);
         if (data == null){
             return Result.fail(20001,"账号或密码错误");
         }
         return Result.success(data);
    }

    //获取个人信息
    @GetMapping("/info")
    public Result<?> getInfo(){
        //根据token获取个人信息
        String token = request.getHeader("Token");
        System.out.println("==============================="+token);
        Admin admin = adminService.getInfo(token);
        if (admin != null){
            return Result.success(admin);
        }
        return Result.fail(20001,"登录信息无效，请重新登录");
    }

  //获取委派给每个检测员的异常信息
    @GetMapping()
    public Result<?> getMessage(){
        List<ExMessageDto> messagesdto = exMessageService.listMessage();
        if(messagesdto == null){
            return Result.success("暂无委派信息");
        }
        return Result.success(messagesdto);
    }

    //获取所有的异常信息
    @GetMapping("/getMessage")
    public Result<?> getDoMessage(){
        List<ExMessageDto> messagesdto = exMessageService.listDoMessage();
        if (messagesdto == null){
            return Result.success("没有异常信息");
        }
        return Result.success(messagesdto);
    }


    //获取所有的检测员
    @GetMapping("/getStaff")
    public Result<?> getStaff(){
        List<AqiDetectionStaff> staffs = aqiDetectionStaffService.listStaff();
        return Result.success(staffs);
    }

    //把异常信息指派给检测员
    @PostMapping("/giveStaff/{messageId}/{staffId}")
    public Result<?> giveStaff(@PathVariable("messageId") Integer messageId,@PathVariable("staffId") Integer staffId){
        adminService.giveMessageToStaff(messageId,staffId);
        return Result.success("已经指派");
    }

    //获取检测员检测后的数据
    @GetMapping("/result")
    public Result<?> getResult(){
        List<TestingDto> testingDtoList = adminService.getAllResult();
        if (testingDtoList == null){
            return Result.success("暂时没有监测数据");
        }
        return Result.success(testingDtoList);
    }











































}
