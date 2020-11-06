package addressbookjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	PreparedStatement addressBookDataPreparedStatement;
	
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
		String sql = "Select * from address_book;";
		return this.getAddressBookDataUsingDB(sql);
	}
	
	
	public List<Contact> getAddressBookDataUsingDB(String sql)
	{
		List<Contact> addressBookList = new ArrayList<Contact>();
		
		try(Connection connection = this.getConnection()) 
		{
			addressBookDataPreparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = addressBookDataPreparedStatement.executeQuery(sql);
			
			addressBookList = this.getAddressBookData(resultSet);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return addressBookList;
	}
	
	public List<Contact> getAddressBookData(ResultSet resultSet)
	{
		List<Contact> addressBookList = new ArrayList<Contact>();
		
		try 
		{
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
