package Common;

import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class User implements Serializable, Comparable {
    public static long serialVersionUID=123457L;
    private String username="";
    private String password;
    private byte[] image;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String Email;
    private String location;
    private String birthday;
    private String gender;
    private ArrayList<Post> posts=new ArrayList<>();
    private ArrayList<User> follower=new ArrayList<>();
    private ArrayList<User> following=new ArrayList<>();
    private final long createdTime = Time.getMilli();
    private final String timeString = Time.getTime();

    public String getTimeString() {
        return timeString;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public User(){

    }

    public User(String username, String password){
        this.username=username;
        this.password=password;
    }

    public User checkTruePass(String username, String password){
        if(this.username.equals(username)&&this.password.equals(password)){
            return this;
        }
        return null;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    public ArrayList<User> getFollower() {
        return follower;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    public void setFollower(ArrayList<User> follower) {
        this.follower = follower;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(Object o) {
        User other=(User) o;
        if(this.createdTime<other.createdTime){
            return 1;
        }
        else if(this.createdTime>other.createdTime){
            return -1;
        }
        return 1;
    }
}
