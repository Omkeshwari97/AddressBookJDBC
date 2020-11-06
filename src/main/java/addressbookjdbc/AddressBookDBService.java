package addressbookjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressBookDBService 
{
	private static final Logger log = LogManager.getLogger(AddressBookDBService.class);
	private static AddressBookDBService addressBookDBService;

	public AddressBookDBService() 
	{}
	
	public static AddressBookDBService getInstance() 
	{
		if(addressBookDBService == null)
		{
			addressBookDBService = new AddressBookDBService();
		}
		
		return addressBookDBService;
	}
	
	private Connection getConnection() 
	{
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
		String userName = "root";
		String password = "root";
		Connection connection = null;
		
		try 
		{
			System.out.println("Connecting to database!");
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is successful!" + connection);					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return connection;
	}

	//uc16
	public List<Contact> readData() 
	{
		List<Contact> addressBookList = new ArrayList<Contact>();
		
		try(Connection connection = this.getConnection()) 
		{
			Statement statement = connection.createStatement();
			String sql = "Select * from address_book;";
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				String zip = resultSet.getString("zip");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");
				
				addressBookList.add(new Contact(id, fname, lname, address, city, state, zip, phone, email));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return addressBookList;
	}
}
