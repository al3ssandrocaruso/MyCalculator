package com.example.calcolatricescientifica;

import java.math.BigDecimal;

public class Utils {
    public static BigDecimal logBase10(BigDecimal op){
        Double opDouble=Double.valueOf(op+"");
        opDouble=Math.log10(opDouble);
        BigDecimal output=new BigDecimal(opDouble+"");
        return output;
    }
    public static BigDecimal pow(BigDecimal a,BigDecimal b){ //performs a^b
        Double op1=Double.valueOf(a+"");
        Double op2=Double.valueOf(b+"");
        Double output;
        output=Math.pow(op1,op2);
        BigDecimal bOutput=new BigDecimal(output+"");
        return bOutput;
    }
}
