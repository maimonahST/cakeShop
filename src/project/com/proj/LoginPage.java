package com.proj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class LoginPage extends JFrame {

    private JPanel LoginPanel;
    private JTextField EmailTextField;
    private JPasswordField PasswordField;
    private JButton LoginButton;
    private JPanel panel2;
    private JLabel donTHaveAnLabel;
    private JButton signUpButton;
    private JLabel label1;

    public static customer user;
    public static manager boss;

    LoginPage(){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        add(LoginPanel);

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //get email and password from textFields
                String email = String.valueOf(EmailTextField.getText());
                String pass = String.valueOf(PasswordField.getPassword());



                try {

                    DBConnection con=new DBConnection();
                    boss =  con.loginManager(email,pass);

                        if (boss.email != null) {
                            //display ManagerHome page
                            EventQueue.invokeLater(() -> {
                                ManagerPage c = new ManagerPage();
                                c.setVisible(true);

                            });
                            dispose();

                        }else {

                            user = con.loginCustomer(email,pass);
                        if (user.email != null) {
                            //display CustomerHome page
                            EventQueue.invokeLater(() -> {
                                CustumerHome c = new CustumerHome();
                                c.setVisible(true);

                            });
                            dispose();

                        } else {
                            JOptionPane.showMessageDialog(null, "wrong email or password ", "failed", JOptionPane.ERROR_MESSAGE);

                        }


                    }

                }catch (SQLException ex){

                    ex.printStackTrace();

                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //display SignUpPage
                EventQueue.invokeLater(() -> {
                    SignUpPage signUp = new SignUpPage();
                    signUp.setVisible(true);
                });
                dispose();
            }
        });

    }


    public static void main(String[] args) {

        //Display LoginPage
        EventQueue.invokeLater(() -> {
            LoginPage ex = new LoginPage();
            ex.setVisible(true);
        });
    }
}
