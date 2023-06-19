import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Product {
    private JTextField txtname;
    private JTextField txtprice;
    private JTextField txtmass;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JPanel Main;
    private JScrollPane table_1;
    private JTextField txtType;
    private JTextField txttarrif;

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("product");
        frame.setContentPane(new Product().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
Connection con;
    PreparedStatement pst;


    public Connection connection() throws SQLException{


        System.out.println("Connected to Database.");
           con = DriverManager.getConnection("jdbc:mysql://localhost/practise", "root", "1234");
            System.out.println("Connected to Database.");
            return con;




    }
    void table_load(){
        try{

            pst = con.prepareStatement("select * from product");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
    public Product() throws SQLException {
        connection();
        table_load();
        System.out.println("Success");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,price,mass,tarrif,type,resultsr;
                Integer result;

                name = txtname.getText();
                price = txtprice.getText();
                mass = txtmass.getText();
                tarrif = txttarrif.getText();
                type = txtType.getText();
                System.out.println(tarrif instanceof String);
                result = Integer.parseInt(mass)*Integer.parseInt(price)+Integer.parseInt(tarrif);
                resultsr = String.valueOf(result);


                try{
                    pst = con.prepareStatement("insert into product (productname,productprice,productmass,tarrif,producttype,productresprice)values(?,?,?,?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, mass);
                    pst.setString(4,tarrif);
                    pst.setString(5,type);
                    pst.setString(6,resultsr);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record added" );
                    txtname.setText("");
                    txtprice.setText("");
                    txtmass.setText("");
                    txttarrif.setText("");
                    txtType.setText("");
                    txtname.requestFocus();
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String prodid = txtid.getText();
                    pst = con.prepareStatement("SELECT productname,productprice,productmass,producttype,tarrif from product where idproduct=?");
                    pst.setString(1, prodid);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next() == true) {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String mass= rs.getString(3);
                        String type = rs.getString(4);
                        String tarrif = rs.getString(5);
                        txtname.setText(name);
                        txtprice.setText(price);
                        txtmass.setText(mass);
                        txtType.setText(type);
                        txttarrif.setText(tarrif);
                    } else {
                        txtname.setText("");
                        txtprice.setText("");
                        txtmass.setText("");
                        txtType.setText("");
                        txttarrif.setText("");
                        JOptionPane.showMessageDialog(null,"invalid number");
                    }
                }
                catch (SQLException ex){
                ex.printStackTrace();
                }
            }

        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,price,mass,tarrif = null,type=null,id;

                name = txtname.getText();
                price = txtprice.getText();
                mass = txtmass.getText();
                id = txtid.getText();
                try{
                    pst = con.prepareStatement("update product set productname=?,productprice=?,productmass=?,tarrif=?,producttype =? where idproduct=?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, mass);
                    pst.setString(4, tarrif);
                    pst.setString(5,type);
                    pst.setString(6,id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record updated");
                    table_load();

                    txtname.setText("");
                    txtprice.setText("");
                    txtmass.setText("");
                    txttarrif.setText("");
                    txtType.setText("");
                    txtid.setText("");

                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id = txtid.getText();

                try {
                    pst= con.prepareStatement("delete from product where idproduct = ?");
                    pst.setString(1,id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record deleted");
                    table_load();
                    txtname.setText("");
                    txtmass.setText("");
                    txtprice.setText("");
                    txtid.setText("");
                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}
