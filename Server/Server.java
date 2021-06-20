package Server;

import Common.Post;
import Common.Time;
import Common.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable{
    public static final int port=2149;
    private static boolean isServerUp=true;
    public static Map<String, User> users=new HashMap<>();
    public static Set<Post> posts=null;
    public static ServerSocket serverSocket=null;

    public static boolean isServerUp(){
        return isServerUp;
    }

    public static void main(String[] args) {
        Database.getInstance().initializeServer();
        try {
            serverSocket=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread t=new Thread(new Server());
        t.start();
    }

    @Override
    public void run() {
        while (isServerUp()){
            Socket currentUserSocket=null;
            try {
                currentUserSocket=serverSocket.accept();
                System.out.println("connect");
                System.out.println("time: "+ Time.getTime());
                ClientHandler clientHandler=new ClientHandler(currentUserSocket);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
