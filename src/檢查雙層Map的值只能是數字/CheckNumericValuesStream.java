package 檢查雙層Map的值只能是數字;

import java.util.Map;

public class CheckNumericValuesStream {

    public static void main(String[] args) {
        // 假設這是你的 Map amtProgress
        Map<String, Map<String, String>> amtProgress = getAmtProgress();

        // 檢核 value 必須為數字
        if (checkNumericValues(amtProgress)) {
            System.out.println("All values are numeric.");
        } else {
            System.out.println("Some values are not numeric.");
        }
    }

    // 假設這是你的 Map amtProgress
    private static Map<String,  Map<String, String>> getAmtProgress() {
        // 假設這是你的實際資料
        // "amtProgress":{"2022":{"1":"55", "2":"66", "3":"77", "4":"88"},"2023":{"1":"1", "2":"2","3":"3", "4":"4"}}
        // 在實際應用中，你應該有合適的方式來初始化這個 Map
        // 這裡只是一個示例
        return Map.of(
            // "2022", Map.of("1","11","2","12", "3","13", "4","14"),
            // "2023", Map.of("1","", "2","22","3","23", "4","24")
        );
    }

    // 檢核 value 必須為數字
    private static boolean checkNumericValues(Map<String, Map<String, String>> amtProgress) {
        return amtProgress.values().stream()
            .flatMap(yearMap -> yearMap.values().stream())// 平鋪陣列成一個流
            .allMatch(CheckNumericValuesStream::isNumeric);
    }

    // 檢查字串是否為數字
    private static boolean isNumeric(String str) {
        try {
            System.out.print(str+ " ");
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("不符合");
            return false;
        }
    }

}
