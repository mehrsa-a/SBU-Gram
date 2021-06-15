package Model;

import Common.Post;
import Common.Requests;
import Common.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, Object> getPosts(){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getPosts);
        toSend.put("posts", Main.posts);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return received;
    }

    public static List<Post> getAllPosts(){
        Map<String,Object> all=getPosts();
        return (List<Post>) all.get("posts");
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

    public static Map<String, Object> getUsers(){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getUsers);
        toSend.put("user", Main.currentUser);
        toSend.put("users", Main.users);
        Map<String,Object> received = ConnectClient.serve(toSend);
        return received;
    }

    public static Map<String, User> getAllUsers(){
        Map<String,Object> all=getUsers();
        return (Map<String, User>) all.get("users");
    }

    public static String follow(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.follow);
        toSend.put("user", Main.currentUser);
        toSend.put("followed", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (String) received.get("answer");
    }

    public static String getNumbers(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.getNumbers);
        toSend.put("user", user);
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

    public static String unfollow(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("request", Requests.unfollow);
        toSend.put("user", Main.currentUser);
        toSend.put("unfollowed", user);
        Map<String,Object> received=ConnectClient.serve(toSend);
        if (received.get("answer")==null){
            return null;
        }
        return (String) received.get("answer");
    }
}
