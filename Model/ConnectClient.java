package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class ConnectClient{
    public static String serverAddress="localhost";
    public static final int port=2106;
    public static Socket socket;
    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;
    private static boolean isConnected = false;

    public static boolean isConnected(){
        return isConnected;
    }

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
