package Server;

import Common.Requests;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket userSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public Boolean clientOnline=true;

    public ClientHandler(Socket socket){
        try{
            userSocket=socket;
            this.objectOutputStream=new ObjectOutputStream (userSocket.getOutputStream());
            this.objectInputStream=new ObjectInputStream (userSocket.getInputStream());
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            Map<String,Object> income=null;
            try{
                income=(Map<String,Object>) objectInputStream.readObject();
                Map<String,Object> answer=null;
                Requests command=(Requests) income.get("request");
                switch(command){
                    case login:
                        answer=ServerAPI.login(income);
                        break;
                    case newUsername:
                        answer = ServerAPI.isUsernameValid(income);
                        break;
                    case signup:
                        answer = ServerAPI.signUp(income);
                        break;
                    case addPost:
                        answer = ServerAPI.addPost(income);
                        break;
                    case getPosts:
                        answer = ServerAPI.getPosts(income);
                        break;
                    case getTimeline:
                        answer = ServerAPI.getTimeLine(income);
                        break;
                    case getMyPosts:
                        answer = ServerAPI.getMyPosts(income);
                        break;
                    case getUsers:
                        answer = ServerAPI.getUsers(income);
                        break;
                    case follow:
                        answer = ServerAPI.follow(income);
                        break;
                    case getNumbers:
                        answer = ServerAPI.getNumbers(income);
                        break;
                    case getNum:
                        answer = ServerAPI.getNum(income);
                        break;
                    case getFollowers:
                        answer = ServerAPI.getFollowers(income);
                        break;
                    case getFollowing:
                        answer = ServerAPI.getFollowings(income);
                        break;
                    case unfollow:
                        answer = ServerAPI.unfollow(income);
                        break;
                    case like:
                        answer = ServerAPI.like(income);
                        break;
                    case dislike:
                        answer = ServerAPI.dislike(income);
                        break;
                    case getPostFeatures:
                        answer = ServerAPI.getPostFeatures(income);
                        break;
                    case getLikes:
                        answer = ServerAPI.getLikes(income);
                        break;
                    case repost:
                        answer = ServerAPI.repost(income);
                        break;
                    case addComment:
                        answer = ServerAPI.addComment(income);
                        break;
                    case getComments:
                        answer = ServerAPI.getComments(income);
                        break;
                    case setProfile:
                        answer = ServerAPI.setProfile(income);
                        break;
                    case changeProfile:
                        answer = ServerAPI.changeProfile(income);
                        break;
                    case getProfile:
                        answer = ServerAPI.getProfile(income);
                        break;
                    case setInformation:
                        answer = ServerAPI.addInformation(income);
                        break;
                    case getInformation:
                        answer = ServerAPI.getInformation(income);
                        break;
                    case setForgetPassword:
                        answer = ServerAPI.setForgetPassword(income);
                        break;
                    case getForgetPassword:
                        answer = ServerAPI.getForgetPassword(income);
                        break;
                    case changePassword:
                        answer = ServerAPI.changePassword(income);
                        break;
                    case deleteAccount:
                        answer = ServerAPI.deleteAccount(income);
                        break;
                    case logout:
                        answer = ServerAPI.logOut(income);
                        break;
                    case block:
                        answer = ServerAPI.block(income);
                        break;
                    case unblock:
                        answer = ServerAPI.unblock(income);
                        break;
                    case getBlocked:
                        answer = ServerAPI.getBlocked(income);
                        break;
                    case getBlocker:
                        answer = ServerAPI.getBlockers(income);
                        break;
                    case mute:
                        answer = ServerAPI.mute(income);
                        break;
                    case unMute:
                        answer = ServerAPI.unMute(income);
                        break;
                    case getMuted:
                        answer = ServerAPI.getMuted(income);
                        break;
                    case sendMassage:
                        answer = ServerAPI.sendMassage(income);
                        break;
                    case receiveMassage:
                        answer = ServerAPI.receiveMassage(income);
                        break;
                    case getMassages:
                        answer = ServerAPI.getMassage(income);
                        break;
                    case readMassage:
                        answer = ServerAPI.readMassage(income);
                        break;
                    case getMassaged:
                        answer = ServerAPI.getMassaged(income);
                        break;
                    case deleteMassage:
                        answer= ServerAPI.deleteMassage(income);
                        break;
                    case editMassage:
                        answer = ServerAPI.editMassage(income);
                        break;
                }
                objectOutputStream.writeObject(answer);
                objectOutputStream.flush();
            }
            catch(ClassCastException | ClassNotFoundException e){
                e.printStackTrace();
            } catch(IOException e){
                break;
            }

        }
        try{
            objectInputStream.close();
            objectOutputStream.close();
            userSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
