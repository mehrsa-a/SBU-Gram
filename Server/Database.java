package Server;

import Common.Massage;
import Common.Post;
import Common.User;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Database {
    public static final String FILE_PREFIX="C:\\Users\\98917\\IdeaProjects\\project\\src\\database\\";
    public static final String UsersFile=FILE_PREFIX+"UsersDatabase.txt";
    public static final String PostsFile=FILE_PREFIX+"PostsDatabase.txt";
    public static final String MassagesFile=FILE_PREFIX+"MassagesDatabase.txt";


    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {}

    public synchronized void initializeServer(){
        try {
            FileInputStream fin=new FileInputStream(Database.UsersFile);
            ObjectInputStream inFromFile=new ObjectInputStream(fin);
            Server.users = new ConcurrentHashMap<>( (ConcurrentHashMap<String, User>) inFromFile.readObject());
            inFromFile.close();
            fin.close();
        }
        catch(EOFException | StreamCorruptedException e){
            Server.users = new ConcurrentHashMap<>();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(Database.PostsFile);
            ObjectInputStream inFromFile = new ObjectInputStream(fin);
            Server.posts = new ConcurrentSkipListSet<>( (ConcurrentSkipListSet<Post>) inFromFile.readObject());
            inFromFile.close();
            fin.close();
        }
        catch(EOFException | StreamCorruptedException e){
            Server.posts = new ConcurrentSkipListSet<>();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(Database.MassagesFile);
            ObjectInputStream inFromFile = new ObjectInputStream(fin);
            Server.massages = new CopyOnWriteArrayList<>( (CopyOnWriteArrayList<Massage>) inFromFile.readObject());
            inFromFile.close();
            fin.close();
        }
        catch(EOFException | StreamCorruptedException e){
            Server.massages = new CopyOnWriteArrayList<>();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void updateDataBase() {
        try {
            FileOutputStream fout = new FileOutputStream(UsersFile);
            ObjectOutputStream objToFile = new ObjectOutputStream(fout);
            objToFile.reset();
            objToFile.writeObject(Server.users);
            objToFile.close();
            fout.close();
            fout = new FileOutputStream(PostsFile);
            objToFile = new ObjectOutputStream(fout);
            objToFile.reset();
            objToFile.writeObject(Server.posts);
            objToFile.close();
            fout.close();
            fout = new FileOutputStream(MassagesFile);
            objToFile = new ObjectOutputStream(fout);
            objToFile.reset();
            objToFile.writeObject(Server.massages);
            objToFile.close();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
