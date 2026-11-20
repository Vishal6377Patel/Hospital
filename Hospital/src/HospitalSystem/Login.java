package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private  JPanel panel;

    // Components declare karein
    private JLabel lblUsername, lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSign;
    private JLabel choose;
    private JComboBox cob;

    public Login() {
        setTitle("Hospital Management System - Login");

        // 1. Image Load (Aapka bataya hua path)
        ImageIcon icon = new ImageIcon("src/icon/first.png");

        // Image ko frame ke size (800x600) ke hisaab se scale karna (Optional par achha dikhta hai)
        Image scaledImage = icon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImage);

        JLabel background = new JLabel(finalIcon);
        setContentPane(background);
        background.setLayout(null); // IMPORTANT: Isse hi components upar dikhenge

        // 2. Username Label aur TextField
        lblUsername = new JLabel("Username:");
        lblUsername.setBounds(200, 200, 150, 30); // X, Y, Width, Height
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUsername.setForeground(Color.BLACK); // Agar background dark hai toh white karein
        background.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(350, 200, 200, 30);
        background.add(txtUsername);

        // 3. Password Label aur Field
        lblPassword = new JLabel("Password:");
        lblPassword.setBounds(200, 260, 150, 30);
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
        background.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(350, 260, 200, 30);
        background.add(txtPassword);

        //4.ChoosenBox
        choose = new JLabel("Choose User:");
        choose.setBounds(200, 320, 150, 30);
        choose.setForeground(Color.BLACK);
        choose.setFont(new Font("Tahoma", Font.BOLD, 16));
        background.add(choose);

        String[] userOptions = {"Admin", "Doctor", "Patient", "Receptionist"};
        cob = new JComboBox<>(userOptions);
        cob.setBounds(350, 320, 200, 30);
        cob.setBackground(Color.WHITE);
        background.add(cob);

        // 5. Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(340, 420, 100, 40);
        btnLogin.setBackground(new Color(30, 144, 255)); // Blue color
        btnLogin.setForeground(Color.WHITE);
        background.add(btnLogin);

        // 6. SignUp Button
        btnSign = new JButton("SignUp");
        btnSign .setBounds(460, 420, 100, 40);
        btnSign .setBackground(new Color(30, 144, 255)); // Blue color
        btnSign .setForeground(Color.WHITE);
        background.add(btnSign);

        btnSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUp().setVisible(true);
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                String selectedRole = (String) cob.getSelectedItem(); // ComboBox se role uthayein

                try {
                    Conn c = new Conn();
                    // Query mein 'role' column check ho raha hai
                    String query = "select * from login where username = ? and password = ? and role = ?";

                    java.sql.PreparedStatement pst = c.c.prepareStatement(query);
                    pst.setString(1, username);
                    pst.setString(2, password);
                    pst.setString(3, selectedRole);

                    java.sql.ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login Successfully as " + selectedRole);

                        // Role ke basis par alag screens open karna
                        if(selectedRole.equals("Admin")) {
                            new Admin().setVisible(true);
                        }
                        else if (selectedRole.equals("Patient")) {
                            new PatientMain().setVisible(true);
                        }
                        else if (selectedRole.equals("Doctor")) {
                            new DoctorMain().setVisible(true);
                        }
                        else if (selectedRole.equals("Receptionist")) {
                            new ReceptionistMain().setVisible(true);
                        }

                        // Current Login window ko close karne ke liye
                        setVisible(false);

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username, Password, or Role!");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
                }
            }
        });
        // Frame Settings
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}