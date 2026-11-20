package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Doctor extends JFrame {

    public Doctor(){
        // Image Load
        ImageIcon icon = new ImageIcon("src/icon/three.png");
        Image scaledImage = icon.getImage().getScaledInstance(1550, 800, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImage);

        JLabel background = new JLabel(finalIcon);
        setContentPane(background);
        background.setLayout(null);

        // --- Form Heading ---
        JLabel heading = new JLabel("DOCTOR REGISTRATION");
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

        // Specialization
        JLabel lblSpecialization = new JLabel("Specialization:");
        lblSpecialization.setBounds(30, 130, 200, 30);
        background.add(lblSpecialization);
        JTextField tfSpecialization= new JTextField();
        tfSpecialization.setBounds(150, 130, 300, 30);
        background.add(tfSpecialization);

        // Mobile
        JLabel lblMobile = new JLabel("Mobile:");
        lblMobile.setBounds(30, 180, 200, 30);
        background.add(lblMobile);
        JTextField tfMobile = new JTextField();
        tfMobile.setBounds(150, 180, 300, 30);
        background.add(tfMobile);

        // Experience
        JLabel lblExperience = new JLabel("Experience:");
        lblExperience.setBounds(30, 230, 200, 30);
        background.add(lblExperience);
        JTextField tfExperience = new JTextField();
        tfExperience.setBounds(150, 230, 300, 30);
        background.add(tfExperience);

        // Fees
        JLabel lblFees = new JLabel("Fees:");
        lblFees.setBounds(30, 280, 200, 30);
        background.add(lblFees);
        JTextField tfFees = new JTextField();
        tfFees.setBounds(150, 280, 300, 30);
        background.add(tfFees);

        // --- LOGIN CREDENTIALS SECTION ---

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

        // Submit Button (Position adjusted down)
        JButton btnAdd = new JButton("Add Doctor");
        btnAdd.setBounds(190, 450, 150, 40);
        btnAdd.setBackground(Color.BLACK);
        btnAdd.setForeground(Color.WHITE);
        background.add(btnAdd);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                String spec = tfSpecialization.getText();
                String mobile = tfMobile.getText();
                String exp = tfExperience.getText();
                String fees = tfFees.getText();
                String user = tfUser.getText();
                String pass = new String(pfPass.getPassword());

                // Validation
                if(user.isEmpty() || pass.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Username and Password are Required!");
                    return;
                }

                try {
                    Conn c = new Conn();

                    // 1. Doctor Table mein data insert karein
                    String q1 = "insert into doctor (name, specialization, mobile, experience, fees) values(?, ?, ?, ?, ?)";
                    java.sql.PreparedStatement pst1 = c.c.prepareStatement(q1);
                    pst1.setString(1, name);
                    pst1.setString(2, spec);
                    pst1.setString(3, mobile);
                    pst1.setString(4, exp);
                    pst1.setString(5, fees);
                    pst1.executeUpdate();

                    // 2. Login Table mein data insert karein
                    String q2 = "insert into login (username, password, role) values(?, ?, 'Doctor')";
                    java.sql.PreparedStatement pst2 = c.c.prepareStatement(q2);
                    pst2.setString(1, user);
                    pst2.setString(2, pass);
                    pst2.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Doctor Registered Successfully!");
                    setVisible(false);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        // Frame Settings
        setTitle("Hospital Management System - Doctor Registration");
        setSize(850, 650); // Height thodi badha di hai kyunki fields zyada ho gayi hain
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // EXIT ki jagah DISPOSE taaki main app band na ho
        setVisible(true);
    }
}