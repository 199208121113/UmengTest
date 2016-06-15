package umeng.x.con.model;

import java.io.Serializable;

/**
 * Created by root on 16-6-13.
 */
public class ChoiceMediaInfo implements Serializable{

    private String videoUrl;
    private String imgUrl;
    private String videoName;
    private boolean isCheck = false;

    public ChoiceMediaInfo() {
    }

    public ChoiceMediaInfo(String videoUrl, String imgUrl, String videoName, boolean isCheck) {
        this.videoUrl = videoUrl;
        this.imgUrl = imgUrl;
        this.videoName = videoName;
        this.isCheck = isCheck;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
