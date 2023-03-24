package com.proj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUpPage extends JFrame{
    private JPanel signUPMainPanel;
    private JTextField UsernameTextField;
    private JTextField emailTextField;
    private JTextField phoneNumTextField;
    private JPasswordField pass1TextField;
    private JPasswordField pass2TextField;
    private JLabel retypePasswordLabel;
    private JLabel image;
    private JButton signUpButton;
    private JComboBox comboBox1;
    private JButton button1;

    SignUpPage(){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        add(signUPMainPanel);

       // String username = "" ;
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // username = UsernameTextField.getText();

                String pas1 = String.valueOf(pass1TextField.getPassword());
                String pas2 = String.valueOf(pass2TextField.getPassword());

                if ( Methods.isValidEmail(emailTextField.getText()) && Methods.isValidPass(pas1) && pas1.equals(pas2) && Methods.isValidPhoneNum(phoneNumTextField.getText())) {
                    customer newUser = new customer();
                    newUser.name = UsernameTextField.getText();
                    newUser.email = emailTextField.getText();
                    newUser.phone_Num = phoneNumTextField.getText();
                    newUser.password = pas1 ;
                    newUser.address = (String) comboBox1.getSelectedItem();

                    try {
                        DBConnection con=new DBConnection();
                        if (con.signupUser(newUser) == 1) {
                            JOptionPane.showMessageDialog(null, "Sign-up successfully ", "Successful", JOptionPane.INFORMATION_MESSAGE);

                            //back to LoginPage
                            EventQueue.invokeLater(() -> {
                                LoginPage ex = new LoginPage();
                                ex.setVisible(true);
                            });

                            dispose();

                        }else {
                            JOptionPane.showMessageDialog(null, "Sign-up unssuccessfully, \n Please try again", "Unsuccessful sign-up", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
                   else
                       JOptionPane.showMessageDialog(null, "Your email or password is invalid, " +
                               "\nPlease try again with valid information\n Password should be > 6","Invalid Entry",JOptionPane.ERROR_MESSAGE);


            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                EventQueue.invokeLater(() -> {
                    LoginPage ex = new LoginPage();
                    ex.setVisible(true);
                });
                dispose();
            }
        });
    }

}
