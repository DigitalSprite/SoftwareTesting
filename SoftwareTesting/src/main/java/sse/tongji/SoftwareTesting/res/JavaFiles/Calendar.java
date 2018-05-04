package sse.tongji.SoftwareTesting.res.JavaFiles;

public class Calendar {

    public static double test(String input){
        String[] data = input.split(",");
        if(data.length != 3){
            return -1.0;
        }
        int year = 0;
        int month = 0;
        int day = 0;
        try{
            year = Integer.parseInt(data[0]);
            month = Integer.parseInt(data[1]);
            day = Integer.parseInt(data[2]);
            if (year == 0)
                return -1.0;
            if(month == 1 || month == 3 || month == 5 || month == 7
                    || month == 8 || month == 10 || month == 12){
                if (day >= 1 && day <= 31)
                    return 1.0;
                else
                    return 0.0;
            }else if(month == 4 || month == 6 || month == 9 || month == 11){
                if (day >= 1 && day <= 30)
                    return 1.0;
                else
                    return 0.0;
            }else if(month == 2){
                if (year % 4 == 0 && year % 100 != 0){
                    if (day >= 1 && day <= 29)
                        return 1.0;
                    else
                        return 0.0;
                }
                else{
                    if (day >= 1 && day <= 28)
                        return 1.0;
                    else
                        return 0.0;
                }
            }else{
                return 0.0;
            }
        }catch (Exception e){
            return -1.0;
        }
    }
}
