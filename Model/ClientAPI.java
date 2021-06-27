package Model;

import Common.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h1>ClientAPI</h1>
 * <p>this class sends massage to server and receive its responds</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class ClientAPI {

    /**
     * @param username its check for existing
     * @param password its check for belong to the same user
     * @return the current user
     */
    public static User login(String username, String password){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.login);
        toSend.put("username",username);
        toSend.put("password",password);
        //Main.currentUser=new User(username, password);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (User) received.get("answer");
    }

    /**
     * this method check the validness of username for new account
     * @param username its check for existing
     * @return a boolean that shows is username exist or not
     */
    public static boolean isUsernameValid(String username){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.newUsername);
        toSend.put("username", username);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return (boolean) received.get("answer");
    }

    /**
     * @param user it sends to server to add to users
     * @return the user that made
     */
    public static User signUp(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.signup);
        toSend.put("user", user);
        //Main.currentUser=user;
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (User) received.get("answer");
    }

    /**
     * @param user it show what user wants the posts
     * @return the map that one of it objects is the posts list
     */
    public static Map<String, Object> getPosts(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getPosts);
        toSend.put("posts", Main.posts);
        toSend.put("user", user);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return received;
    }

    /**
     * @param user it show what user wants the posts
     * @return the list of all posts
     */
    public static List<Post> getAllPosts(User user){
        Map<String,Object> all=getPosts(user);
        return (List<Post>) all.get("posts");
    }

    /**
     * @param user it show what user wants the posts
     * @return the list of user's timeline's posts
     */
    public static List<Post> getTimeline(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getTimeline);
        toSend.put("posts", Main.posts);
        toSend.put("user", user);
        Map<String,Object> received = ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<Post>) received.get("answer");
    }

    /**
     * @param user its the user that we want its posts
     * @return the map that one of its objects is user's posts
     */
    public static List<Post> getMyPosts(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getMyPosts);
        toSend.put("user", user);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return (List<Post>) received.get("myPosts");
    }

    /**
     * @param user its the user that we want its posts
     * @return the user's posts only
     */
    public static List<Post> getAllOfMyPosts(User user){
        List<Post> all=getMyPosts(user);
        return (List<Post>) all;
    }

    /**
     * it sends server a post to add to all posts
     * @param post the post that wants to add
     */
    public static void addPost(Post post){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.addPost);
        toSend.put("post", post);
        ConnectClient.serve(toSend);
    }

    /**
     * it sends server a post that has photo to add to all posts
     * @param post the post that wants to add
     * @param bytes its the post's attached photo
     * @param path the absolute path of the attached photo
     */
    public static void addPost(Post post, byte[] bytes, String path){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.addPost);
        toSend.put("post", post);
        toSend.put("image", bytes);
        toSend.put("path", path);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * @param user its the current user that shouldn't be in user's list that we can follow
     * @return a map that one of its objects is all users except the current user
     */
    public static Map<String, Object> getUsers(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getUsers);
        toSend.put("user", user);
        toSend.put("users", Main.users);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return received;
    }

    /**
     * @param user its the current user that shouldn't be in user's list that we can follow
     * @return all users except the current user
     */
    public static Map<String, User> getAllUsers(User user){
        Map<String,Object> all=getUsers(user);
        return (Map<String, User>) all.get("users");
    }

    /**
     * it sends a message to server to add someone to another one's followers and followings
     * @param cUser its the user that wants to follow someone
     * @param user the user that wants to be followed
     * @return a string that has number of followers and followings and posts of user
     */
    public static String follow(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.follow);
        toSend.put("user", cUser);
        toSend.put("followed", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (String) received.get("answer");
    }

    /**
     * a method that gives shown information of users
     * @param user its the user that client wants its information
     * @return a string that has number of followers and followings and posts of user
     */
    public static String getNum(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getNum);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (String) received.get("answer");
    }

    /**
     * this method helps server to repost in console too
     * @param cUser its the user that wants the another user's info
     * @param user its the user that client wants its information
     * @return a string that has number of followers and followings and posts of user
     */
    public static String getNumbers(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getNumbers);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (String) received.get("answer");
    }

    /**
     * @param user its the user that client wants its info
     * @return the list of user's followers
     */
    public static List<String> getFollowers(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getFollowers);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<String>) received.get("answer");
    }

    /**
     * @param user its the user that client wants its info
     * @return the list of user's followings
     */
    public static List<String> getFollowings(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getFollowing);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<String>) received.get("answer");
    }

    /**
     * it sends a message to server to remove someone from another one's followers and followings
     * @param cUser its the user that wants to unfollow someone
     * @param user the user that wants to be unfollowed
     * @return a string that has number of followers and followings and posts of user
     */
    public static String unfollow(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.unfollow);
        toSend.put("user", cUser);
        toSend.put("unfollowed", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (String) received.get("answer");
    }

    /**
     * it sends a message to server to add user to a post's likes
     * @param user its the user that wants to like a post
     * @param post the post that wants to be liked
     * @return a int that shows posts likes number
     */
    public static Integer like(User user, Post post){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.like);
        toSend.put("user", user);
        toSend.put("liked", post);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (Integer) received.get("answer");
    }

    /**
     * it sends a message to server to remove user from a post's likes
     * @param user its the user that wants to take its like from a post back
     * @param post the post that wants to take its like's back
     * @return a int that shows posts likes number
     */
    public static Integer dislike(User user, Post post){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.dislike);
        toSend.put("user", user);
        toSend.put("disliked", post);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (Integer) received.get("answer");
    }

    /**
     * a method that gives shown information of posts
     * @param post its the post that client wants its information
     * @return a string that shows post's likes and reposts and comments numbers
     */
    public static String getPostFeatures(Post post){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getPostFeatures);
        toSend.put("post", post);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (String) received.get("answer");
    }

    /**
     * @param post its the post that client wants its info
     * @return the list of post's likes
     */
    public static List<String> getLikes(Post post){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getLikes);
        toSend.put("post", post);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<String>) received.get("answer");
    }

    /**
     * it sends a message to server to add user to a post's reposts
     * @param user its the user that wants to repost a post
     * @param post the post that wants to be reposted
     * @return a int that shows posts reposts number
     */
    public static Integer repost(User user, Post post){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.repost);
        toSend.put("user", user);
        toSend.put("repost", post);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (Integer) received.get("answer");
    }

    /**
     * @param post its the post that client wants its info
     * @return the list of post's reposts
     */
    public static List<String> getReposts(Post post){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getReposts);
        toSend.put("post", post);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<String>) received.get("answer");
    }

    /**
     * it sends a message to server to add a comment to a post's comments
     * @param user its the user that wants to adda comment for a post
     * @param post the post that wants add a comment's to
     * @param comment the comment that wants to add
     * @return the list of post's comments
     */
    public static List<Comment> addComment(User user, Post post, Comment comment){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.addComment);
        toSend.put("user", user);
        toSend.put("commented", post);
        toSend.put("comment", comment);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("comments")==null){
            return null;
        }
        return (List<Comment>) received.get("comments");
    }

    /**
     * @param post its the post that client wants its info
     * @return the list of post's comments
     */
    public static List<Comment> getComments(Post post){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getComments);
        toSend.put("post", post);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<Comment>) received.get("answer");
    }

    /**
     * it adds a photo for user's profile
     * @param user its the user that wants to set profile photo for itself
     * @param bytes its the profile photo
     * @param path its the absolute path of profile photo
     * @return the profile photo
     */
    public static byte[] setProfile(User user, byte[] bytes, String path){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.setProfile);
        toSend.put("user", user);
        toSend.put("image", bytes);
        toSend.put("path", path);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (byte[]) received.get("answer");
    }

    /**
     * it changes the user's profile photo
     * @param user its the user that wants to change its profile photo
     * @param bytes its the profile photo
     * @param path its the absolute path of new profile photo
     * @return the profile photo
     */
    public static byte[] changeProfile(User user, byte[] bytes, String path){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.changeProfile);
        toSend.put("user", user);
        toSend.put("image", bytes);
        toSend.put("path", path);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (byte[]) received.get("answer");
    }

    /**
     * @param user its the user that client wants its info
     * @return the user's profile photo
     */
    public static byte[] getProfile(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getProfile);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (byte[]) received.get("answer");
    }

    /**
     * this method set some information for user
     * @param user its the user that wants to set some personal information for its account
     * @param strings its the information that users add
     */
    public static void addInformation(User user, Map<String, String> strings){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.setInformation);
        toSend.put("user", user);
        toSend.put("info", strings);
        ConnectClient.serve(toSend);
    }

    /**
     * @param user its the user that client wants its info
     * @return a map that contain user's personal information
     */
    public static Map<String, String> getInformation(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getInformation);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (Map<String, String>) received.get("answer");
    }

    /**
     * @param user its the user that wants to set password recovery
     * @param question its the question that user choose and answer
     * @param answer its the user's answer
     */
    public static void setForgetPassword(User user, String question, String answer){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.setForgetPassword);
        toSend.put("user", user);
        toSend.put("question", question);
        toSend.put("ans", answer);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * this method helps user to recovery its password
     * @param user its the user that wants to recovery its password
     * @return its user's question and answer
     */
    public static String getForgetPassword(String user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getForgetPassword);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (String) received.get("answer");
    }

    /**
     * this method changes the user's password
     * @param user its the user that wants to change its password
     * @param password its the new password
     */
    public static void changePassword(User user, String password){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.changePassword);
        toSend.put("user", user);
        toSend.put("password", password);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * this method delete user's account
     * @param user its the user that wants to delete its account
     */
    public static void deleteAccount(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.deleteAccount);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * this method helps server to reports someone's logs out
     * @param user its the user that want to log out of its account
     */
    public static void logOut(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.logout);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * it sends a message to server to add someone to another one's blockers and blockeds
     * @param cUser its the user that wants to block someone
     * @param user its the user that wants to be blocked
     */
    public static void block(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.block);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * it sends a message to server to remove someone from another one's blockers and blockeds
     * @param cUser its the user that wants to unblock someone
     * @param user its the user that wants to be unblocked
     */
    public static void unblock(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.unblock);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * @param user its the user that client wants its info
     * @return the list of user's blockeds
     */
    public static List<String> getBlocked(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getBlocked);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<String>) received.get("answer");
    }

    /**
     * @param user its the user that client wants its info
     * @return the list of user's blockers
     */
    public static List<String> getBlockers(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getBlocker);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<String>) received.get("answer");
    }

    /**
     * it sends a message to server to add someone to another one's muteds
     * @param cUser its the user that wants to mute someone
     * @param user its the user that wants to be mute
     */
    public static void mute(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.mute);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * it sends a message to server to remove someone from another one's muteds
     * @param cUser its the user that wants to unMute someone
     * @param user its the user that wants to be unMute
     */
    public static void unMute(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.unMute);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    /**
     * @param user its the user that client wants its info
     * @return the list of user's muteds
     */
    public static List<String> getMuted(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getMuted);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<String>) received.get("answer");
    }

    /**
     * @param cUser its the user that wants to send another user message
     * @param user its the target user
     * @param massage its the message that wants to sent
     */
    public static void sendMassage(User cUser, User user, Massage massage){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.sendMassage);
        toSend.put("sender", cUser);
        toSend.put("receiver", user);
        toSend.put("massage", massage);
        //toSend.put("date", date);
        ConnectClient.serve(toSend);
    }

    /**
     * @param cUser its the user that send a massage to someone
     * @param user its the user that receive a massage
     * @param massage its the target massage
     */
    public static void receiveMassage(User cUser, User user, Massage massage){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.receiveMassage);
        toSend.put("sender", cUser);
        toSend.put("receiver", user);
        toSend.put("massage", massage);
        //toSend.put("date", date);
        ConnectClient.serve(toSend);
    }

    /**
     * @param cUser its the user that wants its shared massages
     * @return a list of massages that the user is in one side of
     */
    public static List<Massage> getMassages(User cUser){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.getMassages);
        toSend.put("sender", cUser);
        Map<String, Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<Massage>) received.get("answer");
    }

    /**
     * this method changes the massage's state to read
     * @param user its the user that read its massages
     * @param massage its the massage that its state changes
     */
    public static void readMassage(User user, Massage massage){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.readMassage);
        toSend.put("user", user);
        toSend.put("massage", massage);
        Map<String, Object> received=ConnectClient.serve(toSend);
    }

    /**
     * @param user its the user that client wants its info
     * @return alist of users that the user chats with
     */
    public static List<String> getMassaged(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getMassaged);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (List<String>) received.get("answer");
    }

    /**
     * this method delete one massage
     * @param user its tne user that wants to delete its massage
     * @param massage the massage that wants to delete
     */
    public static void deleteMassage(User user, Massage massage){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.deleteMassage);
        toSend.put("user", user);
        toSend.put("massage", massage);
        ConnectClient.serve(toSend);
    }

    /**
     *  this method edit one massage
     * @param user its tne user that wants to edit its massage
     * @param massage the massage that wants to edit
     * @param newText the new text of massage
     */
    public static void editMassage(User user, Massage massage, String newText){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.editMassage);
        toSend.put("user", user);
        toSend.put("oldMassage", massage);
        toSend.put("newMassage", newText);
        ConnectClient.serve(toSend);
    }
}
