package org.example;

public class Programs {

    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Число должно быть неотрицательным");
        long result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }

    public static double areaTriangle(int base, int height) {
        if (base <= 0 || height <= 0) throw new IllegalArgumentException("Параметры должны быть > 0");
        return 0.5 * base * height;
    }

    public static int calculate(int a, int b, char operation) {
        return switch (operation) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalArgumentException("Неверная операция");
        };
    }

    public static String compare(int a, int b) {
        if (a > b) return a + " больше чем "  + b;
        if (a < b) return a + " меньше чем "  + b;
        return "Числа равны";
    }
}
