package lecture464.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lecture464.model.Products;

import lecture464.model.Users;


public class DBAccessClass {	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement ps = null;
  
	private ArrayList<Products> list = new ArrayList<Products>();
	private ArrayList<Products> orderProducts = new ArrayList<Products>();
	private ArrayList<ArrayList<Products>> completeOrderProducts = new ArrayList<ArrayList<Products>>();
	private ArrayList<Integer> orderList = new ArrayList<Integer>();
	private ArrayList<Orders> orderProductList = new ArrayList<Orders>();
	private ArrayList<Orders> completeOrderArray = new ArrayList<Orders>();
	private ArrayList<Orders> completeOrderInfo = new ArrayList<Orders>();
	private Orders OrderBean;
	private Products OrderProductBean;
	private Products ProductBean;
    private int userId;
    private int orderId;
    private int NewestOrderID;
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	final String DB_URL = "jdbc:mysql://cse.unl.edu:3306/rhooper";
	
	

	//  Database credentials
	static final String USER = "rhooper"; // Replace with your CSE_LOGIN
	static final String PASS = "An6-vN";   // Replace with your CSE MySQL_PASSWORD0
	
	public void insertCreditData (String userName, int creditNumber, String creditBrand, int userId, int CVV, int expirationDate) {
		int ccLimit = 1000;
		try{
			stmt = conn.createStatement();
			

		String sql = "INSERT INTO CreditCards (CardHolderName, CreditCardNumber, Balance, CardType, UserId, CVV, ExpirationDate)" +
				"VALUES ("+ userName +","+ creditNumber + "," + creditBrand +","+ ccLimit +"," + userId +"," + CVV +","+expirationDate+ ")";

		stmt.executeUpdate(sql);


/*			      PreparedStatement ps = conn.prepareStatement(sql);
			        ps.setString(2, userName);
			        ps.setInt(3, creditNumber);
			        ps.setString(5, creditBrand);
			        ps.setInt(6, userId);
			        ps.setInt(7, CVV);
			        ps.setInt(8, expirationDate);
			        
			        ps.executeQuery();
			        System.out.println(ps);
			       */
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void insertOrderData ( int customerId, double totalCost, String shippingAddress, String billAddress, int creditNumber) {
		try{
			stmt = conn.createStatement();
			
		String sql = "INSERT INTO Orders ( `CustomerId`, `TotalCost`, `OrderDate`, `ShippingAddress`, `BillingAddress`, `CreditCardNumber`)" +
				"VALUES (" + customerId + ", " + totalCost + ", CURRENT_DATE(), '" + shippingAddress + "', '" + billAddress + "', '" + creditNumber +"' )";
		stmt.executeUpdate(sql);

/*			      PreparedStatement ps = conn.prepareStatement(sql);
			        ps.setString(2, userName);
			        ps.setInt(3, creditNumber);
			        ps.setString(5, creditBrand);
			        ps.setInt(6, userId);
			        ps.setInt(7, CVV);
			        ps.setInt(8, expirationDate);
			        
			        ps.executeQuery();
			        System.out.println(ps);
			       */
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addSingleUser(Users aUser) {
		  
		try {
		  stmt = conn.createStatement();
		  String sql;
		  
		  String userName = aUser.getUserName();
		  String firstName = aUser.getFirstName();
		  String lastName = aUser.getLastName();
		  String password = aUser.getPassword();
		  String email = aUser.getEmail();
		  

		  sql = "INSERT INTO Users (FirstName, LastName, EmailAddress, Username, Password)" +
		          "VALUES ('" + firstName + "', '" + lastName + "', '" + email + "', '" + userName + "', '" + password + "')";
		  stmt.executeUpdate(sql);
		  
		  
		  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	}
	
	public boolean findUserByUsername(String aUserName) {
		boolean userExists = false;
		String SQL = "SELECT * from Users";
	    Statement stat;
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){	
				if(aUserName.equals( rs.getString(14) )) {
					userExists = true;
				}    
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userExists;
	}
	public boolean findUserByPassword(String password) {
		boolean passwordMatches = false;
		String SQL = "SELECT * from Users";
	    Statement stat;
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){	
				if(password.equals( rs.getString(15) )) {
					passwordMatches = true;
				}    
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return passwordMatches;
	}
	
	public Users returnUserByUsername(String aUserName) {
		String SQL = "SELECT Username, Password, FirstName, LastName from Users;";
	    Statement stat;
	   
	    Users aUser = new Users();
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){
				if(aUserName.equals( rs.getString("Username") )) {
					aUser.setUserName(rs.getString("Username"));
					aUser.setPassword(rs.getString("Password"));
					aUser.setFirstName(rs.getString("FirstName"));
					aUser.setLastName(rs.getString("LastName"));
				} 
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aUser;
	}
	
	public int returnUserIDbyUsername (String aUserName) {
		String SQL = "SELECT Username, Id from Users;";
	    Statement stat;
	   
	
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){
				if(aUserName.equals( rs.getString("Username") )) {
					userId = rs.getInt("Id");
				} 
		    }
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(userId);
		return userId;
	}
	
	public void connectMeIn() {
		try{
			//Register the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");			
		}catch(ClassNotFoundException e){
			System.err.println(e);
			System.exit (-1);
		}
		try {
			 //Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e){
			e.printStackTrace();
		}
   }
	public void SearchProduct(int pid){
	    String query = "SELECT * FROM Products WHERE Id LIKE ?";
	    
	    try {
	    	
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, pid);
	        
	        ResultSet rs = ps.executeQuery();
	        
	        
			  while(rs.next()){
				    //Retrieve by column name
				  int Id = rs.getInt("Id");
				  String ProductName = rs.getString("ProductName");
				  int ProductCategoryIndex = rs.getInt("ProducCategoryIndex");
				  String ProductDescription = rs.getString("ProductDescription");
				  double Price = rs.getInt("Price");
				  int AvailableQuantity = rs.getInt("AvailableQuantity");
				  int EstimatedDeliveryDays = rs.getInt("EstimatedDeliveryDays");
				  int SellerId = rs.getInt("SellerId");
				  String ProductPhotosLinks = rs.getString("ProductPhotosLinks");
				  String ProductThumbnail = rs.getString("ProductThumbnail");
				  ProductBean = new Products(Id, ProductName, ProductCategoryIndex, ProductDescription, Price, AvailableQuantity, EstimatedDeliveryDays, SellerId, ProductPhotosLinks, ProductThumbnail);
			  }
			  
			  System.out.println(ProductBean.getProductName());
	            
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	}
	
	//public void SearchShippingS

	public void SearchOrderProducts(Orders aOrder){
	    String query = "SELECT * FROM Products WHERE Id LIKE ?";
	    
	    	try {
		    	int thisID = aOrder.getProductId();
		        ps = conn.prepareStatement(query);
		        ps.setInt(1, thisID);
		        
		        ResultSet rs = ps.executeQuery();
		        
		        
				  while(rs.next()){ 
				  int Id = rs.getInt("Id");
				  String ProductName = rs.getString("ProductName");
				  int ProductCategoryIndex = rs.getInt("ProducCategoryIndex");
				  String ProductDescription = rs.getString("ProductDescription");
				  double Price = rs.getInt("Price");
				  int AvailableQuantity = rs.getInt("AvailableQuantity");
				  int EstimatedDeliveryDays = rs.getInt("EstimatedDeliveryDays");
				  int SellerId = rs.getInt("SellerId");
				  String ProductPhotosLinks = rs.getString("ProductPhotosLinks");
				  String ProductThumbnail = rs.getString("ProductThumbnail");
				  OrderProductBean = new Products(Id, ProductName, ProductCategoryIndex, ProductDescription, Price, AvailableQuantity, EstimatedDeliveryDays, SellerId, ProductPhotosLinks, ProductThumbnail);  }
		            
		    } catch (Exception e) {
		        e.printStackTrace();
		    } 
	    
	    }
	public Products   getOrderProduct() {
		return OrderProductBean;
	}
	
	public void insertOrderItemInfo(int OrderId, int ProductId, double ProductPrice, int Quantity, int ShippingStatus, int Status) {
		try{
			stmt = conn.createStatement();
			
		String sql = "INSERT INTO OrderItems ( OrderId, ProductId, ProductPrice, Quantity, ShippingStatus, Status)" +
				"VALUES ('" + OrderId + "', '" + ProductId + "', '" + ProductPrice +"', '" + Quantity +"', '" + ShippingStatus +"', '" + Status + "')";
		stmt.executeUpdate(sql);


		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	    
	
	public void findNewestOrderIdById(int customerID){
	    String query = "SELECT Id, CustomerId FROM Orders WHERE CustomerId LIKE ?";
	    
	    int recentOrderID = 0;
	    try {
	    	
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, customerID);
	        
	        ResultSet rs = ps.executeQuery();
	        System.out.println("query " + query);
	        
			  while(rs.next()){
				  
				 
				 NewestOrderID = rs.getInt("Id");
				 if (NewestOrderID > recentOrderID) {
					 recentOrderID = NewestOrderID;
				 }
				 }
			 
			 System.out.println("recentOrderID:"+NewestOrderID);
	            
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	}
	
	public int getNewestOrderId() {
		System.out.println("NewestOrderId: "+ NewestOrderID);
		return NewestOrderID;
		
	}
	
	public void findOrdersById(int customerID){
	    String query = "SELECT Id, CustomerId FROM Orders WHERE CustomerId LIKE ?";
	    
	    try {
	    	
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, customerID);
	        
	        ResultSet rs = ps.executeQuery();
	        System.out.println("query " + query);
	        
			  while(rs.next()){
				 int orderID = rs.getInt("Id");
				 orderList.add(orderID);
				 }
			 
			 System.out.println(orderList);
	            
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	}
	

	public void findProductsOrderedByOrderID(int orderArray){
	     String query = "SELECT ProductId, OrderId FROM OrderItems WHERE OrderId LIKE ?";
	     
	     ArrayList<Orders> orderProductList = new ArrayList<Orders>();
	     
	      try {
	          ps = conn.prepareStatement(query);
	          ps.setInt(1, orderArray );
	          ResultSet rs = ps.executeQuery();
	          Orders objt = null;
	      while(rs.next()){
	      int orderID = rs.getInt("OrderId");
	      int productID = rs.getInt("ProductId");
	      objt = new Orders(productID, orderID);
	      if (objt!=null) {
		      orderProductList.add(objt);
		      }
	      }
	      System.out.println("Objt: " + objt);
	      System.out.println("OrderProductorderProductList: " + orderProductList);
	      
	      } catch (Exception e) {
	          e.printStackTrace();
	      } 
		  completeOrderArray.addAll(orderProductList);
		     System.out.println("This OrdersProducts: " + completeOrderArray);
		     orderProductList.clear();
	     }
	
	public void findOrderedInfoByOrderID(int orderArray){
	     String query = "SELECT ProductId, OrderId, ShippingStatus FROM OrderItems WHERE OrderId LIKE ?";
	     
	     ArrayList<Orders> orderProductList = new ArrayList<Orders>();
	     
	      try {
	          ps = conn.prepareStatement(query);
	          ps.setInt(1, orderArray );
	          ResultSet rs = ps.executeQuery();
	          Orders objt = null;
	      while(rs.next()){
	      int orderID = rs.getInt("OrderId");
	      int productID = rs.getInt("ProductId");
	      int ShippingStatus =rs.getInt("ShippingStatus");
	      objt = new Orders(productID, orderID);
	      objt.setShippingStatus(ShippingStatus);
	      }
	      System.out.println("Objt: " + objt);
	      System.out.println("OrderProductorderProductList: " + orderProductList);
	      
	      } catch (Exception e) {
	          e.printStackTrace();
	      } 
		  completeOrderArray.addAll(orderProductList);
		     System.out.println("This OrdersProducts: " + completeOrderArray);
		     orderProductList.clear();
	     }
	public ArrayList<Orders> getCompleteOrderInfo() {
		return completeOrderInfo;
	}
	
	
	public ArrayList<Orders> getCompleteOrderList() {
		return completeOrderArray;
	}

	public void clearOrderProductLists() {
		completeOrderArray.clear();
	}

	
	public ArrayList<Integer> getOrderbyId() {
		return orderList;
	}
	public Object getProduct() {
		return ProductBean;
	}
	
	public void SearchProductInfo(String pid){
    String query = "SELECT * FROM Products WHERE ProductName LIKE ?";
    
    try {
    	
        ps = conn.prepareStatement(query);
        ps.setString(1, "%" + pid + "%");
        
        ResultSet rs = ps.executeQuery();
        System.out.println("query " + query);
        
        
		  while(rs.next()){
			    //Retrieve by column name
			  int Id = rs.getInt("Id");
			  String ProductName = rs.getString("ProductName");
			  int ProductCategoryIndex = rs.getInt("ProducCategoryIndex");
			  String ProductDescription = rs.getString("ProductDescription");
			  double Price = rs.getInt("Price");
			  int AvailableQuantity = rs.getInt("AvailableQuantity");
			  int EstimatedDeliveryDays = rs.getInt("EstimatedDeliveryDays");
			  int SellerId = rs.getInt("SellerId");
			  String ProductPhotosLinks = rs.getString("ProductPhotosLinks");
			  String ProductThumbnail = rs.getString("ProductThumbnail");
			  Products ProductBean = new Products(Id, ProductName, ProductCategoryIndex, ProductDescription, Price, AvailableQuantity, EstimatedDeliveryDays, SellerId, ProductPhotosLinks, ProductThumbnail);
			  //store Data
			  list.add(ProductBean);
		  }
		  
		  System.out.println(list.get(0).getProductName());
            
    } catch (Exception e) {
        e.printStackTrace();
    } 
}
	public ArrayList<Products> getProductList() {
		return list;
	}
	
	public boolean checkCreditCard(int creditNumber, String creditBrand, int CVV) {
		boolean cardMatches = false;
		String SQL = "SELECT * from CreditCards";
	    Statement stat;
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(SQL);
			
			while (rs.next()){	
				if(creditNumber == ( rs.getDouble(3)) && creditBrand.equals(rs.getString(5)) && CVV == (rs.getInt(7))) {
					cardMatches = true;
				}
			}
			
		    stat.close();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cardMatches;
	}
	
	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
