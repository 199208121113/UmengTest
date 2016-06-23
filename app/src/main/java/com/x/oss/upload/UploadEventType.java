package com.x.oss.upload;

import java.util.List;

/**
 * Created by root on 16-6-23.
 */
public class UploadEventType {
    private List<String> filePathList;
    public UploadEventType() {
    }

    public UploadEventType(List<String> filePathList) {
        this.filePathList = filePathList;
    }

    public List<String> getFilePathList() {
        return filePathList;
    }

    public void setFilePathList(List<String> filePathList) {
        this.filePathList = filePathList;
    }
}
