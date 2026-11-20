package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class Patient extends JFrame {

    public Patient(){
        // Image Load
        ImageIcon icon = new ImageIcon("src/icon/second.png");
        Image scaledImage = icon.getImage().getScaledInstance(1550, 800, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImage);

        JLabel background = new JLabel(finalIcon);
        setContentPane(background);
        background.setLayout(null);

        // --- Form Heading ---
        JLabel heading = new JLabel("PATIENT REGISTRATION");
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

        // Gender
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

        // Disease
        JLabel lblDisease = new JLabel("Disease:");
        lblDisease.setBounds(30, 330, 200, 30);
        background.add(lblDisease);
        JTextField tfDisease = new JTextField();
        tfDisease.setBounds(150, 330, 300, 30);
        background.add(tfDisease);

        // Appointment Date
        JLabel lblDate = new JLabel("Appt. Date:");
        lblDate.setBounds(30, 380, 200, 30);
        background.add(lblDate);
        JTextField tfDate = new JTextField("DD/MM/YYYY");
        tfDate.setBounds(150, 380, 300, 30);
        background.add(tfDate);

        // --- Login Credentials Section ---

        // Username
        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(30, 430, 200, 30);
        background.add(lblUser);
        JTextField tfUser = new JTextField();
        tfUser.setBounds(150, 430, 300, 30);
        background.add(tfUser);

        // Password
        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(30, 480, 200, 30);
        background.add(lblPass);
        JPasswordField pfPass = new JPasswordField();
        pfPass.setBounds(150, 480, 300, 30);
        background.add(pfPass);

        // Submit Button
        JButton btnAdd = new JButton("Add Patient");
        btnAdd.setBounds(200, 530, 150, 40); // Y position adjust ki hai
        btnAdd.setBackground(Color.BLACK);
        btnAdd.setForeground(Color.WHITE);
        background.add(btnAdd);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Data fetch karna
                String name = tfName.getText();
                String ageStr = tfAge.getText();
                String gender = (String) cbGender.getSelectedItem();
                String mobile = tfMobile.getText();
                String address = tfAddress.getText();
                String disease = tfDisease.getText();
                String admit_date = tfDate.getText();
                String user = tfUser.getText();
                String pass = new String(pfPass.getPassword());

                // Chhota sa Validation
                if(user.isEmpty() || pass.isEmpty() || name.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill all mandatory fields!");
                    return;
                }

                try {
                    Conn c = new Conn();

                    // 1. Patient Table mein Insert
                    String q1 = "insert into patient (name, age, gender, mobile, address, disease, admit_date) values(?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst1 = c.c.prepareStatement(q1);
                    pst1.setString(1, name);
                    pst1.setString(2, ageStr);
                    pst1.setString(3, gender);
                    pst1.setString(4, mobile);
                    pst1.setString(5, address);
                    pst1.setString(6, disease);
                    pst1.setString(7, admit_date);
                    pst1.executeUpdate();

                    // 2. Login Table mein Insert (Taki ye patient login kar sake)
                    String q2 = "insert into login (username, password, role) values(?, ?, 'Patient')";
                    PreparedStatement pst2 = c.c.prepareStatement(q2);
                    pst2.setString(1, user);
                    pst2.setString(2, pass);
                    pst2.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Patient Registered Successfully!");
                    setVisible(false);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database Error!");
                }
            }
        });

        // Frame Settings
        setTitle("Hospital Management System - Patient Registration");
        setSize(850, 650); // Height ko thoda badhaya gaya hai
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}