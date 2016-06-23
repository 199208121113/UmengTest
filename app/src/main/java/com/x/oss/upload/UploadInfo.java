package com.x.oss.upload;

/**
 * Created by root on 16-6-23.
 */
public class UploadInfo {
    private String fileName;
    private String filePath;
    private int progress;

    public UploadInfo() {
    }

    public UploadInfo(String fileName, String filePath, int progress) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.progress = progress;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
