package Server;

import Common.*;

import java.util.*;
import java.util.stream.Collectors;

public class ServerAPI {

    private static Comparator<Post> timeCompare = (a, b) -> -1 * Long.compare(a.getCreatedTime(), b.getCreatedTime());
    
    public static Map<String, Object> login(Map<String, Object> received){
        String username=(String) received.get("username");
        String password=(String) received.get("password");
        Boolean isNullUser=(Server.users.get(username)!=null);
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.login);
        ans.put("exists", isNullUser);
        if(!isNullUser){
            return ans;
        }
        User user=Server.users.get(username).checkTruePass(username, password);
        ans.put("answer", user);
        if(user!=null){
            System.out.println(username+" login");
            System.out.println("time: "+ Time.getTime());
        }
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
        System.out.println(username+" register ");
        System.out.println("time: "+ Time.getTime());
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
        List<Post> sent= Server.users.get(username).getPosts();
        ans.put("myPosts", sent);
        return ans;
    }

    public static Map<String,Object> addPost(Map<String,Object> income){
        Post post = (Post) income.get("post");
        byte[] image= (byte[]) income.get("image");
        Server.posts.add(post);
        if(image!=null){
            for(Post p: Server.posts){
                if(p.equals(post)){
                    p.setImage(image);
                }
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        List<Post> sent = new ArrayList<>(Server.posts);
        ans.put("request", Requests.addPost);
        //ans.put("post", sent);
        ans.put("answer", new Boolean(true));
        return ans;
    }

    public static Map<String,Object> getUsers(Map<String,Object> income){
        User user = (User) income.get("user");
        String username = user.getUsername();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getUsers);
        List<User> temp= Server
                .users
                .values()
                .stream()
                .filter(a -> !a.getUsername().equals(username))
                .collect(Collectors.toList());
        List<String> help= Server
                .users
                .keySet()
                .stream()
                .filter(a -> !a.equals(username))
                .collect(Collectors.toList());
        Map<String, User> sent = new HashMap<>();
        int i=0;
        for(String s: help){
            sent.put(s, temp.get(i));
            i++;
        }
        ans.put("users", sent);
        return ans;
    }

    public static Map<String, Object> follow(Map<String,Object> income){
        User user= (User) income.get("user");
        User followed= (User) income.get("followed");
        Server.users.get(user.getUsername()).getFollowing().add(followed);
        Server.users.get(followed.getUsername()).getFollower().add(user);
        Database.getInstance().updateDataBase();
        String answer=Server.users.get(user.getUsername()).getFollowing().size()+"|"+Server.users.get(followed.getUsername()).getFollower().size();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.follow);
        ans.put("answer", answer);
        System.out.println(user.getUsername()+" follow");
        System.out.println("message: "+followed.getUsername());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    public static Map<String, Object> getNumbers(Map<String, Object> income){
        User user= (User) income.get("user");
        String answer=Server.users.get(user.getUsername()).getFollowing().size()+"|"+Server.users.get(user.getUsername()).getFollower().size()+"|"+Server.users.get(user.getUsername()).getPosts().size();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getNumbers);
        ans.put("answer", answer);
        return ans;
    }

    public static Map<String, Object> getFollowers(Map<String, Object> income){
        User user= (User) income.get("user");
        List<User> list=Server.users.get(user.getUsername()).getFollower();
        List<String> usernames=new ArrayList<>();
        for(User u: list){
            usernames.add(u.getUsername());
        }
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getFollowers);
        ans.put("answer", usernames);
        return ans;
    }

    public static Map<String, Object> unfollow(Map<String,Object> income){
        User user= (User) income.get("user");
        User unfollowed= (User) income.get("unfollowed");
        Server.users.get(user.getUsername()).getFollowing().removeIf(u -> u.getUsername().equals(unfollowed.getUsername()));
        Server.users.get(unfollowed.getUsername()).getFollower().removeIf(u -> u.getUsername().equals(user.getUsername()));
        Database.getInstance().updateDataBase();
        String answer=Server.users.get(user.getUsername()).getFollowing().size()
                +"|"+Server.users.get(unfollowed.getUsername()).getFollower().size();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.unfollow);
        ans.put("answer", answer);
        System.out.println(user.getUsername()+" unfollow");
        System.out.println("message: "+unfollowed.getUsername());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    public static Map<String, Object> like(Map<String, Object> income){
        User user= (User) income.get("user");
        Post liked= (Post) income.get("liked");
        int answer=0;
        for(Post p: Server.posts){
            if(p.equals(liked)){
                p.getLiked().add(user);
                answer=p.getLiked().size();
            }
        }
        for(Post p: Server.users.get(liked.getUser().getUsername()).getPosts()){
            if(p.equals(liked)){
                p.getLiked().add(user);
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.like);
        ans.put("answer", answer);
        System.out.println(user.getUsername()+" like");
        System.out.println("message: "+liked.getUser().getUsername()+" "+liked.getTitle());
        System.out.println("time: "+ Time.getTime());
        return ans;
    }

    public static Map<String, Object> dislike(Map<String, Object> income){
        User user= (User) income.get("user");
        Post disliked= (Post) income.get("disliked");
        int answer=0;
        for(Post p: Server.posts){
            if(p.equals(disliked)){
                p.getLiked().remove(user);
                answer=p.getLiked().size();
            }
        }
        for(Post p: Server.users.get(disliked.getUser().getUsername()).getPosts()){
            if(p.equals(disliked)){
                p.getLiked().remove(user);
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.dislike);
        ans.put("answer", answer);
        return ans;
    }

    public static Map<String, Object> getPostFeatures(Map<String, Object> income){
        Post post= (Post) income.get("post");
        for(Post p: Server.posts){
            if(p.equals(post)){
                post=p;
            }
        }
        String answer=post.getLiked().size()+"|"+post.getReposted().size()+"|"+post.getCommented().size();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getPostFeatures);
        ans.put("answer", answer);
        return ans;
    }

    public static Map<String, Object> getLikes(Map<String, Object> income){
        Post post= (Post) income.get("post");
        for(Post p: Server.posts){
            if(p.equals(post)){
                post=p;
            }
        }
        List<User> list=post.getLiked();
        List<String> usernames=new ArrayList<>();
        for(User u: list){
            usernames.add(u.getUsername());
        }
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getLikes);
        ans.put("answer", usernames);
        return ans;
    }

    public static Map<String, Object> repost(Map<String, Object> income){
        User user= (User) income.get("user");
        Post post= (Post) income.get("repost");
        int answer=0;
        for(Post p: Server.posts){
            if(p.equals(post)){
                p.getReposted().add(user);
                answer=p.getReposted().size();
            }
        }
        for(Post p: Server.users.get(post.getUser().getUsername()).getPosts()){
            if(p.equals(post)){
                p.getReposted().add(user);
            }
        }
        Server.users.get(user.getUsername()).getPosts().add(post);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.repost);
        ans.put("answer", answer);
        System.out.println(user.getUsername()+" repost");
        System.out.println("message: "+post.getUser().getUsername()+" "+post.getTitle());
        System.out.println("time: "+ Time.getTime());
        return ans;
    }

    public static Map<String, Object> addComment(Map<String, Object> income){
        User user= (User) income.get("user");
        Post commented= (Post) income.get("commented");
        Comment cm= (Comment) income.get("comment");
        List<Comment> send=new ArrayList<>();
        for(Post p: Server.posts){
            if(p.equals(commented)){
                p.getCommented().add(cm);
                send=p.getCommented();
            }
        }
        for(Post p: Server.users.get(commented.getUser().getUsername()).getPosts()){
            if(p.equals(commented)){
                p.getCommented().add(cm);
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.addComment);
        ans.put("comments", send);
        System.out.println(user.getUsername()+" comment");
        System.out.println("message: "+commented.getTitle());
        System.out.println("time: "+ Time.getTime());
        return ans;
    }

    public static Map<String, Object> getComments(Map<String, Object> income){
        Post post= (Post) income.get("post");
        for(Post p: Server.posts){
            if(p.equals(post)){
                post=p;
            }
        }
        List<Comment> list=post.getCommented();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getComments);
        ans.put("answer", list);
        return ans;
    }

    public static Map<String, Object> setProfile(Map<String, Object> income){
        User user= (User) income.get("user");
        byte[] image= (byte[]) income.get("image");
        Server.users.get(user.getUsername()).setImage(image);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.setProfile);
        ans.put("answer", image);
        return ans;
    }

    public static Map<String, Object> getProfile(Map<String, Object> income){
        User user= (User) income.get("user");
        byte[] image=Server.users.get(user.getUsername()).getImage();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getProfile);
        ans.put("answer", image);
        return ans;
    }

    public static Map<String, Object> addInformation(Map<String, Object> income){
        User user= (User) income.get("user");
        Map<String, String> info= (Map<String, String>) income.get("info");
        if(info.get("firstName")!=null)
            Server.users.get(user.getUsername()).setFirstName(info.get("firstName"));
        if(info.get("lastName")!=null)
            Server.users.get(user.getUsername()).setLastName(info.get("lastName"));
        if(info.get("phoneNumber")!=null)
            Server.users.get(user.getUsername()).setPhoneNumber(info.get("phoneNumber"));
        if(info.get("email")!=null)
            Server.users.get(user.getUsername()).setEmail(info.get("email"));
        if(info.get("location")!=null)
            Server.users.get(user.getUsername()).setLocation(info.get("location"));
        if(info.get("birthday")!=null)
            Server.users.get(user.getUsername()).setBirthday(info.get("birthday"));
        if(info.get("gender")!=null)
            Server.users.get(user.getUsername()).setGender(info.get("gender"));
        if(info.get("bio")!=null)
            Server.users.get(user.getUsername()).setBio(info.get("bio"));
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.setInformation);
        ans.put("answer", new Boolean(true));
        return ans;
    }

    public static Map<String, Object> getInformation(Map<String, Object> income){
        User user= (User) income.get("user");
        Map<String, String> info=new HashMap<>();
        if(Server.users.get(user.getUsername()).getFirstName()!=null)
            info.put("firstName", Server.users.get(user.getUsername()).getFirstName());
        if(Server.users.get(user.getUsername()).getLastName()!=null)
            info.put("lastName", Server.users.get(user.getUsername()).getLastName());
        if(Server.users.get(user.getUsername()).getPhoneNumber()!=null)
            info.put("phoneNumber", Server.users.get(user.getUsername()).getPhoneNumber());
        if(Server.users.get(user.getUsername()).getEmail()!=null)
            info.put("email", Server.users.get(user.getUsername()).getEmail());
        if(Server.users.get(user.getUsername()).getLocation()!=null)
            info.put("location", Server.users.get(user.getUsername()).getLocation());
        if(Server.users.get(user.getUsername()).getBirthday()!=null)
            info.put("birthday", Server.users.get(user.getUsername()).getBirthday());
        if(Server.users.get(user.getUsername()).getGender()!=null)
            info.put("gender", Server.users.get(user.getUsername()).getGender());
        if(Server.users.get(user.getUsername()).getBio()!=null)
            info.put("bio", Server.users.get(user.getUsername()).getBio());
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getInformation);
        ans.put("answer", info);
        return ans;
    }

    public static Map<String,Object> setForgetPassword(Map<String,Object> income){
        User user=(User) income.get("user");
        Server.users.get(user.getUsername()).setQuestion((String) income.get("question"));
        Server.users.get(user.getUsername()).setAnswer((String) income.get("ans"));
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.setForgetPassword);
        ans.put("answer", new Boolean(true));
        return ans;
    }

    public static Map<String,Object> getForgetPassword(Map<String,Object> income){
        String user=(String) income.get("user");
        String answer=Server.users.get(user).getQuestion()+"|"
                +Server.users.get(user).getAnswer()
                +"|"+Server.users.get(user).getPassword();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getForgetPassword);
        ans.put("answer", answer);
        return ans;
    }

    public static Map<String,Object> changePassword(Map<String,Object> income){
        User user= (User) income.get("user");
        String pass= (String) income.get("password");
        Server.users.get(user.getUsername()).setPassword(pass);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.changePassword);
        ans.put("answer", new Boolean(true));
        return ans;
    }

    public static Map<String,Object> deleteAccount(Map<String,Object> income){
        User user= (User) income.get("user");
        String username=user.getUsername();
        Server.users.remove(user.getUsername());
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.deleteAccount);
        ans.put("answer", new Boolean(true));
        System.out.println(username+"  delete account");
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    public static Map<String,Object> logOut(Map<String,Object> income){
        User user= (User) income.get("user");
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.deleteAccount);
        ans.put("answer", new Boolean(true));
        System.out.println(user.getUsername()+"  logout");
        System.out.println("time: "+Time.getTime());
        return ans;
    }
}
