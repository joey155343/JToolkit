package 檔案;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class ReName {
	public static void main(String[] args) throws Exception {
		
		// 讀取單個檔案
        String route,name,newName;
        route = "C:\\Users\\2008004\\Downloads\\Opendata_勞動部系統\\111年\\Xml處理\\19452國民年金保險基金會計月報";
        File dir = new File(route);
        for(File file : dir.listFiles()) {
        	name = file.getName();
        	if (StringUtils.equals(name.substring(3,4), "0"))
        		newName = name.substring(0,3)+"年" + name.substring(4,5)+"月國民年金保險基金會計月報.zip";
        	else
        		newName = name.substring(0,3)+"年" + name.substring(3,5)+"月國民年金保險基金會計月報.zip";
        	File newfilename = new File(route,newName);
        	boolean success = file.renameTo(newfilename);
        	if (!success)
        		System.out.println("檔案名稱 : " + name + " to "+ newName +" "+ success);
        }
        System.out.println("完成");

	}
}
