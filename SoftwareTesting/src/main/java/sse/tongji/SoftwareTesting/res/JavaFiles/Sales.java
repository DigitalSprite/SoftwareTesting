package sse.tongji.SoftwareTesting.res.JavaFiles;

import com.csvreader.CsvReader;
import sse.tongji.SoftwareTesting.config.FileConfig;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Sales {

    /**
     * 根据销售情况计算月销额
     * @param mainframe
     * @param displayer
     * @param peripheral
     * @return
     */
    private static double calculateSale(int mainframe, int displayer, int peripheral){
        if(mainframe > 1 && mainframe <= 70 && displayer > 1 && displayer <= 70 && peripheral > 1 && peripheral <= 70){
            double raw_price = (int)mainframe * 25 + (int)displayer * 30 + (int)peripheral * 45;
            if(raw_price >= 0 && raw_price <= 1000)
                return raw_price * 0.1;
            else if(raw_price > 1000 && raw_price <= 1800)
                return raw_price * 0.15;
            else
                return raw_price * 0.2;
        }else
            return -1;
    }

    public static Object SaleSystem(String csvFileName){
        String inputFilePath = FileConfig.InputScvFileRoot + csvFileName + ".csv";
        try{
            CsvReader csvReader = new CsvReader(inputFilePath);
            csvReader.readHeaders();
            List<Double> sales = new ArrayList<>();

            //构建csv文件
            Object[] head = {"sales", "verification"};
            List<Object> headList = Arrays.asList(head);
            List<List<Object>> dataList = new ArrayList<List<Object>>();

            //遍历原始csv文件数据
            int qualified_num = 0;
            int content_error_num = 0;
            int format_error_num = 0;
            int total_num = 0;
            String statistics = "";
            while(csvReader.readRecord()){
                total_num++;
                String[] record = csvReader.getRawRecord().split(",");
                System.out.println("Data series length: " + record.length);
                List<Object> rowList = new ArrayList<Object>();
                if(record[0].equals("-1")){
                    total_num--;
                    //计算上述所有的销售和
                    System.out.println("\nFinal result!");
                    double price = 0.0;
                    for (int i = 0; i < sales.size(); i++) {
                        price += sales.get(i);
                    }
                    rowList.add(price);
                    dataList.add(rowList);
                    statistics = String.valueOf(price);
                    break;
                }else if(record.length != 4){
                    System.out.println("Wrong data format!!!");
                    rowList.add(-1);
                    rowList.add(false);
                    dataList.add(rowList);
                    format_error_num++;
                }else{
                    double sale = calculateSale(Integer.parseInt(record[0]), Integer.parseInt(record[1]),
                            Integer.parseInt(record[2]));
                    System.out.print("Estimated value: " + sale);
                    double anticipate = Double.parseDouble(record[3]);
                    System.out.println("\tAnticipated value: " + anticipate);
                    if(sale > 0){
                        sales.add(sale);

                        //写入csv数据
                        rowList.add(sale);
                        rowList.add((int)anticipate == (int)sale);
                        qualified_num++;
                    }else if(sale == -1){

                        //数据本身不符合要求
                        rowList.add(-2);
                        rowList.add(false);
                        content_error_num++;
                    }else{
                        //其他奇奇怪怪的问题
                        rowList.add(-3);
                        rowList.add(false);
                    }
                    dataList.add(rowList);
                }

            }
            System.out.println(dataList);
            HashMap<String, Object> result = new HashMap<>();
            result.put("total_num", total_num);
            result.put("qualified_num", qualified_num);
            result.put("content_error_num", content_error_num);
            result.put("format_error_num", format_error_num);
            result.put("statistics", "total sales: " + statistics);

            String outputFilePath = FileConfig.OutputCsvFileRoot + csvFileName + ".csv";
            File csvFile = null;
            BufferedWriter csvWriter = null;
            try{
                csvFile = new File(outputFilePath);
                File parent = csvFile.getParentFile();
                if (parent != null && parent.exists()){
                    parent.mkdirs();
                }
                csvFile.createNewFile();
                csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"));
                writeRow(headList, csvWriter);
                for(List<Object> row : dataList){
                    writeRow(row, csvWriter);
                }
                csvWriter.flush();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                csvWriter.close();
            }
            return result;

        }catch (IOException e){
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