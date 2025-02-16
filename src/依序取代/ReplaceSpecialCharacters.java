package 依序取代;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceSpecialCharacters {

    public static void main(String[] args) {
        String inputString = "□A同學  □B同學  □C同學  □D同學";
        int[] replaceArray = {1, 0, 1, 1};

        // 定義替換邏輯的Predicate
        String result = replaceSpecialCharacters(inputString, index -> (replaceArray[index] == 0));

        System.out.println(result);
    }

    public static String replaceSpecialCharacters(String inputString, Predicate<Integer> replacementPredicate) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("□");
        Matcher matcher = pattern.matcher(inputString);

        int matchCount = 0; // 用來計算匹配次數

        while (matcher.find()) {
            matchCount++;
            String replacement = replacementPredicate.test(matchCount - 1) ? "□" : "■";
            matcher.appendReplacement(result, replacement);
        }

        matcher.appendTail(result);

        return result.toString();
    }

}
