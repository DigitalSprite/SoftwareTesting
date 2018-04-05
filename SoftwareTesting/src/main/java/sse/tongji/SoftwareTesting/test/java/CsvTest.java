package sse.tongji.SoftwareTesting.test.java;

import java.lang.reflect.Method;
import sse.tongji.SoftwareTesting.config.FileConfig;

public class CsvTest {

    public CsvTest(){}

    public Object Test(String className,String funcName, String csvFileName){
        try{
            Class testClass = Class.forName(FileConfig.JavaClassRoot + className);
            Method method = testClass.getMethod(funcName, String.class);
            Object obj = testClass.getConstructor().newInstance();
            Object result = method.invoke(obj, csvFileName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
