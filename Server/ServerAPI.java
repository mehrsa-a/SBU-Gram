package Server;

import Common.*;
import Model.ClientAPI;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static Model.Main.currentUser;
import static Model.Main.users;

/**
 * <h1>ServerAPI</h1>
 * <p>this class receive massage from client and send respond to</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class ServerAPI {

    /**
     * this method check if the username's exist and its password is correct and let an existing user to log in
     * @param received its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method check if the username's don't exist for sign up with that
     * @param received its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> isUsernameValid(Map<String, Object> received){
        String username=(String) received.get("username");
        User user=Server.users.get(username);
        Boolean exists=(user==null);
        Map<String,Object> ans=new HashMap<>();
        ans.put("answer", exists);
        ans.put("request", Requests.newUsername);
        return ans;
    }

    /**
     * this method add the new user that signed up to all users
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method send all posts to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> getPosts(Map<String,Object> income){
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getPosts);
        List<Post> sent = new ArrayList<>(Server.posts);
        ans.put("posts", sent);
        User user= (User) income.get("user");
        return ans;
    }

    /**
     * this method send the posts of user following and the user, itself to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> getTimeLine(Map<String,Object> income){
        User user= (User) income.get("user");
        List<Post> set=new ArrayList<>();
        set.addAll(Server.users.get(user.getUsername()).getPosts());
        for(User u: Server.users.get(user.getUsername()).getFollowing()){
            set.addAll(Server.users.get(u.getUsername()).getPosts());
        }
        set.removeIf(a-> Server.users.get(user.getUsername()).getMuted().contains(a.getUser()));
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getTimeline);
        ans.put("answer", set);
        System.out.println(user.getUsername()+"  get posts list");
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method send all of a special user posts to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> getMyPosts(Map<String,Object> income){
        User user = (User) income.get("user");
        String username = user.getUsername();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getMyPosts);
        ArrayList<Post> sent= new ArrayList<>(Server.users.get(username).getPosts());
        ans.put("myPosts", sent);
        return ans;
    }

    /**
     * this method add new post to all posts
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> addPost(Map<String,Object> income){
        Post post = (Post) income.get("post");
        byte[] image= (byte[]) income.get("image");
        String path= (String) income.get("path");
        Server.posts.add(post);
        Server.users.get(post.getUser().getUsername()).getPosts().add(post);
        if(image!=null){
            for(Post p: Server.posts){
                if(p.equals(post)){
                    p.setImage(image);
                }
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.addPost);
        ans.put("answer", new Boolean(true));
        System.out.println(post.getUser().getUsername()+" publish");
        if(image!=null){
            System.out.println("message: "+post.getTitle()+" "+path+" "+post.getUser().getUsername());
        }
        else{
            System.out.println("message: "+post.getTitle()+" "+post.getUser().getUsername());
        }
        System.out.println("time: "+ Time.getTime());
        return ans;
    }

    /**
     * this method send all users to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method add the target user to user's following and add the user to its followers too
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method send a user features to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getNum(Map<String, Object> income){
        User user= (User) income.get("user");
        String answer=Server.users.get(user.getUsername()).getFollowing().size()+"|"+Server.users.get(user.getUsername()).getFollower().size()+"|"+Server.users.get(user.getUsername()).getPosts().size();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getNum);
        ans.put("answer", answer);
        return ans;
    }

    /**
     * this method send a user features to the client and it reports this too
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getNumbers(Map<String, Object> income){
        User user= (User) income.get("user");
        User cUser= (User) income.get("cUser");
        String answer=Server.users.get(user.getUsername()).getFollowing().size()+"|"+Server.users.get(user.getUsername()).getFollower().size()+"|"+Server.users.get(user.getUsername()).getPosts().size();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getNumbers);
        ans.put("answer", answer);
        System.out.println(cUser.getUsername()+" get info "+user.getUsername());
        System.out.println("message: "+user.getUsername()+" "+user.getProfilePath());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method send all user's followers to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method send all user's followings to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getFollowings(Map<String, Object> income){
        User user= (User) income.get("user");
        List<User> list=Server.users.get(user.getUsername()).getFollowing();
        List<String> usernames=new ArrayList<>();
        for(User u: list){
            usernames.add(u.getUsername());
        }
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getFollowing);
        ans.put("answer", usernames);
        return ans;
    }

    /**
     * this method remove the target user from user's following and remove the user from its followers too
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method add the user to the target post's likes
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> like(Map<String, Object> income){
        User user= (User) income.get("user");
        Post liked= (Post) income.get("liked");
        int answer=0;
        /*for(Post p: Server.posts){
            if(p.equals(liked)){
                p.getLiked().add(user);
                answer=p.getLiked().size();
            }
        }*/
        for(Post p: Server.users.get(liked.getUser().getUsername()).getPosts()){
            if(p.equals(liked)){
                p.getLiked().add(user);
                answer=p.getLiked().size();
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

    /**
     * this method remove the user from the target post's likes
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> dislike(Map<String, Object> income){
        User user= (User) income.get("user");
        Post disliked= (Post) income.get("disliked");
        int answer=0;
        /*for(Post p: Server.posts){
            if(p.equals(disliked)){
                p.getLiked().remove(user);
                answer=p.getLiked().size();
            }
        }*/
        for(Post p: Server.users.get(disliked.getUser().getUsername()).getPosts()){
            if(p.equals(disliked)){
                p.getLiked().remove(user);
                answer=p.getLiked().size();
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.dislike);
        ans.put("answer", answer);
        return ans;
    }

    /**
     * this method send a post features to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method send all post's likes to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method add the user to the target post's reposts and add the target post to the user's posts
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> repost(Map<String, Object> income){
        User user= (User) income.get("user");
        Post post= (Post) income.get("repost");
        int answer=0;
        /*for(Post p: Server.posts){
            if(p.equals(post)){
                p.getReposted().add(user);
                answer=p.getReposted().size();
            }
        }*/
        for(Post p: Server.users.get(post.getUser().getUsername()).getPosts()){
            if(p.equals(post)){
                p.getReposted().add(user);
                Server.users.get(user.getUsername()).getPosts().add(p);
                answer=p.getReposted().size();
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.repost);
        ans.put("answer", answer);
        System.out.println(user.getUsername()+" repost");
        System.out.println("message: "+post.getUser().getUsername()+" "+post.getTitle());
        System.out.println("time: "+ Time.getTime());
        return ans;
    }

    /**
     * this method send all post's reposts to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getReposts(Map<String, Object> income){
        Post post= (Post) income.get("post");
        for(Post p: Server.posts){
            if(p.equals(post)){
                post=p;
            }
        }
        List<User> list=post.getReposted();
        List<String> usernames=new ArrayList<>();
        for(User u: list){
            usernames.add(u.getUsername());
        }
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getReposts);
        ans.put("answer", usernames);
        return ans;
    }

    /**
     * this method add new comment to all target post's comments
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> addComment(Map<String, Object> income){
        User user= (User) income.get("user");
        Post commented= (Post) income.get("commented");
        Comment cm= (Comment) income.get("comment");
        List<Comment> send=new ArrayList<>();
        /*for(Post p: Server.posts){
            if(p.equals(commented)){
                p.getCommented().add(cm);
            }
        }*/
        for(Post p: Server.users.get(commented.getUser().getUsername()).getPosts()){
            if(p.equals(commented)){
                p.getCommented().add(cm);
                send=p.getCommented();
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

    /**
     * this method send all post's comments to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method set a profile photo for the user
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> setProfile(Map<String, Object> income){
        User user= (User) income.get("user");
        byte[] image= (byte[]) income.get("image");
        String path= (String) income.get("path");
        Server.users.get(user.getUsername()).setImage(image);
        Server.users.get(user.getUsername()).setProfilePath(path);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.setProfile);
        ans.put("answer", image);
        System.out.println(user.getUsername()+" register "+path);
        System.out.println("time: "+ Time.getTime());
        return ans;
    }

    /**
     * this method set a new profile photo for the user
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> changeProfile(Map<String, Object> income){
        User user= (User) income.get("user");
        byte[] image= (byte[]) income.get("image");
        String path= (String) income.get("path");
        Server.users.get(user.getUsername()).setImage(image);
        Server.users.get(user.getUsername()).setProfilePath(path);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.changeProfile);
        ans.put("answer", image);
        System.out.println(user.getUsername()+" update info");
        System.out.println("message: "+path);
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method send the user's profile photo to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getProfile(Map<String, Object> income){
        User user= (User) income.get("user");
        byte[] image=Server.users.get(user.getUsername()).getImage();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getProfile);
        ans.put("answer", image);
        return ans;
    }

    /**
     * this method set the personal information that user add for itself
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method send the user's personal information to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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
        if(Server.users.get(user.getUsername()).getProfilePath()!=null)
            info.put("path", Server.users.get(user.getUsername()).getProfilePath());
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getInformation);
        ans.put("answer", info);
        return ans;
    }

    /**
     * this method save a question and it answer for password recovering
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method send the question and it answer for password recovering
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method change the user's password
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
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

    /**
     * this method delete the user from all users and clear all it's data
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> deleteAccount(Map<String,Object> income){
        User user= (User) income.get("user");
        String username=user.getUsername();
        Server.users.get(user.getUsername()).getPosts().clear();
        for(Post p: Server.posts){
            p.getLiked().removeIf(a-> (a.getUsername().equals(username)));
            p.getReposted().removeIf(a-> (a.getUsername().equals(username)));
            p.getCommented().removeIf(a-> (a.getUser().getUsername().equals(username)));
        }
        for(User u: Server.users.values()){
            u.getFollowing().removeIf(a-> (a.getUsername().equals(username)));
            u.getFollower().removeIf(a-> (a.getUsername().equals(username)));
            //u.getMassaged().removeIf(a-> a.getUsername().equals(username));
            u.getPosts().removeIf(a-> (a.getUser().getUsername().equals(username)));
            for(Post p: u.getPosts()){
                p.getLiked().removeIf(a-> (a.getUsername().equals(username)));
                p.getReposted().removeIf(a-> (a.getUsername().equals(username)));
                p.getCommented().removeIf(a-> (a.getUser().getUsername().equals(username)));
            }
        }
        Server.posts.removeIf(p -> p.getUser().getUsername().equals(username));
        /*Server.massages.removeIf(m -> m.getSender().getUsername().equals(username) || m.getReceiver().getUsername().equals(username));
        Server.users.get(user.getUsername()).getPosts().clear();
        user.getMassaged().clear();
        user.getMuted().clear();
        user.getBlocked().clear();
        user.getBlocker().clear();
        user.setProfilePath("");
        user.setAnswer("");
        user.setQuestion("");
        user.setBio("");
        user.getFollowing().clear();
        user.getFollower().clear();
        user.setImage(null);
        user.setGender("");
        user.setBirthday("");
        user.setLocation("");
        user.setEmail("");
        user.setPhoneNumber("");
        user.setLastName("");
        user.setFirstName("");*/
        Server.users.remove(user.getUsername());
        /*user.setPassword("");
        user.setUsername("");*/
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.deleteAccount);
        ans.put("answer", new Boolean(true));
        System.out.println(username+"  delete account");
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method reports the user's log out
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> logOut(Map<String,Object> income){
        User user= (User) income.get("user");
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.logout);
        ans.put("answer", new Boolean(true));
        System.out.println(user.getUsername()+"  logout");
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method add the target user to user's blockeds and add the user to its blockers too
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> block(Map<String,Object> income){
        User user= (User) income.get("user");
        User cUser= (User) income.get("cUser");
        Server.users.get(cUser.getUsername()).getBlocked().add(user);
        Server.users.get(user.getUsername()).getBlocker().add(cUser);
        Server.users.get(cUser.getUsername()).getFollowing().remove(user);
        Server.users.get(cUser.getUsername()).getFollower().remove(user);
        Server.users.get(user.getUsername()).getFollower().remove(cUser);
        Server.users.get(user.getUsername()).getFollowing().remove(cUser);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.block);
        ans.put("answer", new Boolean(true));
        System.out.println(cUser.getUsername()+"  block");
        System.out.println("message: "+user.getUsername());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method remove the target user from user's blockeds and remove the user from its blockers too
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> unblock(Map<String,Object> income){
        User user= (User) income.get("user");
        User cUser= (User) income.get("cUser");
        Server.users.get(cUser.getUsername()).getBlocked().removeIf(a-> a.getUsername().equals(user.getUsername()));
        Server.users.get(user.getUsername()).getBlocker().removeIf(a-> a.getUsername().equals(cUser.getUsername()));
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.unblock);
        ans.put("answer", new Boolean(true));
        System.out.println(cUser.getUsername()+"  unblock");
        System.out.println("message: "+user.getUsername());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method send the user's blockeds to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getBlocked(Map<String, Object> income){
        User user= (User) income.get("user");
        List<User> list=Server.users.get(user.getUsername()).getBlocked();
        List<String> usernames=new ArrayList<>();
        for(User u: list){
            usernames.add(u.getUsername());
        }
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getBlocked);
        ans.put("answer", usernames);
        return ans;
    }

    /**
     * this method send the user's blockers to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getBlockers(Map<String, Object> income){
        User user= (User) income.get("user");
        List<User> list=Server.users.get(user.getUsername()).getBlocker();
        List<String> usernames=new ArrayList<>();
        for(User u: list){
            usernames.add(u.getUsername());
        }
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getBlocker);
        ans.put("answer", usernames);
        return ans;
    }

    /**
     * this method add the target user to user's muteds
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> mute(Map<String,Object> income){
        User user= (User) income.get("user");
        User cUser= (User) income.get("cUser");
        Server.users.get(cUser.getUsername()).getMuted().add(user);
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.mute);
        ans.put("answer", new Boolean(true));
        System.out.println(cUser.getUsername()+"  mute");
        System.out.println("message: "+user.getUsername());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method from the target user from user's muteds
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> unMute(Map<String,Object> income){
        User user= (User) income.get("user");
        User cUser= (User) income.get("cUser");
        Server.users.get(cUser.getUsername()).getMuted().removeIf(a-> a.getUsername().equals(user.getUsername()));
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.unMute);
        ans.put("answer", new Boolean(true));
        System.out.println(cUser.getUsername()+"  unmute");
        System.out.println("message: "+user.getUsername());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method send the user's muteds to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getMuted(Map<String, Object> income){
        User user= (User) income.get("user");
        List<User> list=Server.users.get(user.getUsername()).getMuted();
        List<String> usernames=new ArrayList<>();
        for(User u: list){
            usernames.add(u.getUsername());
        }
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getMuted);
        ans.put("answer", usernames);
        return ans;
    }

    /**
     * this method send the massage to the target user
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> sendMassage(Map<String,Object> income){
        User sender= (User) income.get("sender");
        User receiver= (User) income.get("receiver");
        Massage massage= (Massage) income.get("massage");
        //Massage date= (Massage) income.get("date");
        Server.massages.add(massage);
        //Server.massages.add(date);
        /*Server.users.get(sender.getUsername()).getMassaged().removeIf(u -> u.getUsername().equals(receiver.getUsername()));
        Server.users.get(receiver.getUsername()).getMassaged().removeIf(u -> u.getUsername().equals(sender.getUsername()));
        Server.users.get(sender.getUsername()).getMassaged().add(receiver);
        Server.users.get(receiver.getUsername()).getMassaged().add(sender);*/
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.sendMassage);
        ans.put("answer", new Boolean(true));
        System.out.println(sender.getUsername()+" send");
        System.out.println("message: from "+sender.getUsername()+" to "+receiver.getUsername()+" "+massage.getPath());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method reports the massage receiving to the target user
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> receiveMassage(Map<String,Object> income){
        User sender= (User) income.get("sender");
        User receiver= (User) income.get("receiver");
        Massage massage= (Massage) income.get("massage");
        //Massage date= (Massage) income.get("date");
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.receiveMassage);
        ans.put("answer", new Boolean(true));
        System.out.println(receiver.getUsername()+" receive");
        System.out.println("message: "+sender.getUsername()+" "+massage.getPath());
        System.out.println("time: "+Time.getTime());
        return ans;
    }

    /**
     * this method send all massages that the user is in one side of to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> getMassage(Map<String,Object> income){
        User sender= (User) income.get("sender");
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getMassages);
        List<Massage> sent=Server.massages.stream()
                .filter(a->(((a.getSender().getUsername().equals(sender.getUsername()))||a.getReceiver().getUsername().equals(sender.getUsername()))))
                .collect(Collectors.toList());
        ans.put("answer", sent);
        return ans;
    }

    /**
     * this method changes the unread massage state to read
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String,Object> readMassage(Map<String,Object> income){
        User user= (User) income.get("user");
        Massage massage= (Massage) income.get("massage");
        for(Massage m: Server.massages){
            if(m.equals(massage)){
                m.setRead(true);
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.readMassage);
        ans.put("answer", new Boolean(true));
        return ans;
    }

    /**
     * this method send the users that have conversation with the user to the client
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> getMassaged(Map<String, Object> income){
        User user= (User) income.get("user");
        Set<User> list=Server.users.get(user.getUsername()).getMassaged();
        List<String> usernames=new ArrayList<>();
        for(User u: list){
            usernames.add(u.getUsername());
        }
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.getMassaged);
        ans.put("answer", usernames);
        return ans;
    }

    /**
     * this method delete the massage
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> deleteMassage(Map<String, Object> income){
        User user= (User) income.get("user");
        Massage massage= (Massage) income.get("massage");
        int i=0;
        for(Massage m: Server.massages){
            if(m.equals(massage)){
                i=Server.massages.indexOf(m);
                massage.setText("this massage was deleted");
                Server.massages.set(i, massage);
                break;
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.deleteMassage);
        ans.put("answer", new Boolean(true));
        return ans;
    }

    /**
     * this method edit the massage
     * @param income its a map that server received from client and it filled by client's request
     * @return it returns a map that filled with server's answer that it responds the client's request
     */
    public static Map<String, Object> editMassage(Map<String, Object> income){
        User user= (User) income.get("user");
        Massage oldMassage= (Massage) income.get("oldMassage");
        String newMassage= (String) income.get("newMassage");
        int i=0;
        for(Massage m: Server.massages){
            if(m.equals(oldMassage)){
                i=Server.massages.indexOf(m);
                oldMassage.setText(newMassage);
                Server.massages.set(i, oldMassage);
                break;
            }
        }
        Database.getInstance().updateDataBase();
        Map<String,Object> ans = new HashMap<>();
        ans.put("request", Requests.editMassage);
        ans.put("answer", new Boolean(true));
        return ans;
    }
}
