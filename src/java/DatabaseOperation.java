
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;
 

 
public class DatabaseOperation {
 
    public static Statement stmt;
    public static Connection conn;
    public static ResultSet resultSet;
    public static PreparedStatement pstmt;
 
    public static Connection getConnection(){  
        try{  
            Class.forName("oracle.jdbc.driver.OracleDriver");     
            String db_url ="jdbc:oracle:thin:@localhost:1521:xe",
                    db_userName = "GENET",
                    db_password = "32is65ra98el!L";
            conn = DriverManager.getConnection(db_url,db_userName,db_password);
            if (conn != null) {
          System.out.println("Connected database successfully...");
       } else {
           System.out.println("Failed to connect to Oracle DB");
       }
        } catch(Exception sqlException) {  
            sqlException.printStackTrace();
        }  
        return conn;
    }
 
    public static ArrayList getCustomerListFromDB() {
        ArrayList customersList = new ArrayList();  
        try {
            stmt = getConnection().createStatement();    
            resultSet = stmt.executeQuery("select * from CUSTOMER_INFO");    
            while(resultSet.next()) {  
                CustomerBean stu = new CustomerBean(); 
                
                stu.setId(resultSet.getInt("customer_id"));  
                stu.setName(resultSet.getString("customer_name"));  
                stu.setPhone(resultSet.getInt("customer_phone"));  
                stu.setDestination(resultSet.getString("customer_destination"));  
                stu.setStart(resultSet.getString("customer_start"));  
                stu.setFee(resultSet.getInt("customer_fee"));  
                customersList.add(stu);  
            }   
            System.out.println("Total Records: " + customersList.size());
            conn.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return customersList;
    }
 
    public static String saveCustomerDetailsInDB(CustomerBean saveCustomer) {
        int saveResult = 0;
        String Result = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into CUSTOMER_INFO (customer_name, customer_phone, customer_destination, customer_start, customer_fee) values (?, ?, ?, ?, ?)" + " ORDER BY first ASC");         
            pstmt.setString(1, saveCustomer.getName());
            pstmt.setInt(2, saveCustomer.getPhone());
            pstmt.setString(3, saveCustomer.getDestination());
            pstmt.setString(4, saveCustomer.getStart());
            pstmt.setInt(5, saveCustomer.getFee());
            saveResult = pstmt.executeUpdate();
            conn.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            Result = "newjsf1.xhtml";
        } else {
            Result = "response.xhtml";
        }
        return Result;
    }
 
    public static String editCustomerRecordInDB(int customerId) {
       CustomerBean editRecord = null;
        System.out.println("editCustomerRecordInDB() : Customer Id: " + customerId);
 
       
        Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmt = getConnection().createStatement();    
            resultSet = stmt.executeQuery("select * from CUSTOMER_INFO where customer_id = " + customerId);    
            if(resultSet != null) {
                resultSet.next();
                editRecord = new CustomerBean(); 
                editRecord.setId(resultSet.getInt("customer_id"));
                editRecord.setName(resultSet.getString("customer_name"));
                editRecord.setPhone(resultSet.getInt("customer_phone"));
                editRecord.setStart(resultSet.getString("customer_start"));
                editRecord.setFee(resultSet.getInt("customer_fee"));
                editRecord.setDestination(resultSet.getString("customer_destination")); 
            }
            sessionMap.put("editRecordObj", editRecord);
            conn.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/newjsf.xhtml";
    }
 
    public static String updateCustomerDetailsInDB(CustomerBean update) {
        try {
            pstmt = getConnection().prepareStatement("update CUSTOMER_INFO set customer_name=?, customer_phone=?, customer_destination=?,customer_start=?, customer_fee=? where customer_id=?");    
            pstmt.setString(1,update.getName());  
            pstmt.setInt(2,update.getPhone());  
            pstmt.setString(3,update.getDestination());  
            pstmt.setString(4,update.getStart());  
            pstmt.setInt(5,update.getFee());  
            pstmt.setInt(6,update.getId());  
            pstmt.executeUpdate();
            conn.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/newjsf1.xhtml";
    }
 
    public static String deleteCustomerRecordInDB(int customerId){
        System.out.println("deleteCustomerRecordInDB() : Customer Id: " + customerId);
        try {
            pstmt = getConnection().prepareStatement("delete from CUSTOMER_INFO where customer_id = " + customerId);  
            pstmt.executeUpdate();  
            conn.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/newjsf1.xhtml";
    }

   
    
}