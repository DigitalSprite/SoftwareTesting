package sse.tongji.SoftwareTesting.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sse.tongji.SoftwareTesting.config.FileConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 文件的Controller类
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 上传csv文件
     * @param file
     * @param name
     * @return
     */
    @PostMapping("/upload/csv")
    @ResponseBody
    public Object getJavaFiles(@RequestParam("file")MultipartFile file,
                               @RequestParam(value = "name")String name){
        if(!file.isEmpty()){
            try{
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(FileConfig.InputScvFileRoot + name + ".csv"));
                stream.write(file.getBytes());
                stream.close();
                stream.flush();
                stream.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
                return "Upload Fail" + e.getMessage();
            }catch (IOException e){
                e.printStackTrace();
                return "Upload Fail" + e.getMessage();
            }
            return "Upload Successfully";
        }else{
            return "Upload Fail，file is empty";
        }
    }


    /**
     * 上传java的方法，返回对应的类以及对应的方法
     * @param request
     * @return
     */
    @PostMapping("/upload/java")
    @ResponseBody
    public Object getJavaFile(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        List<Object> information = new ArrayList<>();
        for(int i = 0; i < files.size(); i++){
            file = files.get(i);
            if(!file.isEmpty()){
                try{
                    byte[] bytes = file.getBytes();
                    String fileName = file.getOriginalFilename();
                    String className = fileName.substring(0, fileName.length() - 5);
                    System.out.println(fileName);
                    stream = new BufferedOutputStream(new FileOutputStream(FileConfig.JavaFilePath + fileName));
                    stream.write(bytes);
                    stream.close();
                    Class testClass = Class.forName(FileConfig.JavaClassRoot + className);
                    System.out.println("\n\nGet Class Successfully!");
                    Method[] methods = testClass.getMethods();
                    HashMap<String, Object> classInfo = new HashMap<>();
                    classInfo.put("class", className);
                    List<String> methodsName = new ArrayList<>();
                    for(int j = 0; j < methods.length; j++){
                        methodsName.add(methods[j].getName());
                    }
                    classInfo.put("method",methodsName);
                    information.add(classInfo);
                }catch (Exception e){
                    stream = null;
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            }else{
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return information;
    }

    /**
     * 下载指定名字的csv文件
     * @param name
     * @param response
     * @return
     */
    @GetMapping("/download")
    public Object Download(@RequestParam(value = "name")String name, HttpServletResponse response){
        try{
            String filename = name + ".csv";
            String path = FileConfig.OutputCsvFileRoot + filename;
            File file = new File(path);
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
