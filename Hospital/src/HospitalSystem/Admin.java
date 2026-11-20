package HospitalSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin extends JFrame implements ActionListener {

    JButton addDoc, viewDoc, addPat, viewPat, addRec, viewRec, btnLogout;

    public Admin() {
        setTitle("Admin Dashboard - LifeLine Hospital");
        setLayout(null);

        // --- Hospital Related Background Color (Light Medical Blue) ---
        getContentPane().setBackground(new Color(225, 245, 254));
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Main Heading with Icon-like feel
        JLabel heading = new JLabel("HOSPITAL ADMINISTRATION", JLabel.CENTER);
        heading.setFont(new Font("Verdana", Font.BOLD, 32));
        heading.setBounds(0, 30, 900, 50);
        heading.setForeground(new Color(1, 87, 155)); // Deep Blue
        add(heading);

        // Helper method to style buttons (Professional Blue Theme)
        Font btnFont = new Font("Arial", Font.BOLD, 14);
        Color btnColor = new Color(2, 119, 189); // Medical Blue
        Color textColor = Color.WHITE;

        // --- DOCTORS SECTION ---
        JLabel lblDoc = new JLabel("DOCTOR MANAGEMENT");
        lblDoc.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDoc.setBounds(100, 120, 300, 30);
        lblDoc.setForeground(new Color(55, 71, 79));
        add(lblDoc);

        addDoc = createStyledButton("Add New Doctor", 100, 160, btnColor, textColor, btnFont);
        viewDoc = createStyledButton("View All Doctors", 270, 160, btnColor, textColor, btnFont);

        // --- PATIENTS SECTION ---
        JLabel lblPat = new JLabel("PATIENT MANAGEMENT");
        lblPat.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPat.setBounds(100, 240, 300, 30);
        lblPat.setForeground(new Color(55, 71, 79));
        add(lblPat);

        addPat = createStyledButton("Add New Patient", 100, 280, btnColor, textColor, btnFont);
        viewPat = createStyledButton("View All Patients", 270, 280, btnColor, textColor, btnFont);

        // --- RECEPTIONIST SECTION ---
        JLabel lblRec = new JLabel(" RECEPTIONIST");
        lblRec.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblRec.setBounds(100, 360, 300, 30);
        lblRec.setForeground(new Color(55, 71, 79));
        add(lblRec);

        addRec = createStyledButton("Add Receptionist", 100, 400, btnColor, textColor, btnFont);
        viewRec = createStyledButton("View Receptionist ", 270, 400, btnColor, textColor, btnFont);

        // --- LOGOUT ---
        btnLogout = new JButton("LOGOUT");
        btnLogout.setBounds(650, 480, 150, 45);
        btnLogout.setBackground(new Color(183, 28, 28)); // Red for Logout
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(this);
        add(btnLogout);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Button style karne ke liye ek chota function
    private JButton createStyledButton(String text, int x, int y, Color bg, Color fg, Font font) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 160, 45);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(font);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.addActionListener(this);
        add(btn);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addDoc) {
            new Doctor().setVisible(true);
        }
        else if (ae.getSource() == viewDoc) {
            showDataInTable("doctor");
        }
        else if (ae.getSource() == addPat) {
            new Patient().setVisible(true);
        }
        else if (ae.getSource() == viewPat) {
            showDataInTable("patient");
        }
        else if (ae.getSource() == addRec) {
            new ReceptionistRegistration().setVisible(true);
        }
        else if (ae.getSource() == viewRec) {
            showDataInTable("receptionist");
        }
        else if (ae.getSource() == btnLogout) {
            int a = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (a == JOptionPane.YES_OPTION) {
                this.dispose();
            }
            new Login().setVisible(true);
        }
    }

    // Ye helper method aap isi class ke andar niche add kar dein
    private void showDataInTable(String tableName) {
        try {
            Conn c = new Conn();
            String query = "select * from " + tableName;
            java.sql.ResultSet rs = c.s.executeQuery(query);

            // Metadata se columns nikalna
            java.sql.ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Column Names
            java.util.Vector<String> columnNames = new java.util.Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Row Data
            java.util.Vector<java.util.Vector<Object>> data = new java.util.Vector<>();
            while (rs.next()) {
                java.util.Vector<Object> row = new java.util.Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            // JTable banana
            JTable table = new JTable(new javax.swing.table.DefaultTableModel(data, columnNames));
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));

            // Bina nayi class ke sidha pop-up mein table dikhana
            JOptionPane.showMessageDialog(this, scrollPane, "Database: " + tableName.toUpperCase(), JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}