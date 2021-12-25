package utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class DialogUtil {

    /**
     * 普通提示框
     * @param message 消息
     * @param messageType 消息类型
     * @param title 标题
     */
    public static void dialog(Object message,int messageType,String title){
        dialog(message,messageType,title,-1);
    }

    /**
     * 定时关闭对话框
     * @param message 消息
     * @param messageType 消息类型
     * @param title 标题
     * @param delay 延迟时间
     */
    public static void dialog(Object message,int messageType,String title,long delay){
        JOptionPane optionPane=new JOptionPane(message,messageType);
        JDialog dialog=optionPane.createDialog(title);
        if(delay>0){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    dialog.dispose();
                }
            },delay);
        }
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /**
     * 确认对话框
     * @param parent 复机组件
     * @param message 消息
     * @param title 标题
     * @param optionType 操作类型
     * @param messageType 消息类型
     * @return
     */
    public static int confirmDialog(Component parent,Object message,String title,int optionType,int messageType){
        return JOptionPane.showConfirmDialog(parent,message,title,optionType,messageType);
    }

    /**
     * 选择导出路径对话框
     * @param parent 父级组件
     * @return 保存路径
     */
    public static String exportPathDialog(Component parent){
        File file=null;
        JFileChooser fileChooser=new JFileChooser();
        //默认当前文件夹
        fileChooser.setCurrentDirectory(new File("."));
        //只能选择文件目录
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int re=fileChooser.showOpenDialog(parent);
        //确认选择
        if(re==JFileChooser.APPROVE_OPTION){
            file=fileChooser.getSelectedFile();
        }
        return file!=null?file.getAbsolutePath():"";
    }


}
