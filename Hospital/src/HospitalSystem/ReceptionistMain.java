package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistMain extends JFrame implements ActionListener {

    // Buttons updated as per your requirement
    JButton btnRegisterPatient, btnViewPatient, btnCheckDocAvailability, btnLogout;

    public ReceptionistMain() {
        setTitle("Hospital Management System - Receptionist Panel");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // --- BACKGROUND IMAGE ---
        ImageIcon icon = new ImageIcon("src/icon/Recptiost.png");
        Image scaled = icon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaled));
        background.setBounds(0, 0, 1000, 700);
        background.setLayout(null);
        add(background);

        // --- HEADING ---
        JLabel heading = new JLabel("RECEPTIONIST DASHBOARD", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 45));
        heading.setForeground(new Color(0, 51, 102));
        heading.setBounds(0, 40, 1000, 60);
        background.add(heading);

        // --- BUTTONS ---

        // 1. Register New Patient
        btnRegisterPatient = new JButton("Register New Patient");
        btnRegisterPatient.setBounds(150, 200, 250, 60);
        btnRegisterPatient.setBackground(new Color(10, 120, 220));
        btnRegisterPatient.setForeground(Color.WHITE);
        btnRegisterPatient.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnRegisterPatient.addActionListener(this);
        background.add(btnRegisterPatient);

        // 2. View All Patients
        btnViewPatient = new JButton("View Patient Records");
        btnViewPatient.setBounds(550, 200, 250, 60);
        btnViewPatient.setBackground(new Color(10, 120, 220));
        btnViewPatient.setForeground(Color.WHITE);
        btnViewPatient.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnViewPatient.addActionListener(this);
        background.add(btnViewPatient);

        // 3. Doctor Availability
        btnCheckDocAvailability = new JButton("Doctor Availability");
        btnCheckDocAvailability.setBounds(150, 320, 250, 60);
        btnCheckDocAvailability.setBackground(new Color(10, 120, 220));
        btnCheckDocAvailability.setForeground(Color.WHITE);
        btnCheckDocAvailability.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCheckDocAvailability.addActionListener(this);
        background.add(btnCheckDocAvailability);

        // 4. Logout
        btnLogout = new JButton("LOGOUT");
        btnLogout.setBounds(780, 580, 150, 45);
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogout.addActionListener(this);
        background.add(btnLogout);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnRegisterPatient) {
            new Patient().setVisible(true);
        }
        else if (ae.getSource() == btnViewPatient) {
            // "patient" table ka data dikhayega
            showDataInTable("patient");
        }
        else if (ae.getSource() == btnCheckDocAvailability) {
            // "doctor" table ka data dikhayega
            showDataInTable("doctor");
        }
        else if (ae.getSource() == btnLogout) {
            int confirm = JOptionPane.showConfirmDialog(this, "Logout from Receptionist Panel?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                 new Login().setVisible(true);
            }
        }
    }

    // --- Core Java Helper Method ---
    private void showDataInTable(String tableName) {
        try {
            Conn c = new Conn();
            String query = "select * from " + tableName;
            java.sql.ResultSet rs = c.s.executeQuery(query);

            java.sql.ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            java.util.Vector<String> columnNames = new java.util.Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i).toUpperCase());
            }

            java.util.Vector<java.util.Vector<Object>> data = new java.util.Vector<>();
            while (rs.next()) {
                java.util.Vector<Object> row = new java.util.Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            table.setRowHeight(25);
            table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(850, 450));

            JOptionPane.showMessageDialog(this, scrollPane, "RECORDS: " + tableName.toUpperCase(), JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + tableName + " data load nahi ho paya.");
            e.printStackTrace();
        }
    }
}