package listener;

import execute.ExecuteQuery;
import utils.DialogUtil;
import utils.ExportUtil;
import window.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * 组件动作监听
 */
public class ActionListener {
    private Logger logger=Logger.getLogger(ActionListener.class.toString());
    private static ActionListener listener;
    //主框架对象
    private static MainFrame mainFrame;
    //查询状态,true:正在查询中,false:未查询
    private static boolean status;

    private static long startTime;
    private static long endTime;

    public ActionListener(){}

    public ActionListener(MainFrame mainFrame){
        this.mainFrame=mainFrame;
        initListener();
    }

    public static ActionListener getInstance(MainFrame mainFrame){
        if(null == listener){
            listener=new ActionListener(mainFrame);
        }
        return listener;
    }

    /**
     * 初始化监听
     */
    public void initListener(){
        if(null!=mainFrame){
            executeQueryListener();
            keyboardListener();
            exportListener();
        }else {
            DialogUtil.dialog("监听事件初始化失败!",JOptionPane.ERROR_MESSAGE,"错误",4000);
        }
    }

    /**
     * 执行查询监听，查询按钮点击监听
     */
    public void executeQueryListener(){
        JButton button=mainFrame.execute;
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query();
            }
        });
    }

    /**
     * 执行查询监听,监听键盘回车(Enter)是否按下并释放
     */
    public void keyboardListener(){
        JTextField field=mainFrame.kw;
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                //回车按下并释放
                if(10==e.getKeyCode()){
                    query();
                }
            }
        });
    }

    /**
     * 查询
     */
    public void query(){
        startTime=new Date().getTime();
        logger.info("执行开始时间:"+startTime);
        if(!status){
            String input=mainFrame.kw.getText();
            if(null!=input && !"".equals(input)){
                mainFrame.result.setText("");
                ExecuteQuery.getInstance().execute(scanDisks(),input,mainFrame.result);
                status=true;
            }else {
                DialogUtil.dialog("请输入查询文件的关键字信息!",JOptionPane.INFORMATION_MESSAGE,"提示",2000);
            }
        }else{
            DialogUtil.dialog("程序正在执行中...",JOptionPane.INFORMATION_MESSAGE,"提示",2000);
        }
    }

    /**
     * 选择需要扫描的盘符，未匹配上相关选择盘符信息时，默认全盘扫描
     * @return 需要扫描盘符数组
     */
    public List<String> scanDisks(){
        List<String> re=new ArrayList<>();
        List<String> disks=mainFrame.disks;
        //选择的扫描盘符
        String selectDisk=mainFrame.diskList.getSelectedItem().toString();
        if(null!=disks && disks.size()>0){
            for(String disk:disks){
                if(!disk.equals(selectDisk)){
                    continue;
                }else {
                    re.add(selectDisk);
                }
            }
        }
        return re.size()>0?re:disks;
    }

    /**
     * 导出监听，导出按钮点击监听
     */
    public void exportListener(){
        JButton export=mainFrame.export;
        export.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //程序不处于执行中状态时
                if(!status){
                    //获取导出路径
                    String exportPath=DialogUtil.exportPathDialog(mainFrame);
                    if(!"".equals(exportPath)){
                        //确认是否导出查询内容结果
                        int confirm=DialogUtil.confirmDialog(mainFrame,"是否将查询结果导出到指定文件目录下?","导出确认",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                        if(confirm==JOptionPane.YES_OPTION){
                            //导出文件格式
                            String type=mainFrame.exportFormatList.getSelectedItem().toString();
                            String result=mainFrame.result.getText();
                            if(!"".equals(result)){
                                try {
                                    ExportUtil.getInstance().export(type,result,exportPath);
                                    DialogUtil.dialog("文件导出成功!",JOptionPane.INFORMATION_MESSAGE,"提示",2000);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    DialogUtil.dialog("文件导出失败!",JOptionPane.ERROR_MESSAGE,"错误",4000);
                                }
                            }else {
                                DialogUtil.dialog("查询结果为空,无法进行导出操作!", JOptionPane.INFORMATION_MESSAGE,"提示",2000);
                            }
                        }
                    }
                }else {
                    DialogUtil.dialog("程序正在执行中,无法进行导出操作!",JOptionPane.INFORMATION_MESSAGE,"提示",2000);
                }
            }
        });
    }

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        ActionListener.status = status;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        ActionListener.startTime = startTime;
    }

    public static long getEndTime() {
        return endTime;
    }

    public static void setEndTime(long endTime) {
        ActionListener.endTime = endTime;
    }
}
