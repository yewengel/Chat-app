import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.*;

public class Client {

    private JFrame frame;
    private JTextPane chatPane;
    private JTextField input;
    private StyledDocument doc;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    // Profile info
    public String fullName = "Not set";
    public String email = "Not set";
    public String bio = "Not set";

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public Client(String ip, int port, String username) throws IOException {
        this.username = username;

        Socket socket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username);

        buildUI();
        listen();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitChat();
            }
        });
    }

    private void buildUI() {
        frame = new JFrame("Java Chat App - " + username);

        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setBackground(new Color(180, 210, 255));
        doc = chatPane.getStyledDocument();

        input = new JTextField(30);
        JButton send = new JButton("Send");

        JPanel bottom = new JPanel();
        bottom.add(input);
        bottom.add(send);

        frame.add(new JScrollPane(chatPane), BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar());

        frame.setSize(750, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        input.addActionListener(e -> send());
        send.addActionListener(e -> send());
    }

    private JMenuBar menuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu theme = new JMenu("Theme");
        theme.add(themeItem("Light", Color.WHITE));
        theme.add(themeItem("Blue", new Color(180, 210, 255)));
        theme.add(themeItem("Dark", Color.DARK_GRAY));
        theme.add(themeItem("Green", new Color(200, 255, 200)));

        JMenu profile = new JMenu("Profile");
        JMenuItem edit = new JMenuItem("Edit Profile");
        JMenuItem view = new JMenuItem("View Profile");
        edit.addActionListener(e -> editProfile());
        view.addActionListener(e -> viewProfile());
        profile.add(edit);
        profile.add(view);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About App");
        JMenuItem guide = new JMenuItem("How to Use");
        about.addActionListener(e -> showAbout());
        guide.addActionListener(e -> showGuide());
        help.add(about);
        help.add(guide);

        bar.add(theme);
        bar.add(profile);
        bar.add(help);

        return bar;
    }

    private JMenuItem themeItem(String name, Color color) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(e -> chatPane.setBackground(color));
        return item;
    }

    private void editProfile() {
        fullName = JOptionPane.showInputDialog(frame, "Full Name:", fullName);
        email = JOptionPane.showInputDialog(frame, "Email:", email);
        bio = JOptionPane.showInputDialog(frame, "Short Bio:", bio);
    }

    private void viewProfile() {
        JOptionPane.showMessageDialog(frame,
                "👤 Profile Information\n\n" +
                        "Full Name: " + fullName + "\n" +
                        "Username: " + username + "\n" +
                        "Email: " + email + "\n" +
                        "Bio: " + bio,
                "Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(frame,
                "Java Client–Server Chat Application\n\n" +
                        "• Built using Java Socket Programming\n" +
                        "• Supports multiple clients\n" +
                        "• Uses Swing for GUI\n" +
                        "• Client–Server architecture",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showGuide() {
        JOptionPane.showMessageDialog(frame,
                "How to Use the Chat App:\n\n" +
                        "1. Type your message and press Enter or Send\n" +
                        "2. Change themes from Theme menu\n" +
                        "3. Edit/view profile from Profile menu\n" +
                        "4. Exit safely using close button",
                "Help Guide", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listen() {
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    append(msg, Color.BLACK);
                }
            } catch (IOException e) {
                append("Connection closed.", Color.RED);
            }
        }).start();
    }

    private void send() {
        String text = input.getText().trim();
        if (text.isEmpty()) return;
        out.println(text);
        append("Me: " + text, Color.BLUE);
        input.setText("");
    }

    private void append(String msg, Color c) {
        try {
            Style style = chatPane.addStyle("style", null);
            StyleConstants.setForeground(style, c);
            doc.insertString(doc.getLength(),
                    "[" + sdf.format(new Date()) + "] " + msg + "\n", style);
        } catch (Exception ignored) {}
    }

    private void exitChat() {
        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to leave the chat?",
                "Exit Chat",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            out.println("/quit");
            JOptionPane.showMessageDialog(frame,
                    "🌸 Thank you for chatting with us!\n" +
                            "We hope to see you again soon 💙",
                    "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JPanel panel = new JPanel(new GridLayout(0, 1));

                JTextField ipField = new JTextField("127.0.0.1");
                JTextField usernameField = new JTextField();
                JTextField fullNameField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField bioField = new JTextField();

                panel.add(new JLabel("Server IP:"));
                panel.add(ipField);
                panel.add(new JLabel("Username:"));
                panel.add(usernameField);
                panel.add(new JLabel("Full Name:"));
                panel.add(fullNameField);
                panel.add(new JLabel("Email:"));
                panel.add(emailField);
                panel.add(new JLabel("Short Bio:"));
                panel.add(bioField);

                JButton helpButton = new JButton("Help");
                panel.add(helpButton);

                helpButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                        "Fill in the following before entering the chat:\n\n" +
                                "• Server IP (e.g., 127.0.0.1)\n" +
                                "• Username (your chat name)\n" +
                                "• Full Name (optional but recommended)\n" +
                                "• Email (optional)\n" +
                                "• Short Bio (optional)",
                        "Help", JOptionPane.INFORMATION_MESSAGE));

                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Enter Chat Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result != JOptionPane.OK_OPTION) System.exit(0);

                String ip = ipField.getText().trim();
                String username = usernameField.getText().trim();
                String fullName = fullNameField.getText().trim();
                String email = emailField.getText().trim();
                String bio = bioField.getText().trim();

                if (ip.isEmpty() || username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Server IP and Username are required!");
                    System.exit(0);
                }

                Client client = new Client(ip, 5000, username);

                if (!fullName.isEmpty()) client.fullName = fullName;
                if (!email.isEmpty()) client.email = email;
                if (!bio.isEmpty()) client.bio = bio;

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to connect to server.");
            }
        });
    }
}
