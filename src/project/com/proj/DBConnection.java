package com.proj;

import javax.swing.*;
import java.sql.*;

public class DBConnection {

    public Connection connection ;
    public Statement statement;
    public ResultSet resultSet;
    public ResultSetMetaData metaData ;
    public PreparedStatement ps;
    private String pass = "Mai12345";

    private static final String url = "jdbc:mysql://localhost:3306/Java_Project";

    DBConnection(String SQL_query) throws SQLException {
        connection = DriverManager.getConnection(url, "root", pass);
        statement = connection.createStatement();
        metaData = resultSet.getMetaData();

    }

    DBConnection() throws SQLException{
        connection = DriverManager.getConnection(url, "root", pass);
    }

    manager loginManager(String email,String pass) throws SQLException {

        ps =connection.prepareStatement("Select * From Manager Where email =? AND password =?");
        ps.setString(1,email);
        ps.setString(2,pass);
        resultSet=ps.executeQuery();
        manager m = new manager();

        while (resultSet.next()) {
            m.id = (int) resultSet.getObject(1);
            m.FName = (String) resultSet.getObject(2);
            m.MName = (String) resultSet.getObject(3);
            m.LName = (String) resultSet.getObject(4);
            m.email = (String) resultSet.getObject(5);
            m.password = (String) resultSet.getObject(6);
            m.shopId = (int) resultSet.getObject(7);

        }
        return m;
    }

    customer loginCustomer(String email,String pass) throws SQLException {

        ps =connection.prepareStatement("Select * From Customer Where Email =? AND Cust_Password =?");
        ps.setString(1,email);
        ps.setString(2,pass);
        resultSet=ps.executeQuery();

        customer user = new customer();
        while (resultSet.next()) {
            user.id = (Integer) resultSet.getObject(1);
            user.name = (String) resultSet.getObject(2);
            user.phone_Num = (String) resultSet.getObject(3);
            user.email = (String) resultSet.getObject(4);
            user.password = (String) resultSet.getObject(5);
            user.address = (String) resultSet.getObject(6);

            resultSet.next();
        }
        return user;
    }

    int signupUser (customer newUser) throws SQLException {
        ps = connection.prepareStatement("INSERT INTO Java_Project.Customer(Username,Phone_no,Email,Cust_Password,address) values(?, ? , ? , ? , ? )");
        ps.setString(1,newUser.name);
        ps.setString(2,newUser.phone_Num);
        ps.setString(3,newUser.email);
        ps.setString(4,newUser.password);
        ps.setString(5,newUser.address);
        return ps.executeUpdate();
    }

    int update_CName(String UserName, int id) throws SQLException{

        ps= connection.prepareStatement("update Customer set Username =? where cust_ID = ?");
        ps.setString(1,UserName);
        ps.setString(2, String.valueOf(id));

            int i = ps.executeUpdate();
            connection.close();
            return i;

    }

    int update_phoneNum (String phone, int id) throws SQLException{
        ps = connection.prepareStatement("update Customer set Phone_no =? where cust_ID = ?");
        ps.setString(1,phone);
        ps.setString(2, String.valueOf(id));
        int i = ps.executeUpdate();
        connection.close();
        return i;
    }

    int update_email (String email, int id) throws SQLException{
        ps = connection.prepareStatement("update Customer set Email =? where cust_ID = ?");
        ps.setString(1,email);
        ps.setString(2, String.valueOf(id));
        int i = ps.executeUpdate();
        connection.close();
        return i;
    }

    int update_pass (String password, int id) throws SQLException{
        ps = connection.prepareStatement("update Customer set Cust_Password =? where cust_ID = ?");
        ps.setString(1,password);
        ps.setString(2, String.valueOf(id));
        int i = ps.executeUpdate();
        connection.close();
        return i;
    }

    int update_pass_Manger (String password, int id) throws SQLException{
        ps = connection.prepareStatement("update Manager set password =? where Mng_ID = ?");
        ps.setString(1,password);
        ps.setString(2, String.valueOf(id));
        int i = ps.executeUpdate();
        connection.close();
        return i;
    }

    int insertPayment(int mng) throws SQLException{
        System.out.println("Payment price  "+ CustumerHome.basketTotLabel.getText());
        ps = connection.prepareStatement("INSERT INTO  Java_Project.payment (payment_date, amount, Mng_id)values (CURRENT_TIMESTAMP,?,?)");
        ps.setFloat(1,Float.parseFloat(CustumerHome.basketTotLabel.getText()));
        ps.setInt(2, mng);

        int f = ps.executeUpdate();
        System.out.println("Payment "+f);
        return f;
    }

    boolean insertOrder(String states,customer cust ,String loc) throws SQLException{

        int mngID = getMngID(cust);
        int shopID = getshopID(cust);

        if (insertPayment(mngID) == 1) {
            int paymentID = getPaymentID();

            ps = connection.prepareStatement("INSERT INTO  Orders(order_states,order_date,Mng_id, cust_id, payment_id , shop_id,Shop_Location) values(?,CURRENT_TIMESTAMP,?,?,?,?,?);");
            ps.setString(1, states);
            ps.setInt(2, mngID);
            ps.setInt(3, cust.id);
            ps.setInt(4, paymentID);
            ps.setInt(5, shopID);
            ps.setString(6, loc);

            boolean i = ps.execute();
            connection.close();
            return i;
        }else {
            System.out.println("error");
            return false;
        }

    }

