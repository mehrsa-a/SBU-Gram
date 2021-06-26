package Common;

import java.io.Serializable;

/**
 * <h1>Comment</h1>
 * <p>this class handles comments</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class Comment implements Serializable {
    public static long serialVersionUID=123456789L;
    private User user=new User();
    private String title="";
    private String text="";
    private final long createdTime = Time.getMilli();
    private final String timeString = Time.getTime();

    //setters
    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //getters
    public String getTimeString() {
        return timeString;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }
}
