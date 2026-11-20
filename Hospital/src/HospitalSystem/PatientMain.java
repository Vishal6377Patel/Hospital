package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientMain extends JFrame implements ActionListener {

    // Ab sirf do buttons bachenge
    JButton btnViewDoctors, btnLogout;

    public PatientMain() {
        setTitle("Patient Personal Portal");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // --- BACKGROUND IMAGE ---
        ImageIcon icon = new ImageIcon("src/icon/Patient.png");
        Image scaled = icon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaled));
        background.setBounds(0, 0, 1000, 700);
        background.setLayout(null);
        add(background);

        // --- HEADING ---
        JLabel heading = new JLabel("PATIENT DASHBOARD", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 45));
        heading.setForeground(new Color(0, 102, 102));
        heading.setBounds(0, 40, 1000, 60);
        background.add(heading);

        // --- BUTTONS ---

        // 1. View Doctors Button (Patient dekh sake ki kaun-kaun se doctors available hain)
        btnViewDoctors = new JButton("VIEW DOCTORS");
        btnViewDoctors.setBounds(400, 250, 200, 60); // Center mein rakha hai
        btnViewDoctors.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnViewDoctors.setBackground(new Color(0, 153, 153));
        btnViewDoctors.setForeground(Color.WHITE);
        btnViewDoctors.addActionListener(this);
        background.add(btnViewDoctors);

        // 2. Logout Button
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
        if (ae.getSource() == btnViewDoctors) {
            // "doctor" table ka data dikhayega
            showDataInTable("doctor");
        }
        else if (ae.getSource() == btnLogout) {
            int confirm = JOptionPane.showConfirmDialog(this, "Kya aap Logout karna chahte hain?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose(); // Frame close karein
            }
            new Login().setVisible(true);
        }
    }

    // --- Core Java Helper Method (Database connection ke liye) ---
    private void showDataInTable(String tableName) {
        try {
            Conn c = new Conn();
            String query = "select * from " + tableName;
            java.sql.ResultSet rs = c.s.executeQuery(query);

            java.sql.ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Headers
            java.util.Vector<String> columnNames = new java.util.Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i).toUpperCase());
            }

            // Data
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
            scrollPane.setPreferredSize(new Dimension(850, 400));

            JOptionPane.showMessageDialog(this, scrollPane, "AVAILABLE DOCTORS", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Doctors ki list load nahi ho saki!");
            e.printStackTrace();
        }
    }
}