package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistRegistration extends JFrame {

    public ReceptionistRegistration(){
        // Image Load
        ImageIcon icon = new ImageIcon("src/icon/four.png");
        Image scaledImage = icon.getImage().getScaledInstance(1550, 800, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImage);

        JLabel background = new JLabel(finalIcon);
        setContentPane(background);
        background.setLayout(null);

        // --- Form Heading ---
        JLabel heading = new JLabel("RECEPTIONIST REGISTRATION");
        heading.setBounds(250, 20, 400, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        background.add(heading);

        // --- Labels aur Fields ---

        // Name
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 80, 200, 30);
        background.add(lblName);
        JTextField tfName = new JTextField();
        tfName.setBounds(150, 80, 300, 30);
        background.add(tfName);

        // Age
        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(30, 130, 200, 30);
        background.add(lblAge);
        JTextField tfAge = new JTextField();
        tfAge.setBounds(150, 130, 300, 30);
        background.add(tfAge);

        // Gender (Dropdown use karna zyada better hai)
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(30, 180, 200, 30);
        background.add(lblGender);
        String[] genderOptions = {"Male", "Female", "Other"};
        JComboBox<String> cbGender = new JComboBox<>(genderOptions);
        cbGender.setBounds(150, 180, 300, 30);
        background.add(cbGender);

        // Mobile
        JLabel lblMobile = new JLabel("Mobile:");
        lblMobile.setBounds(30, 230, 200, 30);
        background.add(lblMobile);
        JTextField tfMobile = new JTextField();
        tfMobile.setBounds(150, 230, 300, 30);
        background.add(tfMobile);

        // Address
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(30, 280, 200, 30);
        background.add(lblAddress);
        JTextField tfAddress = new JTextField();
        tfAddress.setBounds(150, 280, 300, 30);
        background.add(tfAddress);

        // Username
        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(30, 330, 200, 30);
        background.add(lblUser);
        JTextField tfUser = new JTextField();
        tfUser.setBounds(150, 330, 300, 30);
        background.add(tfUser);

        // Password
        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(30, 380, 200, 30);
        background.add(lblPass);
        JPasswordField pfPass = new JPasswordField(); // PasswordField ka use
        pfPass.setBounds(150, 380, 300, 30);
        background.add(pfPass);

        // Submit Button
        JButton btnAdd = new JButton("Add Receptionist");
        btnAdd.setBounds(200, 430, 150, 40);
        btnAdd.setBackground(Color.BLACK);
        btnAdd.setForeground(Color.WHITE);
        background.add(btnAdd);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                String age = tfAge.getText();
                String gender = (String) cbGender.getSelectedItem();
                String mobile = tfMobile.getText();
                String address = tfAddress.getText();
                String user = tfUser.getText();
                String pass = new String(pfPass.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and Password are mandatory!");
                    return;
                }

                try {
                    Conn c = new Conn();

                    // 1. Receptionist Table Entry (ID skip kar rahe hain kyunki wo auto-increment hai)
                    String q1 = "insert into receptionist (name, age, gender, mobile, address) values(?, ?, ?, ?, ?)";
                    java.sql.PreparedStatement pst1 = c.c.prepareStatement(q1);
                    pst1.setString(1, name);
                    pst1.setString(2, age);
                    pst1.setString(3, gender);
                    pst1.setString(4, mobile);
                    pst1.setString(5, address);
                    pst1.executeUpdate();

                    // 2. Login Table Entry
                    String q2 = "insert into login (username, password, role) values(?, ?, 'Receptionist')";
                    java.sql.PreparedStatement pst2 = c.c.prepareStatement(q2);
                    pst2.setString(1, user);
                    pst2.setString(2, pass);
                    pst2.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Receptionist Registered Successfully!");
                    setVisible(false);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        // Frame Settings
        setTitle("Hospital Management System - ReceptionistRegistration");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}