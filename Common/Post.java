package Common;

import Server.Server;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Post implements Serializable, Comparable {
    public static long serialVersionUID=123456L;
    private User user=new User();
    private User publisher;
    private String title="";
    private String text="";
    private byte[] image;
    private int like;
    private int repost;
    private int comment;
    private ArrayList<User> liked=new ArrayList<>();
    private ArrayList<User> reposted=new ArrayList<>();
    private ArrayList<Post> commented=new ArrayList<>();
    private final long createdTime = Time.getMilli();
    private final String timeString = Time.getTime();

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getTimeString() {
        return timeString;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCommented(ArrayList<Post> commented) {
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

    public ArrayList<Post> getCommented() {
        return commented;
    }

    public ArrayList<User> getReposted() {
        return reposted;
    }

    public ArrayList<User> getLiked() {
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
