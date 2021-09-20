import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Logic {
    private final TreeMap<Integer, String> romanMap = new TreeMap<>();

    {
        Stream.of(RomanNumber.values()).forEach(x ->
                romanMap.put(x.getRomanNumber(), x.name()));
    }

    public void calculation(ArrayList<Integer> integerArrayOperands,
                            @NotNull String currentOperation,
                            boolean presenceRomanNumber) throws Exception {
        int sum;
        switch (currentOperation) {
            case "+":
                sum = integerArrayOperands.get(0) + integerArrayOperands.get(1);
                break;
            case "-":
                sum = integerArrayOperands.get(0) - integerArrayOperands.get(1);
                break;
            case "*":
                sum = integerArrayOperands.get(0) * integerArrayOperands.get(1);
                break;
            default:
                sum = integerArrayOperands.get(0) / integerArrayOperands.get(1);
                break;
        }
        if (presenceRomanNumber) {
            System.out.println(arabicToRoman(sum));
        } else {
            System.out.println(sum);
        }
    }

    public String arabicToRoman(int number) throws Exception {
        if (number < 1) {
            throw new Exception("В римской системе счисления могут быть только положительные числа");
        }
        int key = romanMap.floorKey(number);
        if (number == key) {
            return romanMap.get(number);
        }
        return romanMap.get(key) + arabicToRoman(number - key);
    }
}