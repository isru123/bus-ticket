
 
import java.util.ArrayList;
 
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
//import com.jsf.crud.db.operations.DatabaseOperation;
 
@ManagedBean
@RequestScoped
public class CustomerBean {
 
    private int id;  
    private String name;  
    private int phone;  
    private String start; 
    private String destination;  
    private int fee;
 
    public ArrayList customersListFromDB;
 
    public int getId() {
        return id;  
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public int getPhone() {
        return phone;
    }
 
    public void setPhone(int phone) {
        this.phone = phone;
    }
 
    public String getStart() {
        return start;
    }
 
    public void setStart(String start) {
        this.start = start;
    }
    
    public String getDestination() {
        return destination;
    }
 
    public void setDestination(String destination) {
        this.destination = destination;
    }
 
    public int getFee() {
        return fee;
    }
 
    public void setFee(int fee) {
        this.fee = fee;
    }  
     
    @PostConstruct
    public void init() {
        customersListFromDB = DatabaseOperation.getCustomerListFromDB();
    }
 
    public ArrayList customersList() {
        return customersListFromDB;
    }
     
    public String saveCustomerDetails(CustomerBean newCustomerObj) {
        return DatabaseOperation.saveCustomerDetailsInDB(newCustomerObj);
    }
     
    public String editCustomerRecord(int customerId) {
        return DatabaseOperation.editCustomerRecordInDB(customerId);
    }
     
    public String updateCustomerDetails(CustomerBean updateCustomerObj) {
        return DatabaseOperation.updateCustomerDetailsInDB(updateCustomerObj);
    }
     
    public String deleteCustomerRecord(int customerId) {
        return DatabaseOperation.deleteCustomerRecordInDB(customerId);
    }
}

