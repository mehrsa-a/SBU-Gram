package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

/**
 * <h1>ConnectClient</h1>
 * <p>this class is connection between client and server in client side</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class ConnectClient{
    public static String serverAddress="localhost";
    public static final int port=2397;
    public static Socket socket;
    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;
    private static boolean isConnected = false;

    public static boolean isConnected(){
        return isConnected;
    }

    /**
     * this method connects client to server
     * @return a boolean that shows if client connected or not
     */
    public static Boolean connectToServer(){
        if(socket!=null){
            return false;
        }
        try{
            socket=new Socket(serverAddress, port);
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            isConnected=true;
            return true;
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * this method disconnects client from server
     * @return a boolean that shows if client connected or not
     */
    public static Boolean disconnectFromServer(){
        try{
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            isConnected=false;
            socket=null;
            objectInputStream=null;
            objectOutputStream=null;
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        socket=null;
        objectInputStream=null;
        objectOutputStream=null;
        return false;
    }

    /**
     * this method send massage to server and receive massage from
     * @param toSend its the map that this method send to server
     * @return it returns server answer in map way
     */
    public static Map<String,Object> serve(Map<String,Object> toSend){
        Map<String,Object> get=null;
        try{
            objectOutputStream.writeObject(toSend);
            objectOutputStream.flush();
            objectOutputStream.reset();
            get=(Map<String,Object>) objectInputStream.readObject();
            return get;

        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
        return get;
    }
}
