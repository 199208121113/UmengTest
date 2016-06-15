package umeng.x.con.model;

/**
 * Created by root on 16-6-14.
 */
public class FileInfo {
    private String fileName;
    private String filePath;
    private boolean isCheck;

    public FileInfo() {
    }

    public FileInfo(String fileName, String filePath,boolean isCheck) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
