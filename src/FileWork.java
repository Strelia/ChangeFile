import com.jcraft.jsch.SftpException;

import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by strelchenko on 21.08.15.
 */
public class FileWork {

    String getText(SFTPinJava sftp, String removePathFile, String file) {
        BufferedInputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            sftp.getChannelSftp().cd(removePathFile);
            inputStream = new BufferedInputStream(sftp.getChannelSftp().get(file));
        } catch (SftpException e) {
            e.printStackTrace();
        }
        int b;
        try {
            while ((b = inputStream.read()) != -1) {
                builder.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    void setText(SFTPinJava sftp, String removePathFile, String file, String mess) {
        try {
            sftp.getChannelSftp().cd(removePathFile);
            File f = new File(file);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                byte[] content = mess.getBytes();
                OutputStream out = sftp.getChannelSftp().put(file);
                out.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
