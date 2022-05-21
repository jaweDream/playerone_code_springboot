package com.jawe.system.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PolicyConditions;
import com.jawe.system.service.AliOssService;
import com.jawe.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @author NieChangan
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value = "aliOss模块", tags = "aliOss管理")
public class AliOssController {


    @Autowired
    private AliOssService aliOssService;



    @ApiOperation(value = "获取aliOss信息")
    @RequestMapping(value = "oss/policy",method = RequestMethod.GET)
    public Result policy(@RequestParam String path) {
        Map<String, String> policy = aliOssService.policy(path);
        return Result.ok().data("policy",policy);
    }

    @ApiOperation(value = "上传图片文件")
    @PostMapping("/uploadImgFile")
    public Result uploadImgFile(MultipartFile file,String storagePath){
        String s = aliOssService.upload(file,storagePath);
        return Result.ok().data("url",s);
    }

    @ApiOperation(value = "删除替换之后的头像")
    @PostMapping("/deleteImgFile")
    public Result deleteImgFile(String file){
        //https://xinguan-parent.oss-cn-hangzhou.aliyuncs.com/2020/10/20/300f7c9d6546486eb55d825d4edcf668.png
        try {
            String[] split = file.split(".com/");
            System.out.println(split[1]);
            aliOssService.deleteFile(split[1]);
            return Result.ok();
        }catch (Exception e){
            //需要打印错误日志到本地系统中(服务器系统)
            return Result.error();
        }
    }


}
