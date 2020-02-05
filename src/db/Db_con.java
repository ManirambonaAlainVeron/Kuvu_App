package db;

import java.sql.*;



public class Db_con {
	
    
 private static Connection con=null;
 
 public static int modifierDB(String sql){
     
         int result=-1;
        
             
         if(con==null)
             if(!createConnection())
                 return result;
                         
         Statement st;
		try {
			st = con.createStatement();
			result=st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         
   
      
     return result;
 }
 
 /*
 select
 */
 public static ResultSet extraireData(String requete){
     
     ResultSet result=null;
         
 
     Statement  st;
         if(con==null)
             if(!createConnection())
                 return result;
         
         	try {
				st = con.createStatement();
				result=st.executeQuery(requete);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
         return result;
 }
 
 static boolean createConnection(){
     boolean status=false;
    
         
         
         try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/umuvunjayidbweb","root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         status=true;
     
     
     return status;
 }
}
