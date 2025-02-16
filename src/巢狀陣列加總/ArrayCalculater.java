package 巢狀陣列加總;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class ArrayCalculater {
    private BigDecimal[] values;

    public ArrayCalculater(BigDecimal[] values) {
        this.values = values;
    }

    public BigDecimal[] getValues() {
        return values;
    }

    // 計算多個陣列的總和，按照對應索引位置相加
    public static BigDecimal[] calculateTotal(ArrayCalculater... ArrayCalculaters) {
        if (ArrayCalculaters == null || ArrayCalculaters.length == 0) {
            return new BigDecimal[0];
        }

        int length = ArrayCalculaters[0].values.length;
        BigDecimal[] result = new BigDecimal[length];

        // 一般寫法
        // for (int i = 0; i < length; i++) {
        //     BigDecimal sum = BigDecimal.ZERO;
        //     for (ArrayCalculater payment : ArrayCalculaters) {
        //         sum = sum.add(payment.values[i]);
        //     }
        //     result[i] = sum;
        // }

        for (int i = 0; i < length; i++) {
            int index = i;
            result[i] = Arrays.stream(ArrayCalculaters)
                    .filter(Objects::nonNull)
                    .filter(ArrayCalculater -> ArrayCalculater.values != null)
                    .map(ArrayCalculater -> ArrayCalculater.values[index])
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return result;
    }

    public static void main(String[] args) {
        BigDecimal[] valuesA1 = {BigDecimal.valueOf(0), BigDecimal.valueOf(6)};
        BigDecimal[] valuesA2 = {BigDecimal.valueOf(1), BigDecimal.valueOf(7)};
        BigDecimal[] valuesA3 = {BigDecimal.valueOf(2), BigDecimal.valueOf(8)};
        BigDecimal[] valuesB1 = {BigDecimal.valueOf(3), BigDecimal.valueOf(9)};
        BigDecimal[] valuesB2 = {BigDecimal.valueOf(4), BigDecimal.valueOf(10)};
        BigDecimal[] valuesB3 = {BigDecimal.valueOf(5), BigDecimal.valueOf(11)};

        ArrayCalculater arrayCalculaterA1 = new ArrayCalculater(valuesA1);
        ArrayCalculater arrayCalculaterA2 = new ArrayCalculater(valuesA2);
        ArrayCalculater arrayCalculaterA3 = new ArrayCalculater(valuesA3);
        ArrayCalculater arrayCalculaterB1 = new ArrayCalculater(valuesB1);
        ArrayCalculater arrayCalculaterB2 = new ArrayCalculater(valuesB2);
        ArrayCalculater arrayCalculaterB3 = new ArrayCalculater(valuesB3);

        BigDecimal[] valuesATotal = ArrayCalculater.calculateTotal(arrayCalculaterA1, arrayCalculaterA2, arrayCalculaterA3);
        BigDecimal[] valuesBTotal = ArrayCalculater.calculateTotal(arrayCalculaterB1, arrayCalculaterB2, arrayCalculaterB3);
        BigDecimal[] values1Total = ArrayCalculater.calculateTotal(arrayCalculaterA1, arrayCalculaterB1);
        BigDecimal[] values2Total = ArrayCalculater.calculateTotal(arrayCalculaterA2, arrayCalculaterB2);
        BigDecimal[] values3Total = ArrayCalculater.calculateTotal(arrayCalculaterA3, arrayCalculaterB3);
        BigDecimal[] valuesTotal = ArrayCalculater.calculateTotal(arrayCalculaterA1, arrayCalculaterA2, arrayCalculaterA3, arrayCalculaterB1, arrayCalculaterB2, arrayCalculaterB3);

        System.out.print("valuesATotal:");
        printArray(valuesATotal);
        System.out.print("valuesBTotal:");
        printArray(valuesBTotal);
        System.out.print("values1Total:");
        printArray(values1Total);
        System.out.print("values2Total:");
        printArray(values2Total);
        System.out.print("values3Total:");
        printArray(values3Total);
        System.out.print("valuesTotal:");
        printArray(valuesTotal);
    }

    // Helper method to print BigDecimal array
    private static void printArray(BigDecimal[] array) {
        System.out.print(" [ ");
        for (BigDecimal value : array) {
            System.out.print(value + " ");
        }
        System.out.print("]");
        System.out.println();
    }
}
