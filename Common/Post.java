package Common;

import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class Post implements Serializable {
    private User user;
    private String title;
    private String text;
    private ImageView image;
    private int like;
    private int repost;
    private int comment;
    private ArrayList<User> liked=new ArrayList<>();
    private ArrayList<User> reposted=new ArrayList<>();
    private ArrayList<User> commented=new ArrayList<>();

    public void setCommented(ArrayList<User> commented) {
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

    public void setImage(ImageView image) {
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

    public ArrayList<User> getCommented() {
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

    public ImageView getImage() {
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
}
