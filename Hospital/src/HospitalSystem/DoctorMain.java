package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorMain extends JFrame implements ActionListener {

    // 1. Saare buttons ko global define kiya taaki ActionListener inhe pehchan sake
    JButton btnAddPatient, btnViewPatient, btnAppointments, btnPrescription, btnReports, btnLogout;

    public DoctorMain() {
        setTitle("Doctor Control Panel");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // --- BACKGROUND IMAGE ---
        ImageIcon icon = new ImageIcon("src/icon/Doct.png");
        Image scaled = icon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaled));
        background.setBounds(0, 0, 1000, 700);
        background.setLayout(null);
        add(background);


        // --- HEADING ---
        JLabel heading = new JLabel("DOCTOR DASHBOARD", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 45));
        heading.setForeground(new Color(0, 102, 102));
        heading.setBounds(0, 40, 1000, 60);
        background.add(heading); // Frame ki jagah background pe add karein

        // --- BUTTONS INITIALIZATION ---

        // Add Patient
        btnAddPatient = new JButton("Add New Patient");
        btnAddPatient.setBounds(100, 200, 180, 50);
        btnAddPatient.setBackground(new Color(40,160,120));
        btnAddPatient.addActionListener(this); // Isse click hone par actionPerformed chalega
        background.add(btnAddPatient);

        // View Patient
        btnViewPatient = new JButton("View Patient");
        btnViewPatient.setBounds(300, 200, 180, 50);
        btnViewPatient.setBackground(new Color(40,160,120));
        btnViewPatient.addActionListener(this);
        background.add(btnViewPatient);


        // Logout
        btnLogout = new JButton("LOGOUT");
        btnLogout.setBounds(780, 580, 150, 45);
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(this);
        background.add(btnLogout);

        setVisible(true);
    }

    // --- SAARI FUNCTIONALITY IS EK METHOD MEIN HAI ---
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnAddPatient) {
            new Patient().setVisible(true);
        }

        else if (ae.getSource() == btnViewPatient) {
            showDataInTable("patient");
        }

        else if (ae.getSource() == btnLogout) {
            int confirm = JOptionPane.showConfirmDialog(this, "Kya aap Logout karna chahte hain?", "Logout Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
            }
            new Login().setVisible(true);
        }
    }

    // --- YE METHOD CLASS KE ANDAR PAR ACTIONPERFORMED KE BAHAR RAKHEIN ---
    private void showDataInTable(String tableName) {
        try {
            Conn c = new Conn();
            String query = "select * from " + tableName;
            java.sql.ResultSet rs = c.s.executeQuery(query);

            // Metadata: Columns ke naam nikalne ke liye
            java.sql.ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 1. Headers (Columns)
            java.util.Vector<String> columnNames = new java.util.Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i).toUpperCase());
            }

            // 2. Rows (Data)
            java.util.Vector<java.util.Vector<Object>> data = new java.util.Vector<>();
            while (rs.next()) {
                java.util.Vector<Object> row = new java.util.Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            // JTable Design
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            table.setRowHeight(25);
            table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));

            // Pop-up mein table dikhana
            JOptionPane.showMessageDialog(this, scrollPane, tableName.toUpperCase() + " DATABASE", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            // Agar table database mein nahi hogi toh ye error dikhayega
            JOptionPane.showMessageDialog(this, "Table '" + tableName + "' nahi mili ya Database Error hai!");
            e.printStackTrace();
        }
    }

}