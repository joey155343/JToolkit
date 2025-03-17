package 檔案;

import java.io.File;
import java.util.ArrayList;

public class ReadFileName {
	public static ArrayList<String> dirAllStrArr = new ArrayList<String>();
	public static void DirAll(File dirFile) throws Exception { 
		if (dirFile.exists()) { 
			File files[] = dirFile.listFiles(); 
			for (File file : files) { 
				//如果遇到資料夾則遞迴呼叫。 
				if (file.isDirectory()) { 
					// 遞迴呼叫 
					DirAll(file); 
				} else { 
					dirAllStrArr.add(dirFile.getPath()+"\\"+file.getName()); 
				} 
			} 
		} 
	} 
	
	public static void findAllFilesInFolder(File folder) {
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				System.out.println(file.getName());
			} else {
				findAllFilesInFolder(file);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		// 遞回讀取裡面檔案
		File f = new File("C:\\Users\\Joey\\Downloads");
		DirAll(f);
		for(String st :dirAllStrArr) {
			System.out.println(st);
		}
		
		// 遞回讀取裡面檔案2
//		File f = new File("C:\\Users\\2008004\\Downloads");
//		findAllFilesInFolder(f);
		
		// 讀取單個檔案
//        String route,name;
//        route = "C:\\Users\\2008004\\Downloads";
//        name = "工作事項.txt";
//        File dir = new File(route);
//        File filename = new File(route,name);
//        System.out.println("檔案名稱 : "+name);
//        System.out.println("檔案是否存在 : "+filename.exists());
//        System.out.println("檔案所資料夾 : "+filename.getParent());//取得file路徑
//        System.out.println("是否為資料夾 : "+filename.isDirectory());
//        System.out.println("是否為檔案 : "+filename.isFile());
//        System.out.println("可讀取 : "+filename.canRead());//是否可讀取
//        System.out.println("可寫入 : "+filename.canWrite());//是否可寫入
//        System.out.println("可執行 : "+filename.canExecute());//是否可執行
//        System.out.println("檔案大小 : "+filename.length());//檔案長度
        
        // 讀取路徑
//        String route;
//        route = "C://Users//2008004//Downloads";
//        File tmp = new File(route);
//        String s[] = tmp.list(); //把該路徑的所有資料夾和檔案輸入s陣列中
//        System.out.println("檔案路徑 : "+route);
//        System.out.println("此路徑有"+s.length+"個資料");
//        for(int i=0;i<s.length;i++){
//            File site = new File(route+"/"+s[i]);
//            if(site.isDirectory()){
//                System.out.println(s[i]+" is a Directory");
//            }
//            else if(site.isFile()){
//                System.out.println(s[i]+" is a File");
//            }
//        }
	}
}
