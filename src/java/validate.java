
//import static DatabaseOperation.getConnection;
//import static DatabaseOperation.stmtObj;
import static java.awt.Color.red;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Israel
 */

@ManagedBean(name="valid")
@RequestScoped
public class validate implements Serializable {
    private String userName;
  
   private String password;
   
    String SQL;
    Statement statement;
    ResultSet resultSet;
    Connection connection;

    public String getName() {
        return userName;
    }

 
    public String getPassword() {
        return password;
    }

    public void setName(String userName) {
        this.userName= userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
 public String validate() throws SQLException, ClassNotFoundException {
        boolean status = validd(userName, password);
        if (status) {
          DatabaseOperation dbcon = new DatabaseOperation();
            Connection con = dbcon.getConnection();
            PreparedStatement ps = con.prepareStatement("select ADMINNAME from USERTBB where ADMINNAME=?");
            ps.setString(1, userName);   
          
            ResultSet rs = ps.executeQuery();
            rs.next();
            String UserName =rs.getString(1);
            if ("mark".equals(UserName)) {
                return "newjsf1.xhtml";
            } else {
                return "index.xhtml";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username or Passowrd",
                            "Please enter correct username or Password"));
            return "index.xhtml";
        }
    }
 
    public boolean validd(String adminame, String password) {
        
        boolean check = false;
        try {

            DatabaseOperation dbcon = new DatabaseOperation();
            Connection con = dbcon.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from USERTBB where ADMINNAME=? and PASSWORD=?");
            ps.setString(1, adminame);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            check = rs.next();

        } catch (Exception e) {
            System.out.println(e);
        }
        return check;
    }
    
}
    

    
        
 
        



