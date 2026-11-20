package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame implements ActionListener {

    // Define buttons
    JButton patientBtn, doctorBtn, receptionistBtn;

    public SignUp() {
        // Basic Frame Setup
        setTitle("Hospital Management System - Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows (Header + 3 Buttons)

        // Header Label
        JLabel header = new JLabel("Select Registration Type", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        add(header);

        // Initialize and add buttons
        patientBtn = new JButton("Patient Registration");
        doctorBtn = new JButton("Doctor Registration");
        receptionistBtn = new JButton("Receptionist Registration");

        // Add Listeners
        patientBtn.addActionListener(this);
        doctorBtn.addActionListener(this);
        receptionistBtn.addActionListener(this);

        // Add to Frame
        add(patientBtn);
        add(doctorBtn);
        add(receptionistBtn);

        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == patientBtn) {
            new Patient().setVisible(true);
        } else if (e.getSource() == doctorBtn) {
            new Doctor().setVisible(true);
        } else if (e.getSource() == receptionistBtn) {
           new ReceptionistRegistration().setVisible(true);
        }
    }

}