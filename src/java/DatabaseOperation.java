
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
 
import javax.faces.context.FacesContext;
 

 
public class DatabaseOperation {
 
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
 
    public static Connection getConnection(){  
        try{  
            Class.forName("oracle.jdbc.driver.OracleDriver");     
            String db_url ="jdbc:oracle:thin:@localhost:1521:xe",
                    db_userName = "GENET",
                    db_password = "32is65ra98el!L";
            connObj = DriverManager.getConnection(db_url,db_userName,db_password);
            if (connObj != null) {
          System.out.println("Connected database successfully...");
       } else {
           System.out.println("nFailed to connect to Oracle DB");
       }
        } catch(Exception sqlException) {  
            sqlException.printStackTrace();
        }  
        return connObj;
    }
 
    public static ArrayList getCustomerListFromDB() {
        ArrayList customersList = new ArrayList();  
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from CUSTOMER_INFO");    
            while(resultSetObj.next()) {  
                CustomerBean stuObj = new CustomerBean(); 
                stuObj.setId(resultSetObj.getInt("customer_id"));  
                stuObj.setName(resultSetObj.getString("customer_name"));  
                stuObj.setPhone(resultSetObj.getInt("customer_phone"));  
                stuObj.setDestination(resultSetObj.getString("customer_destination"));  
                stuObj.setStart(resultSetObj.getString("customer_start"));  
                stuObj.setFee(resultSetObj.getInt("customer_fee"));  
                customersList.add(stuObj);  
            }   
            System.out.println("Total Records Fetched: " + customersList.size());
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        } 
        return customersList;
    }
 
    public static String saveCustomerDetailsInDB(CustomerBean newCustomerObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {      
            pstmt = getConnection().prepareStatement("insert into CUSTOMER_INFO (customer_name, customer_phone, customer_destination, customer_start, customer_fee) values (?, ?, ?, ?, ?)");         
            pstmt.setString(1, newCustomerObj.getName());
            pstmt.setInt(2, newCustomerObj.getPhone());
            pstmt.setString(3, newCustomerObj.getDestination());
            pstmt.setString(4, newCustomerObj.getStart());
            pstmt.setInt(5, newCustomerObj.getFee());
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        if(saveResult !=0) {
            navigationResult = "newjsf1.xhtml?faces-redirect=true";
        } else {
            navigationResult = "response.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
 
    public static String editCustomerRecordInDB(int customerId) {
       CustomerBean editRecord = null;
        System.out.println("editCustomerRecordInDB() : Customer Id: " + customerId);
 
        /* Setting The Particular Student Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
 
        try {
            stmtObj = getConnection().createStatement();    
            resultSetObj = stmtObj.executeQuery("select * from CUSTOMER_INFO where customer_id = " + customerId);    
            if(resultSetObj != null) {
                resultSetObj.next();
                editRecord = new CustomerBean(); 
                editRecord.setId(resultSetObj.getInt("customer_id"));
                editRecord.setName(resultSetObj.getString("customer_name"));
                editRecord.setPhone(resultSetObj.getInt("customer_phone"));
                editRecord.setStart(resultSetObj.getString("customer_start"));
                editRecord.setFee(resultSetObj.getInt("customer_fee"));
                editRecord.setDestination(resultSetObj.getString("customer_destination")); 
            }
            sessionMapObj.put("editRecordObj", editRecord);
            connObj.close();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/newjsf.xhtml?faces-redirect=true";
    }
 
    public static String updateCustomerDetailsInDB(CustomerBean updateCustomerObj) {
        try {
            pstmt = getConnection().prepareStatement("update CUSTOMER_INFO set customer_name=?, customer_phone=?, customer_destination=?,customer_start=?, customer_fee=? where customer_id=?");    
            pstmt.setString(1,updateCustomerObj.getName());  
            pstmt.setInt(2,updateCustomerObj.getPhone());  
            pstmt.setString(3,updateCustomerObj.getDestination());  
            pstmt.setString(4,updateCustomerObj.getStart());  
            pstmt.setInt(5,updateCustomerObj.getFee());  
            pstmt.setInt(6,updateCustomerObj.getId());  
            pstmt.executeUpdate();
            connObj.close();            
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/newjsf1.xhtml?faces-redirect=true";
    }
 
    public static String deleteCustomerRecordInDB(int customerId){
        System.out.println("deleteCustomerRecordInDB() : Customer Id: " + customerId);
        try {
            pstmt = getConnection().prepareStatement("delete from CUSTOMER_INFO where customer_id = " + customerId);  
            pstmt.executeUpdate();  
            connObj.close();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/newjsf1.xhtml?faces-redirect=true";
    }

   
    
}