package client;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class FileBrowser extends JFrame implements TreeSelectionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private String userRank, userBranch; // 302 sales, 101 warehouse
    private String perm;

    public FileBrowser(int rank, String branch) {
        if (rank == 101) {
            userRank = "Warehouse";
        } else if (rank == 302) {
            userRank = "Sales";
        } else {
            userRank = "";
        }
        perm = userRank == "" ? "w" : "r";
        userBranch = branch;

        File fileRoot = new File(System.getProperty("user.dir") + "\\cybersecurity\\ABCFS" + branch + userRank);
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);

        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(tree);

        setTitle("ABC File System");
        add(scrollPane);
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setVisible(true);

        new Thread(new CreateChildNodes(fileRoot, root)).start();
    }

    public static void main(String[] args) {
        new FileBrowser(0, "");
    }

    private class CreateChildNodes implements Runnable {

        private DefaultMutableTreeNode root;

        private File fileRoot;

        public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
            this.fileRoot = fileRoot;
            this.root = root;
        }

        @Override
        public void run() {
            createChildren(fileRoot, root);
        }

        private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if (files != null) {
                for (File file : files) {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                    node.add(childNode);
                    if (file.isDirectory()) {
                        createChildren(file, childNode);
                    }
                }
            }
        }

    }

    private class FileNode {

        private File file;

        public FileNode(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            String name = file.getName();
            return name.equals("") ? file.getAbsolutePath() : name;
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()){
            System.out.println(nodeInfo.toString());
        }
    }
}