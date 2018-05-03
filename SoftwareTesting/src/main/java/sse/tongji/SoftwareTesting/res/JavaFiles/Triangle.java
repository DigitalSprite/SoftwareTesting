package sse.tongji.SoftwareTesting.res.JavaFiles;

public class Triangle {

    public static double test(String input){
        try{
            String[] data = input.split(",");
            double a1 = Double.parseDouble(data[0]);
            double a2 = Double.parseDouble(data[1]);
            double a3 = Double.parseDouble(data[2]);

            String result = data[3];
            if (a1 + a2 > a3 && Math.abs(a1 - a2) < a3
                    && a1 + a3 > a2 && Math.abs(a1 - a3) < a2
                    && a2 + a3 > a1 && Math.abs(a2 - a3) < a1){
                if (result.equals("rectangle")){
                    return 1.0;
                }else if(result.equals("isosceles triangle")){
                    if (a1 == a2 || a1 == a3 || a2 == a3)
                        return 1.0;
                    else
                        return 0.0;
                }else if(result.equals("equilateral triangle")){
                    if (a1 == a2 && a2 == a3)
                        return 1.0;
                    else
                        return 0.0;
                }else
                    return -1.0;
            }else{
                // 结果不对
                return 0.0;
            }
        }catch (Exception e){
            return -1.0;
        }
    }
}