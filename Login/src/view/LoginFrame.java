/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import login.DataBaseConector;

/**
 *
 * @author denis
 */
public class LoginFrame extends JFrame {

    private static final String TITLE = "Login";
    private DataBaseConector dbconector;
    private JButton loginButton;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    
    private static void BorderLayout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public LoginFrame() {
        super(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        addevents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(initLoginPanel(), BorderLayout.CENTER);
        mainPanel.add(new JPanel(), BorderLayout.LINE_START);
        mainPanel.add(new JPanel(), BorderLayout.LINE_END);
        mainPanel.add(new JPanel(), BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JPanel initLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(4, 1, 10, 20));
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.lightGray);
        JLabel loginLabel = new JLabel("Login         ");
        loginLabel.setFont(new java.awt.Font("Arial", 10, 36));
        loginPanel.add(loginLabel);
        usernameTextField = new JTextField("User name");
        loginPanel.add(usernameTextField);
        passwordTextField = new JPasswordField("password");
        loginPanel.add(passwordTextField);
        loginPanel.add(loginButton);
        return loginPanel;
    }

    private void addevents() {
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dbconector = new DataBaseConector();
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                try {
                    if (validate(username, password)) {
                        JOptionPane.showMessageDialog(rootPane, "ha ingresado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "el nombre de usuario o la contraseña son incorrectas");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                dbconector.closeConection();

            }
        });
    }
    public boolean validate(String username, String password) throws SQLException {
        boolean res = false;
        DataBaseConector x = new DataBaseConector();
        x.connect();
        ResultSet rs = x.search(username, password);
        try {
            while (rs.next()) {
                //imprimimos todos los datos contenidos en la tabla
                String usernameExpectation = rs.getString(1);
                String passwordExpectation = rs.getString(2);
                if (usernameExpectation.equals(username) && passwordExpectation.equals(password)) {
                    res = true;
                }
            }
        } catch (Exception e) {
            System.out.println("problem with print data base");
        }
        x.closeConection();
        return res;
    }

    public static void main(String[] args) {
        JFrame loginFrame = new LoginFrame();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                loginFrame.pack();
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);
            }
        });
    }

}
