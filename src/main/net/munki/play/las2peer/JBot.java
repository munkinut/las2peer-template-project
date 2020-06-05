package net.munki.play.las2peer;

import java.io.Serializable;
import java.util.Objects;

public class JBot implements Serializable {

    private static final long serialVersionUID = 225L;

    private String realName;
    private String nickname;
    private String description;
    private String ownerEmail;
    private String server;
    private String channel;
    private String ip;
    private int port;
    private String message;

    public JBot(String realName,
                String nickname,
                String description,
                String ownerEmail,
                String server,
                String channel,
                String ip,
                int port,
                String message) {
        super();
        this.realName = realName;
        this.nickname = nickname;
        this.description = description;
        this.ownerEmail = ownerEmail;
        this.server = server;
        this.channel = channel;
        this.ip = ip;
        this.port = port;
        this.message = message;
    }


    public String getRealName() {
        return this.realName;
    }


    public String getNickname() {
        return this.nickname;
    }


    public String getDescription() {
        return this.description;
    }


    public String getOwnerEmail() {
        return this.ownerEmail;
    }


    public String getServer() {
        return this.server;
    }


    public String getChannel() {
        return this.channel;
    }


    public String getIp() {
        return this.ip;
    }


    public int getPort() {
        return this.port;
    }


    public String getMessage() {
        return this.message;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }


    public void setServer(String server) {
        this.server = server;
    }


    public void setChannel(String channel) {
        this.channel = channel;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JBot jBot = (JBot) o;
        return realName.equals(jBot.realName);
    }

    public int hashCode() {
        return Objects.hash(realName);
    }


}
