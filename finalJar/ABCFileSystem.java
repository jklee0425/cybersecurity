package client;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ABCFileSystem extends JFrame implements ActionListener {
    JFileChooser fc;
    JButton btnFind;
    JButton btnChoose;
    JLabel lbFind;
    JLabel lbFile;
    JButton btnUpload;

    public void buildGUI() {
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        
        lbFind = new JLabel();
        btnFind = new JButton("Find");
        btnFind.addActionListener(this);
        inputPanel.add(lbFind);
        inputPanel.add(btnFind);

        JPanel btnPanel = new JPanel();
        btnChoose = new JButton("Choose");
        lbFile = new JLabel("Choose a file to upload...");
        btnUpload = new JButton("Upload");
        btnChoose.addActionListener(this);
        btnUpload.addActionListener(this);
        btnPanel.add(btnChoose);
        btnPanel.add(lbFile);
        btnPanel.add(btnUpload);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.PAGE_START);
        add(btnPanel, BorderLayout.CENTER);
        setTitle("ABC Airlines File System");
        setSize(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public ABCFileSystem() {
        buildGUI();
        fc = new JFileChooser();
    }
    public static void main(String[] args) {
        new ABCFileSystem();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int retVal;
        if (e.getSource() == btnFind) {
            new FileBrowser();
            // TODO
        } else if (e.getSource() == btnChoose) {
            retVal = fc.showDialog(ABCFileSystem.this, "Choose");
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                lbFile.setText(file.getName());
            }
        } else if (e.getSource() == btnUpload) {
            // TODO
            File file = fc.getSelectedFile();
            Path path = Paths.get(System.getProperty("user.dir") + "\\");
            // Files.copy(file, path.resolve(file.getName()));
        }
    }
    public void log(){
        
    }
}