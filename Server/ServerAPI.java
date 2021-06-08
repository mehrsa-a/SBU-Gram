package Server;

import Common.Post;
import Common.Requests;
import Common.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerAPI {

    public static Map<String, Object> login(Map<String, Object> received){
        String username=(String) received.get("username");
        String password=(String) received.get("password");
        Boolean isNullUser=(Server.users.get(username)!=null);
        Map<String,Object> ans = new HashMap<>();
        ans.put("command", Requests.login);
        ans.put("exists", isNullUser);
        if(!isNullUser){
            return ans;
        }
        User user=Server.users.get(username).checkTruePass(username, password);
        ans.put("answer", user);
        return ans;
    }

    //its for sign up time
    public static Map<String, Object> isUsernameValid(Map<String, Object> received){
        String username=(String) received.get("username");
        User user=Server.users.get(username);
        Boolean exists=(user==null);
        Map<String,Object> ans=new HashMap<>();
        ans.put("answer", exists);
        ans.put("request", Requests.newUsername);
        return ans;
    }

    public static Map<String,Object> signUp(Map<String,Object> income){
        User newUser=(User) income.get("user");
        String username=newUser.getUsername();
        Server.users.put(username, newUser);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.signup);
        ans.put("answer", newUser);
        return ans;
    }

    public static Map<String,Object> getPosts(Map<String,Object> income){
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getPosts);
        List<Post> sent = new ArrayList<>(Server.posts);
        ans.put("posts", sent);
        return ans;
    }

    public static Map<String,Object> getMyPosts(Map<String,Object> income){
        User user = (User) income.get("user");
        String username = user.getUsername();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getMyPosts);
        List<Post> sent = Server
                .posts
                .stream()
                .filter(a-> a.getUser().getUsername().equals(username))
                .collect (Collectors.toList());
        ans.put("myPosts", sent);
        return ans;
    }

    public static Map<String,Object> addPost(Map<String,Object> income){

        Post post = (Post) income.get("post");
        Server.posts.add(post);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.addPost);
        ans.put("answer", new Boolean(true));
        return ans;
    }
}
