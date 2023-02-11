package com.vanart.okxnotificationbot;

public class ArithmeticUtils {
    public static Double countPriceStep(Double price) {
        var step = Constants.PRISE_MIN_STEP;
        while (step < price) {
            step *= 10;
        }
        return step;
    }
}
