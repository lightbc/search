package utils;

import enums.ExportType;

import javax.swing.*;
import java.io.*;

/**
 * 导出功能相关工具类
 */
public class ExportUtil {
    private static final String TXT="txt";
    private static final String WORD="word";

    private static ExportUtil exportUtil;

    public static ExportUtil getInstance() {
        if (null == exportUtil) {
            exportUtil = new ExportUtil();
        }
        return exportUtil;
    }

    /**
     * 导出
     * @param type 导出文件格式类型
     * @param result 导出的查询结果信息
     * @param exportPath 导出路径
     * @throws Exception
     */
    public void export(String type,String result,String exportPath) throws Exception{
        switch (type.toLowerCase()) {//忽略类型大小写
            case TXT:
                exportTxt(result,exportPath, ExportType.TXT.getExt());
                break;
            case WORD:
                exportWord(result,exportPath,ExportType.WORD.getExt());
                break;
            default:
                DialogUtil.dialog("未匹配到先关可导出格式!", JOptionPane.ERROR_MESSAGE, "错误", 4000);
        }
    }

    /**
     * 导出文本格式文件
     * @param exportContent 导出内容
     * @param exportPath 导出路径
     * @param ext 拓展名
     * @throws Exception
     */
    private void exportTxt(String exportContent,String exportPath,String ext) throws Exception {
        exportContent=exportContent.replaceAll("\n","\r\n");
        exportFile(exportContent,exportPath,ext);
    }

    /**
     * 导出word格式文件
     * @param exportContent 导出内容
     * @param exportPath 导出路径
     * @param ext 拓展名
     * @throws Exception
     */
    private void exportWord(String exportContent,String exportPath,String ext) throws Exception {
        exportFile(exportContent,exportPath,ext);
    }

    /**
     * 导出文件
     * @param exportContent 导出内容
     * @param exportPath 导出路径
     * @param ext 拓展名
     * @throws Exception
     */
    private void exportFile(String exportContent,String exportPath,String ext) throws Exception {
        InputStream is=new ByteArrayInputStream(exportContent.getBytes());
        BufferedInputStream bis=new BufferedInputStream(is);
        int len;
        byte[] bytes=new byte[2048];
        //在指定导出文件目录下，导出指定格式名为result的查询结果文件
        File file=new File(exportPath+File.separator+"result"+ext);
        OutputStream out=new FileOutputStream(file,true);
        while ((len=bis.read(bytes)) != -1){
            out.write(bytes,0,len);
        }
        out.flush();
        out.close();
        bis.close();
        is.close();
    }
}
