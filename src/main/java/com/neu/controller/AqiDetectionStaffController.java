package com.neu.controller;

import com.neu.dto.TestingDto;
import com.neu.util.JwtUtil;
import com.neu.vo.Result;
import com.neu.dto.ExMessageDto;
import com.neu.pojo.AqiDetectionStaff;
import com.neu.pojo.Testing;
import com.neu.service.*;
import com.neu.service.AqiDetectionStaffService;
import com.neu.service.AssignService;
import com.neu.service.ExMessageService;
import com.neu.service.TestingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/staff")
public class AqiDetectionStaffController {

    @Autowired
    private AqiDetectionStaffService service;
    @Resource
    private AssignService assignService;
    @Resource
    private ExMessageService exMessageService;
    @Resource
    private TestingService testingService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;


    //登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody AqiDetectionStaff detectionStaff){
        Map<String,Object> data = service.login(detectionStaff);
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
        AqiDetectionStaff staff = service.getInfo(token);
        if (staff != null){
            return Result.success(staff);
        }
        return Result.fail(20001,"登录信息无效，请重新登录");
    }


    //获取自己需要检测的所有异常信息
    @GetMapping()
    public Result<?> getAllMessage(){
        String token = request.getHeader("Token");

        AqiDetectionStaff staff = jwtUtil.parseToken(token, AqiDetectionStaff.class);

        //根据id从指派表里面查找到自己需要去检测的事件的id
        List<Integer> messageIds = assignService.getMessageId(staff.getId());
        if (messageIds == null){
            //没有自己需要检测的事件
            return Result.success("没有自己需要检测的事件");
        }

        //有自己需要检测的事件
        List<ExMessageDto> messageDtoList = new ArrayList<>();

        for (Integer messageId:messageIds) {
            //根据事件的id去事件表里面查找事件的信息
            ExMessageDto exMessageDto= exMessageService.getAllMessage(messageId);

            messageDtoList.add(exMessageDto);
        }
        return Result.success(messageDtoList);
    }
    //获取自己已经完成的检测任务
    @GetMapping("/alldo")
    public Result<?> getAllDo(){
        String token = request.getHeader("Token");
        AqiDetectionStaff staff = jwtUtil.parseToken(token, AqiDetectionStaff.class);

        //根据id从指派表里面查找到自己需要去检测的事件的id
        List<Integer> messageIds = assignService.getDoMessageId(staff.getId());
        if (messageIds == null){
            return Result.success("暂时没有已经完成的检测任务");
        }

        //有自己已经完成的检测信息
        /*List<ExMessageDto> messageDtoList = new ArrayList<>();

        for (Integer messageId:messageIds) {
            //根据事件的id去事件表里面查找事件的信息
            ExMessageDto exMessageDto= exMessageService.getAllMessage(messageId);

            messageDtoList.add(exMessageDto);

        }*/

        //有自己已经完成的检测信息
        List<TestingDto> testingDtoList = new ArrayList<>();
        for (Integer messageId:messageIds) {
            TestingDto testingDto = testingService.getResultByExMessageId(messageId);
            testingDtoList.add(testingDto);

        }


        return Result.success(testingDtoList);
    }

    //记录检测结果
    @PostMapping("/do")
    public Result<?> toDo(@RequestBody Testing testing){

        Integer co = testing.getCO();
        Integer pm = testing.getPM();
        Integer so2 = testing.getSO2();
        int SO2AQI=0;
        int COAQI=0;
        int PMAQI=0;
        int AQI=0;

        if (co == null || pm == null || so2 == null){
            return Result.fail(20001,"请填写完整信息");
        }

        if (so2>=0&&so2<=50) SO2AQI =1;
        if (so2>=51&&so2<=150) SO2AQI =2;
        if (so2>=151&&so2<=475) SO2AQI =3;
        if (so2>=476&&so2<=800) SO2AQI =4;
        if (so2>=801&&so2<=1600) SO2AQI =5;
        if (so2>=1601) SO2AQI =6;


        if (co>=0&&co<=5) COAQI =1;
        if (co>=6&&co<=10) COAQI =2;
        if (co>=11&&co<=35) COAQI =3;
        if (co>=36&&co<=60) COAQI =4;
        if (co>=61&&co<=90) COAQI =5;
        if (co>=91&&co<=150) COAQI =6;

        if (pm>=0&&pm<=35) PMAQI =1;
        if (pm>=36&&pm<=75) PMAQI =2;
        if (pm>=76&&pm<=115) PMAQI =3;
        if (pm>=116&&pm<=150) PMAQI =4;
        if (pm>=151&&pm<=250) PMAQI =5;
        if (pm>=251&&pm<=500) PMAQI =6;

        if (SO2AQI >= COAQI){
            AQI = SO2AQI;
        }else{
            AQI = SO2AQI;
        }
        if (PMAQI >= AQI){
            AQI =PMAQI;
        }


        testing.setAQILevel(AQI);

        String token = request.getHeader("Token");
        AqiDetectionStaff staff = jwtUtil.parseToken(token, AqiDetectionStaff.class);
        testing.setAQIDetectionStaffId(staff.getId());

        testingService.record(testing);
        return Result.success("已经完成记录");

    }





}
