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
                    /*case UPDATE_PROFILE:
                        answer = API.updateProfile(income);
                        break;
                    case LOGOUT:
                        answer = API.logout(income);
                        clientOnline = false;
                        break;
                    case SEND_MAIL:
                        answer = API.sendMail(income);
                        break;
                    case CHECK_MAIL:
                        answer = API.checkMail(income);
                        break;
                    case TRASH_MAIL:
                        answer = API.trashMail(income);
                        break;
                    case READ_MAIL:
                        answer = API.readMail(income);
                        break;
*/
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