    DefaultComboBoxModel getCakeShops(customer cust ) throws SQLException {

        ps = connection.prepareStatement("select Location from CakeShop_Location,CakeShop where Shop_L_id = Shop_id and City = '"+cust.address+"';");
        resultSet = ps.executeQuery();
        DefaultComboBoxModel dm=new DefaultComboBoxModel();

        while (resultSet.next()){
            dm.addElement((String)resultSet.getObject(1) );
        }
        connection.close();
        return dm;
    }

    int getMngID(customer cust) throws SQLException {

        ps = connection.prepareStatement("select manager.Mng_ID from manager , CakeShop  where CakeShop_M = CakeShop.Shop_id and CakeShop.City = '"+cust.address + "' ");
        resultSet = ps.executeQuery();
        int mng = 0;
        while(resultSet.next()){
        mng = (int) resultSet.getObject(1);
        }

        System.out.println("mng "+mng);
         return mng;
    }

    int getshopID(customer cust) throws SQLException {

        ps = connection.prepareStatement("select Shop_id from CakeShop where City = '"+cust.address+"'");
        resultSet = ps.executeQuery();
        int sh = 0;
        while(resultSet.next()){
            sh = (int) resultSet.getObject(1);

        }
        return sh;
    }

    int getshopID(String city) throws SQLException {

        ps = connection.prepareStatement("select Shop_id from CakeShop where City = '"+city+"'");
        resultSet = ps.executeQuery();
        int sh = 0;
        while(resultSet.next()){
            sh = (int) resultSet.getObject(1);

        }
        return sh;
    }

    int getPaymentID() throws SQLException {
    ps = connection.prepareStatement("select payment_id from payment ORDER BY  payment_id DESC LIMIT 1");
    resultSet = ps.executeQuery();
    int p = 0;
    while(resultSet.next()){
        p = (int) resultSet.getObject(1);

    }
    System.out.println("p id " + p );
    return p;
    }

    int getOrderID() throws SQLException {
        ps = connection.prepareStatement("select Order_id from Orders ORDER BY  Order_id DESC LIMIT 1");
        resultSet = ps.executeQuery();
        int o= 0;
        while(resultSet.next()){
            o = (int) resultSet.getObject(1);

        }
        System.out.println("Order id " + o );
        return o;
    }

    int insertOrderItem(Order_item item ) throws SQLException{

            String s = "insert into Order_Item(Quantity,Total_price,product_ID, order_id) values( " + item.Quantity + " ," +  item.totalP + " ," + item.pid + " ," + getOrderID() + ")";
            statement = connection.createStatement();

            int row = statement.executeUpdate(s);
            connection.close();

            return row;
        }

    int insertRate(Rating s, customer c ) throws SQLException{

        String Q = "Insert into Rating(date_recorded,score, feedback, cust_id, pro_id) values (CURRENT_TIMESTAMP,"+ s.score + ",'"+ s.feedback + "', "+ c.id +","+s.pid+")";
        statement = connection.createStatement();
        int row = statement.executeUpdate(Q);
        connection.close();

        return row;
    }

    int insertShop( manager boss, String Location  ) throws SQLException{


        String Q = "insert into CakeShop_Location(Shop_L_id, Location) values (" + boss.shopId + " ,'" + Location + "')";
        statement = connection.createStatement();
        int row = statement.executeUpdate(Q);
        connection.close();

        return row;
    }

    Float getTotalCost(int PaymentID) throws SQLException{
        System.out.println( "PID "+PaymentID);
        ps = connection.prepareStatement("Select amount from payment where payment_ID = ?");
        ps.setInt(1,PaymentID);

        resultSet = ps.executeQuery();
        Float amount = 0F ;

        while (resultSet.next()){
            amount = (Float) resultSet.getObject(1);
        }

        System.out.println(amount);
        return amount;
    }

    void setRateAvr (int proId) throws SQLException {
        ps = connection.prepareStatement("select avg(score) from Rating where pro_id = ?");
        ps.setInt(1,proId);

        int score = 0 ;
        resultSet = ps.executeQuery();
        while (resultSet.next()){
            score = Math.round(resultSet.getFloat(1));
        }

        for (JLabel s: ManagerPage.stars ) {
            s.setIcon(new ImageIcon("src/project/star.png"));
        }

        for (int i = 0; i < score; i++) {
            ManagerPage.stars[i].setIcon(new ImageIcon("src/project/star (1).png"));
        }

    }

    String getShopName (manager b) throws SQLException {
        ps = connection.prepareStatement("select City  from CakeShop where Shop_id = ? ");
        ps.setInt(1, b.shopId);
        resultSet = ps.executeQuery();
        String city = "";
        while (resultSet.next()){
            city = (String) resultSet.getObject(1);
        }
        return city;
    }

    int update_State (String state, int id) throws SQLException{
        ps = connection.prepareStatement("update Orders set order_states =? where order_id = ?");
        ps.setString(1,state);
        ps.setInt(2, id);
        int i = ps.executeUpdate();
        connection.close();
        return i;
    }

    int updateCity (String city, int id) throws SQLException{
        ps = connection.prepareStatement("update Customer set address =? where cust_ID = ?");
        ps.setString(1,city);
        ps.setInt(2,id);
        int i = ps.executeUpdate();
        connection.close();
        return i;
    }

    void closeDB() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

    }





