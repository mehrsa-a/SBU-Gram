package Server;

import Common.Massage;
import Common.Post;
import Common.Time;
import Common.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>Server</h1>
 * <p>this class is the server's main and run the server side</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class Server implements Runnable{
    public static final int port=2397;
    public static Map<String, User> users=new HashMap<>();
    public static Set<Post> posts=null;
    public static List<Massage> massages=null;
    public static ServerSocket serverSocket=null;

    /**
     * this method initialize all information in database and runs the server
     * it make a new socket and start listening
     */
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

    /**
     * this method is a socket that always listening and after connecting a new client, it makes a new client handler and new thread for it
     */
    @Override
    public void run() {
        while (true){
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
