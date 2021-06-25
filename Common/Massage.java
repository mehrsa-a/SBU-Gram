package Common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <h1>Massage</h1>
 * <p>this class handles massages</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class Massage implements Serializable, Comparable {
    public static long serialVersionUID=79676435L;
    private User sender;
    private User receiver;
    private String text;
    private byte[] file;
    private String path;
    private final long createdTime = Time.getMilli();
    private final String timeString = Time.getTime();
    private boolean read;
    private boolean dateFlag;

    public void setDateFlag(boolean dateFlag) {
        this.dateFlag = dateFlag;
    }

    public boolean isDateFlag() {
        return dateFlag;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeString() {
        return timeString;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public byte[] getFile() {
        return file;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(Object o) {
        Massage other=(Massage) o;
        if(this.createdTime<other.createdTime){
            return -1;
        }
        else if(this.createdTime>other.createdTime){
            return 1;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o){
        Massage m=(Massage) o;
        if(file!=null){
            return this.getSender().equals(m.getSender()) && this.getReceiver().equals(m.getReceiver()) && Arrays.equals(this.getFile(), m.getFile());
        }
        else{
            return this.getText().equals(m.getText()) && this.getSender().equals(m.getSender()) && this.getReceiver().equals(m.getReceiver());
        }
    }
}
