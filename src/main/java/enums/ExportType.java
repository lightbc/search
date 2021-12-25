package enums;

/**
 * 导出文件格式枚举类型
 */
public enum  ExportType {
    TXT("txt",".txt"),WORD("word",".doc");

    //类型
    private String type;
    //导出文件拓展名
    private String ext;

    ExportType(String type, String ext){
        this.type=type;
        this.ext=ext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
