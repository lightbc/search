package utils;

import exceptions.InvalidTimeException;
import listener.ActionListener;

import javax.swing.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * 扫描盘符工具类
 */
public class ScanDisksUtil{
    private Logger logger=Logger.getLogger(ScanDisksUtil.class.toString());
    //输入的查询匹配信息
    private String eq;
    //显示结果的组件
    private JTextPane show;
    //盘符数组
    private List<String> disks;
    //计数器
    private int count;
    //扫描文件总数
    private long fileCount;
    //查询符合条件文件数
    private long fileValid;

    public ScanDisksUtil(){

    }

    public ScanDisksUtil(String eq, JTextPane show, List<String> disks){
        this.eq=eq.toLowerCase();
        this.show=show;
        this.disks=disks;
        this.count=disks.size();
        fileCount=0;
    }

    /**
     * 扫描选择的磁盘
     * @throws InterruptedException 中断异常
     */
    public void scan() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(disks.size());
        for(int i=0;i<disks.size();i++){
            new Thread(new ScanDisksThread(disks.get(i),countDownLatch)).start();
        }
    }

    /**
     * 扫描磁盘线程类
     */
    private class ScanDisksThread implements Runnable{
        //磁盘根路径
        private String root;
        private CountDownLatch countDownLatch;

        public ScanDisksThread(){

        }

        public ScanDisksThread(String root,CountDownLatch countDownLatch){
            this.root=root;
            this.countDownLatch=countDownLatch;
        }

        @Override
        public void run(){
            try {
                findFiles();
                count--;
                logger.info("当前线程:"+Thread.currentThread().getName());
            }catch (Exception e){
                e.printStackTrace();
                DialogUtil.dialog("线程"+Thread.currentThread().getName()+"查找文件报错:"+e.getMessage(),JOptionPane.ERROR_MESSAGE,"错误",4000);
            }finally {
                if(count==0){
                    DialogUtil.dialog("查询完成!",JOptionPane.INFORMATION_MESSAGE,"提示",2000);
                    //更新组件动作监听类的查询状态字段
                    ActionListener.setStatus(false);
                    long endTime=new Date().getTime();
                    logger.info("执行结束时间:"+endTime);
                    ActionListener.setEndTime(endTime);
                    //计算总耗时
                    String elapsedTime= null;
                    try {
                        elapsedTime = DateUtil.getInstance().getElapsedTime(ActionListener.getStartTime(),ActionListener.getEndTime());
                    } catch (InvalidTimeException e) {
                        e.printStackTrace();
                        DialogUtil.dialog("无效时间错误！",JOptionPane.ERROR_MESSAGE,"错误",4000);
                    }
                    DialogUtil.dialog("总耗时:"+elapsedTime+",扫描文件总数:"+fileCount+",符合查询数:"+fileValid,JOptionPane.INFORMATION_MESSAGE,"提示");
                }
                countDownLatch.countDown();
            }
        }

        /**
         * 查找文件
         */
        public void findFiles(){
            File file=new File(root);
            File[] files=file.listFiles();
            if(null != files && files.length>0){
                findByIterator(files);
            }
        }

        /**
         * 迭代查找文件
         * @param files 文件数组
         */
        public void findByIterator(File[] files){
            for(File file:files){
                //文件不是目录
                if(!file.isDirectory()){
                    //忽略文件名大小写
                    String fileName=file.getName().toLowerCase();
                    //匹配输入关键字中是否包含通配符"*"
                    if(eq.indexOf("*") != -1){
                        String regx=eq.replaceAll("\\*",".*");

                        if(Pattern.matches(regx,fileName)){
                            String path=file.getAbsolutePath()+"\n";
                            //显示组件中实时显示匹配上的数据信息
                            show.setText(show.getText()+path);
                            fileValid++;
                        }
                    }else {//输入关键字中未有"*"通配符时，进行全文件名匹配
                        if(eq.equals(fileName)){
                            String path=file.getAbsolutePath()+"\n";
                            //显示组件中实时显示匹配上的数据信息
                            show.setText(show.getText()+path);
                            fileValid++;
                        }
                    }
                    fileCount++;
                }else {//文件为目录不是格式文件的，循环调用自身
                    File[] innnerFiles=file.listFiles();
                    if(null != innnerFiles && innnerFiles.length>0){
                        findByIterator(innnerFiles);
                    }
                }
            }
        }
    }

}
