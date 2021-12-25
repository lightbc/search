package execute;

import utils.DialogUtil;
import utils.ScanDisksUtil;

import javax.swing.*;
import java.util.List;

/**
 * 执行查询
 */
public class ExecuteQuery {

    private static ExecuteQuery executeQuery;

    public static ExecuteQuery getInstance(){
        if(null==executeQuery){
            executeQuery=new ExecuteQuery();
        }
        return executeQuery;
    }

    /**
     * 执行
     * @param disks 盘符数组（盘符信息）
     * @param input 输入内容
     * @param show 展示查询结果的组件
     */
    public void execute(List<String> disks, String input, JTextPane show){
        executeQuery(disks,input,show);
    }

    /**
     * 执行查询
     * @param disks 盘符数组 （盘符信息）
     * @param input 输入内容
     * @param show 展示查询结果的组件
     */
    private void executeQuery(List<String> disks,String input, JTextPane show){
        try {
            query(disks,input,show);
        } catch (InterruptedException e) {
            e.printStackTrace();
            DialogUtil.dialog("线程中断:"+e.getMessage(),JOptionPane.ERROR_MESSAGE,"错误",4000);
        }
    }

    /**
     * 查询
     * @param disks 盘符数组 （盘符信息）
     * @param input 输入内容
     * @param show 展示查询结果的组件
     * @throws InterruptedException 中断异常
     */
    private void query(List<String> disks,String input, JTextPane show) throws InterruptedException {
        if(null!=disks&&disks.size()>0){
            new ScanDisksUtil(input,show,disks).scan();
        }
    }

}
