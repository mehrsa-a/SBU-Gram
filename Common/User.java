package Common;

import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <h1>User</h1>
 * <p>this class handles users</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class User implements Serializable, Comparable {
    public static long serialVersionUID=123457890L;
    private String username="";
    private String password;
    private byte[] image;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String Email;
    private String locationField;
    private String birthday;
    private String gender;
    private String bio;
    private Set<Post> posts=new ConcurrentSkipListSet<>();
    private List<User> follower=new CopyOnWriteArrayList<>();
    private List<User> following=new CopyOnWriteArrayList<>();
    private final long createdTime = Time.getMilli();
    private final String timeString = Time.getTime();
    private String question;
    private String answer;
    private String profilePath;
    private List<User> blocked=new CopyOnWriteArrayList<>();
    private List<User> blocker=new CopyOnWriteArrayList<>();
    private List<User> muted=new CopyOnWriteArrayList<>();
    private transient Set<User> massaged=new ConcurrentSkipListSet<>();

    //constructors
    public User(){

    }

    public User(String username, String password){
        this.username=username;
        this.password=password;
    }

    //setters
    public void setPosts(HashSet<Post> posts) {
        this.posts = posts;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setLocation(String location) {
        this.locationField = location;
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

    public void setMassaged(Set<User> massaged) {
        this.massaged = massaged;
    }

    public void setMuted(List<User> muted) {
        this.muted = muted;
    }

    public void setBlocker(List<User> blocker) {
        this.blocker = blocker;
    }

    public void setBlocked(List<User> blocked) {
        this.blocked = blocked;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    public void setFollower(ArrayList<User> follower) {
        this.follower = follower;
    }

    //getters
    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<User> getFollower() {
        return follower;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLocation() {
        return locationField;
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

    public Set<User> getMassaged() {
        return massaged;
    }

    public List<User> getMuted() {
        return muted;
    }

    public List<User> getBlocker() {
        return blocker;
    }

    public List<User> getBlocked() {
        return blocked;
    }

    public String getBio() {
        return bio;
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
        User other=(User) o;
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
        User other= (User) o;
        return other.getUsername().equals(this.getUsername());
    }

    /**
     * @param username the username of a user
     * @param password the password of that user
     * @return a boolean that show username and password are belong to a same user or not
     */
    public User checkTruePass(String username, String password){
        if(this.username.equals(username)&&this.password.equals(password)){
            return this;
        }
        return null;
    }
}
