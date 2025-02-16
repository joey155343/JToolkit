package 執行續安全;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MailComponent {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // testMail a = new testMail();
        Runnable addMailtoTaskA = () -> {
            testMail a = new testMail();
            a.setDo("A");
            a.addMail("A", "A1", Arrays.asList("A2", "A3", "A4"), "A5", "A6");
            a.print();
        };

        Runnable addMailtoTaskB = () -> {
            testMail a = new testMail();
            a.setDo("B");
            a.addMail("B", "B1", Arrays.asList("B2", "B3", "B4"), "B5", null);
            a.print();
        };

        Runnable addMailtoTaskC = () -> {
            testMail a = new testMail();
            a.setDo("C");
            a.addMail("C", "C1", List.of(), "C5", "C6");
            a.print();
        };

        Runnable addMailtoTaskD = () -> {
            testMail a = new testMail();
            a.setDo("D");
            a.addMail("D", "D1", Arrays.asList("D2", "D3", "D4"), "", null);
            a.print();
        };

        // 提交相同的任務到執行緒池
        for (int i = 0; i < 2; i++) {
            executorService.submit(addMailtoTaskA);
            executorService.submit(addMailtoTaskB);
            executorService.submit(addMailtoTaskC);
            executorService.submit(addMailtoTaskD);
        }

        // executorService.submit(addMailtoTaskA);
        // executorService.submit(addMailtoTaskB);
        // executorService.submit(addMailtoTaskC);
        // executorService.submit(addMailtoTaskD);

        // 等待所有任務完成
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}

class testMail {

    private String mailto = "";

    private String ext = "";

    void setDo(String ext) {
        this.ext = ext;
    }

    // @Synchronized
    void addMail(String objName, String a, List<String> b, String c, String d) {
        // 原始的
        // System.out.println("MailComponent2");
        // mailto = new MailComponent2(objName).addMailto(a)
        //     .addMailto(b)
        //     .addMailto(c)
        //     .addMailto(d)
        //     .getMailto();


        // 將 builder 改 Static 並且 new
        // System.out.println("MailComponent3");
        // mailto = new MailComponent3.mailToBuilder(objName)
        //     .addMailto(a)
        //     .addMailto(b)
        //     .addMailto(c)
        //     .addMailto(d)
        //     .getMailto();

        // System.out.println("MailComponent4");
        mailto = new MailComponent4(objName, b, a, c, d)
                .getMailto();
    }

    void print() {
        // System.out.println("print");
        System.out.println(ext + " Service : " + mailto);
        // System.out.println(mailto);
    }

}

class MailComponent2 {

    private final String objName;

    MailComponent2(String objName) {
        this.objName = objName;
    }

    private final Set<String> mailto = new HashSet<>();

    public MailComponent2 addMailto(String mailtoAdd) {
        Set<String> mailtoAddSet = Set.of(mailtoAdd.split(";"));
        mailto.addAll(mailtoAddSet);
        return this;
    }

    public MailComponent2 addMailto(List<String> mailtoAdd) {
        Set<String> mailtoAddSet = new HashSet<>(mailtoAdd);
        mailto.addAll(mailtoAddSet);
        return this;
    }

    public String getMailto() {
        return objName + " 實際的 mailto = " + String.join(";", mailto);
    }
}

class MailComponent3 {

    public static class mailToBuilder {

        private final String objName;

        mailToBuilder(String objName) {
            this.objName = objName;
        }

        private final Set<String> mailto = ConcurrentHashMap.newKeySet();

        public mailToBuilder addMailto(String mailtoAdd) {
            // System.out.println(objName +" 使用者 加入前 = "+String.join(";", mailto) + " | ");
            // System.out.println(objName +" 使用者 加入 addMail= "+mailtoAdd + " | ");
            Set<String> mailtoAddSet = Set.of(mailtoAdd.split(";"));
            mailto.addAll(mailtoAddSet);
            // System.out.println(objName +" 使用者 加入後 = "+String.join(";", mailto));
            return this;
        }

        public mailToBuilder addMailto(List<String> mailtoAdd) {
            // System.out.println(objName +" 使用者 加入前 = "+String.join(";", mailto) + " | ");
            // System.out.println(objName +" 使用者 加入 addMail= "+String.join(";", mailtoAdd) + " | ");
            Set<String> mailtoAddSet = new HashSet<>(mailtoAdd);
            mailto.addAll(mailtoAddSet);
            // System.out.println(objName +" 使用者 加入後 = "+String.join(";", mailto));
            return this;
        }

        public String getMailto() {
            return objName + " 使用者 完成 mailto = " + String.join(";", mailto);
        }
    }

}

class MailComponent4 {
    private final String objName;

    MailComponent4(String objName, List<String> mailtoList, String... mailtos) {
        this.objName = objName;
        addMailto(mailtoList);
        for (String mailto : mailtos) {
            if (StringUtils.isNotBlank(mailto))
                addMailto(mailto);
        }
    }

    private final Set<String> mailto = new HashSet<>();

    private void addMailto(String mailtoAdd) {
        Set<String> mailtoAddSet = Set.of(mailtoAdd.split(";"));
        mailto.addAll(mailtoAddSet);
    }

    private void addMailto(List<String> mailtoAdd) {
        Set<String> mailtoAddSet = new HashSet<>(mailtoAdd);
        mailto.addAll(mailtoAddSet);
    }

    public String getMailto() {
        return objName + " 實際的 mailto = " + String.join(";", mailto);
    }

}
