package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 磁盘工具类
 */
public class DiskUtil {

    private static DiskUtil diskUtil;

    /**
     * 获取实例
     * @return DiskUtil实例
     */
    public static DiskUtil getInstance(){
        if(null == diskUtil){
            diskUtil=new DiskUtil();
        }
        return diskUtil;
    }

    /**
     * 获取盘符字符串数组
     * @return 字符串数组
     */
    public List<String> getDisks(){
        List<String> list=new ArrayList<>();
        File[] files=File.listRoots();
        for(int i=0;i<files.length;i++){
            list.add(files[i].getPath());
        }
        return list;
    }

}
