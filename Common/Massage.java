package Common;

import java.io.Serializable;
import java.util.Arrays;

public class Massage implements Serializable, Comparable {
    private User sender;
    private User receiver;
    private String text;
    private byte[] file;
    private final long createdTime = Time.getMilli();
    private final String timeString = Time.getTime();

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
            return this.getText().equals(m.getText()) && this.getSender().equals(m.getSender()) && this.getReceiver().equals(m.getReceiver()) && Arrays.equals(this.getFile(), m.getFile());
        }
        else{
            return this.getText().equals(m.getText()) && this.getSender().equals(m.getSender()) && this.getReceiver().equals(m.getReceiver());
        }
    }
}
