import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strelchenko on 17.08.15.
 */
public class Main extends JFrame {
    private JTextField user;
    private JPasswordField pass;
    private JButton GetFile;
    private JButton SetFile;
    private JTextField IPServer;
    private JPanel rootPanel;
    private JTextField pathToFile;
    private JTextField fileName;
    private JTextArea dirTree;
    private JButton treeDir;
    private JTextArea context;

    public Main() {
        super("File Change");
        setContentPane(rootPanel);
        setBounds(500, 400, 370, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        GetFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.setText("");
                String passStr = new String(pass.getPassword());
                SFTPinJava sftp = new SFTPinJava();
                sftp.connect(IPServer.getText(), user.getText(), passStr);

                FileWork file = new FileWork();
                if (sftp.getChannelSftp().isConnected()){
                    context.setText(file.getText(sftp, pathToFile.getText(), fileName.getText()));
                    sftp.disconnect();
                }else {
                    context.setText("NOT CONNECT");
                }
            }
        });
        SetFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passStr = new String(pass.getPassword());
                SFTPinJava sftp = new SFTPinJava();
                sftp.connect(IPServer.getText(), user.getText(), passStr);

                FileWork file = new FileWork();
                if (sftp.getChannelSftp().isConnected()){
                    file.setText(sftp, pathToFile.getText(), fileName.getText(), context.getText());
                    sftp.disconnect();
                }else {
                    context.setText("NOT CONNECT");
                }
            }
        });
        treeDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dirTree.setText("");
                String passStr = new String(pass.getPassword());

                SFTPinJava sftp = new SFTPinJava();
                sftp.connect(IPServer.getText(), user.getText(), passStr);

                if (sftp.getChannelSftp().isConnected()) {
                    dirTree.setText(sftp.tree(pathToFile.getText()));
                }

                sftp.disconnect();
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
            }
        });
    }

}
