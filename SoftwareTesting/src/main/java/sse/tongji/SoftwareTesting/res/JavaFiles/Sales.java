package sse.tongji.SoftwareTesting.res.JavaFiles;

public class Sales {

    public static double calculateSale(String input){
        String[] data = input.split(",");
        int mainframe = 0;
        int displayer = 0;
        int peripheral = 0;
        double result = 0.0;
        try{
            mainframe = Integer.parseInt(data[0]);
            displayer = Integer.parseInt(data[1]);
            peripheral = Integer.parseInt(data[2]);
            result = Double.parseDouble(data[3]);
        }catch (Exception e){
            return -1;
        }

        if(mainframe > 1 && mainframe <= 70 && displayer > 1 && displayer <= 70 && peripheral > 1 && peripheral <= 70){
            double k = 0.0;
            double raw_price = (int)mainframe * 25 + (int)displayer * 30 + (int)peripheral * 45;
            if(raw_price >= 0 && raw_price <= 1000)
                k =  raw_price * 0.1;
            else if(raw_price > 1000 && raw_price <= 1800)
                k = raw_price * 0.15;
            else
                k = raw_price * 0.2;
            if(k == result)
                return 1;
            else
                return 0;
        }else
            return 0;
    }
}
