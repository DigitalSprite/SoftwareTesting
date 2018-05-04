package sse.tongji.SoftwareTesting.test.java;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csvreader.CsvReader;
import sse.tongji.SoftwareTesting.config.FileConfig;

public class CsvTest {

    //Update
    public CsvTest(){}

    public Object Test(String className,String funcName, String csvFileName){

        String inputFilePath = FileConfig.InputScvFileRoot + csvFileName + ".csv";
        try{
            CsvReader csvReader = new CsvReader(inputFilePath);
            csvReader.readRecord();
            String title = csvReader.getRawRecord();
            System.out.println(title);
            List<String> headlist = new ArrayList<>();
            for (String i : title.split(","))
                headlist.add(i);
            headlist.add("judge");
            List<Double> sales = new ArrayList<>();

            int total_num = 0;
            int correct_num = 0;
            int content_error = 0;
            int format_error = 0;
            int other_error = 0;
            String statistics = "";

            //遍历原始csv文件数据
            List<Object> tof = new ArrayList<>();
            while(csvReader.readRecord()){
                boolean judge = false;
                String record = null;
                //获取数据
                try{
                    record = csvReader.getRawRecord();
                    Class testClass = Class.forName(FileConfig.JavaClassRoot + className);
                    Method method = testClass.getMethod(funcName, String.class);
                    Object obj = testClass.getConstructor().newInstance();
                    Object result = method.invoke(obj, record);
                    double r = (double)result;
                    if (r == 1.0){
                        correct_num++;
                        judge = true;
                    }
                    else if(r == 0.0)
                        content_error++;
                    else if(r == -1.0)
                        format_error++;
                }catch (Exception e){
                    other_error++;
                }
                List<Object> temp = new ArrayList<>();
                String[] content = record.split(",");
                for(int i = 0; i < headlist.size() - 1; i++){
                    if (i < content.length){
                        temp.add(content[i]);
                    }
                    else{
                        temp.add("");
                    }
                }
                temp.add(judge);
                tof.add(temp);
                total_num++;
            }
//            String outputFilePath = FileConfig.OutputCsvFileRoot + csvFileName + ".csv";
//            File csvFile = null;
//            BufferedWriter csvWriter = null;
//            try{
//                csvFile = new File(outputFilePath);
//                File parent = csvFile.getParentFile();
//                if (parent != null && parent.exists()){
//                    parent.mkdirs();
//                }
//                csvFile.createNewFile();
//                csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"));
//                writeRow(headList, csvWriter);
//                for(List<Object> row : dataList){
//                    writeRow(row, csvWriter);
//                }
//                csvWriter.flush();
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                csvWriter.close();
//            }
            HashMap<String, Object> result = new HashMap<>();
            result.put("total_value",total_num);
            result.put("correct_value", correct_num);
            result.put("content_error", content_error);
            result.put("format_error", format_error);
            result.put("other_error", other_error);
//            result.put("statistics", statistics);
            result.put("result",tof);
            result.put("header",headlist);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static void writeRow(List<Object>row, BufferedWriter csvWriter) throws IOException{
        for(Object data : row){
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append(data + ",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
}
