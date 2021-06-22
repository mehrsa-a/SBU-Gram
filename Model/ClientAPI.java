package Model;

import Common.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientAPI {

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

    public static boolean isUsernameValid(String username){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.newUsername);
        toSend.put("username", username);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return (boolean) received.get("answer");
    }

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

    public static Map<String, Object> getPosts(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getPosts);
        toSend.put("posts", Main.posts);
        toSend.put("user", user);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return received;
    }

    public static List<Post> getAllPosts(User user){
        Map<String,Object> all=getPosts(user);
        return (List<Post>) all.get("posts");
    }

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

    public static Map<String,Object> getMyPosts(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getMyPosts);
        toSend.put("user", user);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return received;
    }

    public static List<Post> getAllOfMyPosts(User user){
        Map<String,Object> all=getMyPosts(user);
        return (List<Post>) all.get("myPosts");
    }

    public static void addPost(Post post){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.addPost);
        toSend.put("post", post);
        ConnectClient.serve(toSend);
    }

    public static void addPost(Post post, byte[] bytes, String path){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.addPost);
        toSend.put("post", post);
        toSend.put("image", bytes);
        toSend.put("path", path);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    public static Map<String, Object> getUsers(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getUsers);
        toSend.put("user", user);
        toSend.put("users", Main.users);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return received;
    }

    public static Map<String, User> getAllUsers(User user){
        Map<String,Object> all=getUsers(user);
        return (Map<String, User>) all.get("users");
    }

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

    public static byte[] changeProfile(User user, byte[] bytes, String path){
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

    public static void addInformation(User user, Map<String, String> strings){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.setInformation);
        toSend.put("user", user);
        toSend.put("info", strings);
        ConnectClient.serve(toSend);
    }

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

    public static void setForgetPassword(User user, String question, String answer){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.setForgetPassword);
        toSend.put("user", user);
        toSend.put("question", question);
        toSend.put("ans", answer);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

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

    public static void changePassword(User user, String password){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.changePassword);
        toSend.put("user", user);
        toSend.put("password", password);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    public static void deleteAccount(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.deleteAccount);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    public static void logOut(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.logout);
        toSend.put("user", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    public static void block(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.block);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    public static void unblock(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.unblock);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    public static void mute(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.mute);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

    public static void unMute(User cUser, User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.unMute);
        toSend.put("user", user);
        toSend.put("cUser", cUser);
        Map<String,Object> received=ConnectClient.serve(toSend);
    }

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

    public static void sendMassage(User cUser, User user, Massage massage, Massage date){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.sendMassage);
        toSend.put("sender", cUser);
        toSend.put("receiver", user);
        toSend.put("massage", massage);
        toSend.put("date", date);
        ConnectClient.serve(toSend);
    }

    public static void receiveMassage(User cUser, User user, Massage massage, Massage date){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.receiveMassage);
        toSend.put("sender", cUser);
        toSend.put("receiver", user);
        toSend.put("massage", massage);
        toSend.put("date", date);
        ConnectClient.serve(toSend);
    }

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

    public static void readMassage(User user, Massage massage){
        Map<String,Object> toSend = new HashMap<>();
        toSend.put("request", Requests.readMassage);
        toSend.put("user", user);
        toSend.put("massage", massage);
        Map<String, Object> received=ConnectClient.serve(toSend);
    }

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
}
