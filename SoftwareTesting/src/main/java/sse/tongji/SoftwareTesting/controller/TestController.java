package sse.tongji.SoftwareTesting.controller;

import org.springframework.web.bind.annotation.*;
import sse.tongji.SoftwareTesting.test.java.CsvTest;

@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 针对csv文件进行测试
     * @param className
     * @param funcName
     * @param csvName
     * @return
     */
    @GetMapping("/csv")
    public Object Test(@RequestParam(value = "class") String className,
                             @RequestParam(value = "func") String funcName,
                             @RequestParam(value = "csv") String csvName){
        try{
            CsvTest csvTest = new CsvTest();
            Object result = csvTest.Test(className, funcName, csvName);
            System.out.print(result);
            return result;
        }catch (Exception e){
            return "Failed";
        }
    }
}
