import java.io.*;
import java.util.StringTokenizer;

import com.jcraft.jsch.*;

/**
 * Created by strelchenko on 17.08.15.
 */
public class SFTPinJava {

    final int SFTPPORT = 22;
    Session session;
    Channel channel;
    ChannelSftp channelSftp;


    void connect(String sftpHost, String user, String pass) {
        JSch jsch = new JSch();
        session = null;
        channel = null;
        channelSftp = null;
        try {
            session = jsch.getSession(user, sftpHost, SFTPPORT);
            session.setPassword(pass);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
            channelSftp.disconnect();
            channel.disconnect();
            session.disconnect();
        }
    }

    ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    String tree(String removePathFile) {
        System.out.println(removePathFile);
        StringBuilder treeText = new StringBuilder();
        try {
            channelSftp.cd(removePathFile);
            treeText.append(channelSftp.pwd() + "\n");
            for (Object enumeration : channelSftp.ls(channelSftp.pwd())) {
                StringTokenizer tokenizer = new StringTokenizer(enumeration.toString()," ");
                treeText.append(String.format(
                        "%10s %3s %15s %15s %5s %3s %3s %6s %25s\n",
                        tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken(),
                        tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken(),
                        tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken()
                ));
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return treeText.toString();
    }

    void disconnect() {
        channelSftp.disconnect();
        channel.disconnect();
        session.disconnect();
    }
}
