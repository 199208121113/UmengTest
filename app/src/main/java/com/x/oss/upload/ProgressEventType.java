package com.x.oss.upload;

/**
 * Created by root on 16-6-23.
 */
public class ProgressEventType {
    private int progress;
    private String filePath;

    public ProgressEventType(int progress, String filePath) {
        this.progress = progress;
        this.filePath = filePath;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
