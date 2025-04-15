package 日期區間計算;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DateIntervalService {
    /*
    程式碼說明
    - 重點
            子要點
    ---
    - 定義日期區間物件
            使用 Java record 定義 DateInterval，包含 startDate 與 endDate 兩個欄位，並提供靜態方法 of 建立實例。
    - 方法參數
            方法 getAvailableIntervals 接收可變參數：多個 List<DateInterval>，每個 List 代表要排除的日期區間。
    - 初始時間點
    	    以當下日期 +1 日作為可用區間的起始點。
    - 排除區間處理
    	    1. 將所有排除區間用 Stream 平坦化並排序。
            2. 合併重疊或連續的排除區間。
    -可用區間計算
    	    1. 從基準起始日期開始，依序「扣除」排除區間。
            2. 若存在間隔區段，則加入可用區間。
            3. 最後一個可用區間若無上限，則 endDate 為 null。
    -使用 Stream	盡量使用 Stream 來合併與排序，但合併與扣除演算法部分因較複雜而使用傳統迴圈。
     */
    public record DateInterval(LocalDate startDate, LocalDate endDate) {

        public Long getDays() {
            return Objects.isNull(endDate) ? null : 
                java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        }

        public static DateInterval of(LocalDate startDate, LocalDate endDate) {
            return new DateInterval(startDate, endDate);
        }
    }

    /**
     * 主方法：取得未被排除的可用日期區間
     */
    @SafeVarargs
    public static List<DateInterval> getAvailableIntervals(List<DateInterval>... excludeLists) {
        LocalDate baseStart = LocalDate.now().plusDays(1);

        List<DateInterval> excluded = Stream.of(excludeLists)
                .flatMap(Collection::stream)
                .filter(interval -> interval.endDate() == null || !interval.endDate().isBefore(baseStart))
                .sorted(Comparator.comparing(DateInterval::startDate))
                .collect(Collectors.toList());

        List<DateInterval> merged = mergeOverlappingIntervals(excluded, baseStart);

        System.out.println("-----");
        System.out.println("合併後日期區間:");
        merged.forEach(interval ->
                System.out.println("日期: " + interval.startDate() + " ~ " + interval.endDate())
        );
        System.out.println("-----");

        List<DateInterval> available = new ArrayList<>();
        LocalDate current = baseStart;
        for (DateInterval ex : merged) {
            if (ex.startDate().isAfter(current)) {
                LocalDate availableEnd = ex.startDate().minusDays(1);
                if (!current.isAfter(availableEnd)) {
                    available.add(DateInterval.of(current, availableEnd));
                }
            }
            if (ex.endDate() == null) {
                current = null;
                break;
            } else {
                current = ex.endDate().plusDays(1);
            }
        }

        if (current != null) {
            available.add(DateInterval.of(current, null));
        }

        return available;
    }

    /**
     * 合併重疊或相鄰的日期區間
     */
    private static List<DateInterval> mergeOverlappingIntervals(List<DateInterval> intervals, LocalDate baseStartDate) {
        List<DateInterval> result = new ArrayList<>();
        for (DateInterval interval : intervals) {
            if (interval.endDate() != null && interval.endDate().isBefore(baseStartDate)) continue;

            if (result.isEmpty()) {
                result.add(interval);
            } else {
                DateInterval last = result.getLast();

                boolean isOverlappingOrAdjacent =
                        last.endDate() == null || // 無限延伸，自然相交
                                interval.startDate().isBefore(last.endDate().plusDays(1)) || // 有重疊
                                interval.startDate().isEqual(last.endDate().plusDays(1)); // 相鄰

                if (isOverlappingOrAdjacent) {
                    LocalDate newEnd;
                    if (last.endDate() == null || interval.endDate() == null) {
                        newEnd = null;
                    } else {
                        newEnd = last.endDate().isAfter(interval.endDate()) ? last.endDate() : interval.endDate();
                    }
                    result.set(result.size() - 1, DateInterval.of(last.startDate(), newEnd));
                } else {
                    result.add(interval);
                }
            }
        }
        return result;
    }

    // 測試
    public static void main(String[] args) {
        var baseDate = LocalDate.of(2025, 4, 10);
        List<DateInterval> exclude1 = List.of(
                DateInterval.of(baseDate.plusDays(3), baseDate.plusDays(5)),
                DateInterval.of(baseDate.plusDays(10), baseDate.plusDays(12))
        );
        List<DateInterval> exclude2 = List.of(
                DateInterval.of(baseDate.plusDays(6), baseDate.plusDays(8))
        );
        System.out.println("原始日期區間:");
        Stream.of(exclude1, exclude2)
                .flatMap(Collection::stream)
                .forEach(interval ->
                        System.out.println("日期: " + interval.startDate() + " ~ " + interval.endDate())
                );

        List<DateInterval> available = getAvailableIntervals(exclude1, exclude2);
        System.out.println("排除後日期區間:");
        available.forEach(interval ->
                System.out.println("日期: " + interval.startDate() + " ~ " + interval.endDate() + ", 天數:" + interval.getDays())
        );
    }
}
