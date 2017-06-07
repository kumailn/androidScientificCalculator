package com.example.kumail.scientificcalculator;


import org.mariuszgromada.math.mxparser.Expression;

/**
 * Created by Kumail on 04-Jun-17.
 */

public class Rational {

    private int num, denom;

    public Rational(double d) {
        String s = String.valueOf(d);
        int digitsDec = s.length() - 1 - s.indexOf('.');

        int denom = 1;
        for(int i = 0; i < digitsDec; i++){
            d *= 10;
            denom *= 10;
        }
        int num = (int) Math.round(d);

        this.num = num; this.denom = denom;
    }

    public Rational(int num, int denom) {
        this.num = num; this.denom = denom;
    }

    public String toString() {
        return String.valueOf(num) + "/" + String.valueOf(denom);
    }

    public static void main(String[] args) {
        System.out.println(new Rational(123.456));
    }

    public String simple(){
        Expression e = new Expression("gcd(" + String.valueOf(num) + "," + String.valueOf(denom) + ")");
        Double result2 = Double.parseDouble(String.valueOf(e.calculate()));
        int result = result2.intValue();

        while(result != 1){
            num = num / result;
            denom = denom / result;
            e = new Expression("gcd(" + String.valueOf(num) + "," + String.valueOf(denom) + ")");
            result2 = Double.parseDouble(String.valueOf(e.calculate()));
            result = result2.intValue();
        }
        return String.valueOf(num) + "/" + String.valueOf(denom);
    }
}