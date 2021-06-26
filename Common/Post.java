package Common;

import Server.Server;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * <h1>Post</h1>
 * <p>this class handles posts</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class Post implements Serializable, Comparable {
    public static long serialVersionUID=123456L;
    private User user=new User();
    private List<User> publisher=new CopyOnWriteArrayList<>();
    private String title="";
    private String text="";
    private byte[] image;
    private int like;
    private int repost;
    private int comment;
    private List<User> liked=new CopyOnWriteArrayList<>();
    private List<User> reposted=new CopyOnWriteArrayList<>();
    private List<Comment> commented=new CopyOnWriteArrayList<>();
    private final long createdTime = Time.getMilli();
    private final String timeString = Time.getTime();

    //setters
    public void setPublisher(List<User> publisher) {
        this.publisher = publisher;
    }

    public void setCommented(ArrayList<Comment> commented) {
        this.commented = commented;
    }

    public void setReposted(ArrayList<User> reposted) {
        this.reposted = reposted;
    }

    public void setLiked(ArrayList<User> liked) {
        this.liked = liked;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public void setRepost(int repost) {
        this.repost = repost;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

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
    public List<Comment> getCommented() {
        return commented;
    }

    public List<User> getReposted() {
        return reposted;
    }

    public List<User> getLiked() {
        return liked;
    }

    public int getComment() {
        return comment;
    }

    public int getRepost() {
        return repost;
    }

    public int getLike() {
        return like;
    }

    public byte[] getImage() {
        return image;
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

    public List<User> getPublisher() {
        return publisher;
    }

    public String getTimeString() {
        return timeString;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    /**
     * @param o the object that this compares with
     * @return 1 or -1 to sort
     */
    @Override
    public int compareTo(Object o) {
        Post other = (Post) o;
        if(this.createdTime<other.createdTime){
            return 1;
        }
        else if(this.createdTime>other.createdTime){
            return -1;
        }
        return 1;
    }

    /**
     * @param o the object that this compares with
     * @return a boolean that show two objects are equal or not
     */
    @Override
    public boolean equals(Object o){
        Post other= (Post) o;
        if(other.getUser().getUsername().equals(this.getUser().getUsername())){
            if(other.getTitle().equals(this.getTitle())&&other.getText().equals(this.getText())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
