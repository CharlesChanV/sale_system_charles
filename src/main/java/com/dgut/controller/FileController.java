package com.dgut.controller;

import com.dgut.vo.Result;
import com.dgut.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "文件管理控制器")
public class FileController {

    @ApiOperation(value = "[文件] - 上传",consumes = "application/form-data",httpMethod = "POST")
    @PostMapping("/FILE/upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 生成新文件名
        //```
        // 配置文件得路径
        // filePath=XXXX/新文件名字
        // File newFileile = new File(filePath);

//        if (!newFile) {
//
//        }
        return ResultUtils.success();

    }


}
