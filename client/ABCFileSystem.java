import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ABCFileSystem extends JFrame implements ActionListener, MouseListener {

    // private final URI PATH = new URI("");
    FileSystem fs;
    JFileChooser fc;
    JTextArea logArea;
    JTextField userInput;
    JButton btnFind;
    JButton btnChoose;
    JLabel lbFile;
    JButton btnUpload;

    public void buildGUI() {
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        userInput = new JTextField("Type a filename to find...");
        userInput.addMouseListener(this);
        btnFind = new JButton("Find");
        btnFind.addActionListener(this);
        inputPanel.add(userInput);
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

        logArea = new JTextArea();
        logArea.setEditable(false);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.PAGE_START);
        add(btnPanel, BorderLayout.CENTER);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);
        setTitle("ABC Airlines File System");
        setSize(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public ABCFileSystem() {
        buildGUI();
        // fs = newFileSystem(PATH);
        fc = new JFileChooser();
    }
    // reference :
    // https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.htm
    @Override
    public void actionPerformed(ActionEvent e) {
        int retVal;
        if (e.getSource() == btnFind) {
            String fName = userInput.getText();
            logArea.append("Looking for: " + fName + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
            // TODO
            // Find if the file is in the file system and act accordingly
        } else if (e.getSource() == btnChoose) {
            retVal = fc.showDialog(ABCFileSystem.this, "Choose");
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                lbFile.setText(file.getName());
                logArea.append("File Selected: " + file.getName() + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            }
        } else if (e.getSource() == btnUpload) {
            // TODO
            File file = fc.getSelectedFile();
            logArea.append("File Uploaded: " + file.getName() + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());

        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        userInput.setText("");
    }
    public static void main(String[] args) {
        new ABCFileSystem();
        // SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        // //Turn off metal's use of bold fonts
        // UIManager.put("swing.boldMetal", Boolean.FALSE);
        // buildGUI();
        // }
        // });
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}