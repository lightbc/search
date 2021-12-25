package window;

import listener.ActionListener;
import utils.DiskUtil;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * 主框架类
 */
public class MainFrame extends JFrame {
    //logo
    private static final URL APP_LOGO=MainFrame.class.getResource("/assets/icon/logo.png");
    //窗体固定宽度
    private static final int WINDOW_WIDTH=500;
    //窗体固定高度
    private static final int WINDOW_HEIGHT=440;
    //程序名称
    private static final String WINDOW_TITLE="Search";

    //磁盘信息数组
    public static List<String> disks;
    //磁盘下拉选择列表
    public static JComboBox diskList;
    //搜索关键词
    public static JTextField kw;
    //执行查询按钮
    public static JButton execute;
    //结果文本框
    public static JTextPane result;
    //导出文件格式下拉选择列表
    public static JComboBox exportFormatList;
    //导出按钮
    public static JButton export;

    public MainFrame(){
        init();
    }

    /**
     * 初始化
     */
    public void init(){
        //logo
        Image iconImage=Toolkit.getDefaultToolkit().getImage(APP_LOGO);
        //标题
        this.setTitle(WINDOW_TITLE);
        this.setIconImage(iconImage);
        //窗体大小
        this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        //窗体屏幕居中
        this.setLocationRelativeTo(null);
        //大小不可调整
        this.setResizable(false);
        //设置关闭退出
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //初始化组件
        initComponent();
        //设置窗体可见
        this.setVisible(true);
    }

    /**
     * 初始化组件
     */
    public void initComponent(){
        JPanel mainPanel=new JPanel(null);
        Dimension dimension=new Dimension(-1,-1);
        mainPanel.setPreferredSize(dimension);
        mainPanel.setMaximumSize(dimension);
        mainPanel.setMinimumSize(dimension);
        this.add(mainPanel);

        //选择盘符组件
        selectDiskComponent(mainPanel);
        //输入关键词组件
        inputKeyWordsComponent(mainPanel);
        //执行查询组件
        executeQueryComponent(mainPanel);
        //查询结果显示组件
        resultComponent(mainPanel);
        //导出格式组件
        exportFormat(mainPanel);
        //导出按钮
        exportBtn(mainPanel);
        //组件事件监听
        ActionListener.getInstance(this);
    }

    /**
     * 选择扫描磁盘
     * @param panel 父级面板
     */
    public void selectDiskComponent(JPanel panel){
        diskList=new JComboBox();
        //获取盘符信息
        disks=DiskUtil.getInstance().getDisks();
        if(null != disks && disks.size()>0){
            diskList.addItem("全盘");
            for(String disk:disks){
                diskList.addItem(disk);
            }
            diskList.setBounds(10,10,70,25);
            panel.add(diskList);
        }else {
            JOptionPane.showMessageDialog(this,"未查询到相关盘符信息！","错误",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * 关键词输入组件
     * @param panel 父级面板
     */
    public void inputKeyWordsComponent(JPanel panel){
        kw=new JTextField();
        kw.setBounds(90,10,300,25);
        panel.add(kw);
    }

    /**
     * 执行查询组件
     * @param panel 父级面板
     */
    public void executeQueryComponent(JPanel panel){
        execute=new JButton("查询");
        execute.setBounds(400,10,70,25);
        panel.add(execute);
    }

    /**
     * 查询结果显示组件
     * @param panel 父级面板
     */
    public void resultComponent(JPanel panel){
        result=new JTextPane();
        //设置jtextpane的垂直滚动条一直在底部，显示最新内容
        DefaultCaret caret=(DefaultCaret) result.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        //控制显示内容的行高
        SimpleAttributeSet simpleAttributeSet=new SimpleAttributeSet();
        StyleConstants.setLineSpacing(simpleAttributeSet,.3f);
        result.setParagraphAttributes(simpleAttributeSet,false);

        JScrollPane scrollPane=new JScrollPane(result,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10,45,460,270);
        panel.add(scrollPane);
    }

    /**
     * 导出文件格式组件
     * @param panel 父级面板
     */
    public void exportFormat(JPanel panel){
        JLabel label=new JLabel("导出格式:");
        label.setBounds(10,325,60,25);
        exportFormatList=new JComboBox();
        exportFormatList.addItem("txt");
        exportFormatList.addItem("word");
        exportFormatList.setBounds(80,325,390,25);

        panel.add(label);
        panel.add(exportFormatList);
    }

    /**
     * 导出组件
     * @param panel 父级面板
     */
    public void exportBtn(JPanel panel){
        export=new JButton("导出");
        export.setBounds(400,360,70,25);
        panel.add(export);
    }
}
