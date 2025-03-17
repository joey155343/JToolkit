package 檔案;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class deleteFileBelowDir {

    private static final String PATH = "D:\\Project\\核果";
    private static final String LOG = "log";
    private static final String HTTP_REQUEST = ".idea\\httpRequests";

    private static final boolean IS_DELETE_ALL = true;

    // 刪除專案目錄底下的 \log 和 .\.idea\httpRequests
    public static void main(String[] args) {
        var root = new File(PATH);

        Optional.ofNullable(root.listFiles())
                .stream()
                .flatMap(Arrays::stream)
                .filter(File::isDirectory)
                .peek(d -> System.out.println("=== " + d.getName() + " ==="))
                .flatMap(folder -> Optional.ofNullable(folder.listFiles()).stream().flatMap(Arrays::stream))
                .filter(File::isDirectory)
                .forEach(project -> {
                    System.out.println("專案: " + project.getName());
                    deleteFiles(new File(project, LOG));
                    deleteFiles(new File(project, HTTP_REQUEST));
                    System.out.println();
                });

        System.out.println("全部刪除完成...");
    }

    private static void deleteFiles(File folder) {
        int count = 0;
        if (folder.exists()) {
            File[] listFiles = folder.listFiles();
            for (File listFile : listFiles) {
                if (deleteMoreThanOneDay(listFile)) {
                    listFile.delete();
                    count++;
                }
            }
        }
        System.out.printf("%s 共刪除: %s 筆。 \n", (Object) folder.getName(), Optional.of(count));
    }

    // 刪除一天以上
    private static boolean deleteMoreThanOneDay(File file) {
        boolean result = false;
        long eligibleForDeletion = System.currentTimeMillis() - (24 * 60 * 60 * 1000L); // 清除1天前檔案
        if (IS_DELETE_ALL || file.lastModified() < eligibleForDeletion) {
            result = true;
        }
        return result;
    }
}
