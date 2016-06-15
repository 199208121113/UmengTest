package umeng.x.con.model;

/**
 * Created by root on 16-6-14.
 */
public class ChoiceAudioInfo {
    private String title;
    private String audioUrl;
    private long duration;
    private boolean isCheck;

    public ChoiceAudioInfo() {
    }

    public ChoiceAudioInfo(String title, String audioUrl, long duration, boolean isCheck) {
        this.title = title;
        this.audioUrl = audioUrl;
        this.duration = duration;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
