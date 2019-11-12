import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileSystems;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ABCFileSystem {

    private final URI PATH = "";
    FileSystem fs;
    JFileChooser fc;
    JTextArea logArea;
    JTextField userInput;
    JButton btnFind;
    JButton btnChoose;
    JButton btnUpload;

    public ABCFileSystem() {
        
        buildGUI();
        fs = newFileSystem(PATH);
        fc = new JFileChooser();

        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        userInput = new JTextField();
        btnFind = new JButton("Find");
        btnFind.addActionListener((ActionListener) this);

        JPanel btnPanel = new JPanel();
        btnChoose = new JButton("Choose");
        btnUpload = new JButton("Upload");
        btnChoose.addActionListener((ActionListener) this);
        btnUpload.addActionListener((ActionListener) this);
        btnPanel.add(btnChoose);
        btnPanel.add(btnUpload);

        logArea = new JTextArea();
        logArea.setEditable(false);

        setLayout(new BorderLayout());
        add(tfUserInput, BorderLayout.PAGE_START);
        add(btnPanel, BorderLayout.CENTER);
        add(new ScrollPane(logArea), BorderLayout.SOUTH);
    }

    private void buildGUI() {
        JFrame frame = new JFrame("ABC Airlines File System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(frame, new ABCFileSystem());
        frame.pack();
        frame.setVisible(true);
    }

    // reference : https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
    public void actionPerformed(ActionEvent e) {
        int retVal;
        switch(e.getSource()) {
            case btnFind:
                String fName = userInput.getText();
                logArea.append("Looking for: " + fName + "\n");
                logArea.setCaretPosition(log.getDocument().getLength());
                // TODO
                // Find if the file is in the file system and act accordingly
                break;
            case btnChoose:
                // TODO
                // retVal = fc.showDialog(ABCFileSystem.this, "Choose");
                // if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    logArea.append("File Selected: " + file.getName() + "\n");
                    logArea.setCaretPosition(log.getDocument().getLength());
                // }
                break;
            case btnUpload:
                // TODO
                    logArea.append("File Uploaded: " + file.getName() + "\n");
                    logArea.setCaretPosition(log.getDocument().getLength());
                    
                break;
            default:
        }
    }

    public static void main(String[] args) {
        new ABCFileSystem();
        // SwingUtilities.invokeLater(new Runnable() {
        //     public void run() {
        //         //Turn off metal's use of bold fonts
        //         UIManager.put("swing.boldMetal", Boolean.FALSE); 
        //         buildGUI();
        //     }
        // });
    }
}