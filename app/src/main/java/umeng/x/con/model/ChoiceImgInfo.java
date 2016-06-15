package umeng.x.con.model;

/**
 * Created by root on 16-6-13.
 */
public class ChoiceImgInfo {
    private String imgUrl;
    private boolean isCheck = false;

    public ChoiceImgInfo() {
    }

    public ChoiceImgInfo(String imgUrl, boolean isCheck) {
        this.imgUrl = imgUrl;
        this.isCheck = isCheck;
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
