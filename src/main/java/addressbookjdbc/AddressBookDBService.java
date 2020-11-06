package addressbookjdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
	
	//uc17
	public int updatePersonEmail(String fname, String email) 
	{
		String sql = String.format("update address_book set email = '%s' where fname = '%s';", email, fname);
		
		try(Connection connection = this.getConnection()) 
		{
			addressBookDataPreparedStatement = connection.prepareStatement(sql);
			return addressBookDataPreparedStatement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public List<Contact> getAddressBookData(String fname) 
	{
		String sql = String.format("Select * from address_book where fname = '%s';", fname);
		return getAddressBookDataUsingDB(sql);
	}

	//uc18
	public List<Contact> readDataonDateRange(LocalDate startDate, LocalDate endDate) 
	{
		String sql = String.format("select * from address_book where date_added between '%s' and '%s';", Date.valueOf(startDate), Date.valueOf(endDate));
		return getAddressBookDataUsingDB(sql);
	}

	//uc19
	public List<Contact> readDataForCity(String city) 
	{
		String sql = String.format("select * from address_book where city = '%s';", city);
		return getAddressBookDataUsingDB(sql);
	}

	public List<Contact> readDataForState(String state) 
	{
		String sql = String.format("select * from address_book where state = '%s';", state);
		return getAddressBookDataUsingDB(sql);
	}

	//uc20
	public Contact addContactToAddressBook(String fname, String lname, String address, String city, String state, String zip, String phone, String email, String dateAdded) 
	{
		int contactId = -1;
		
		String sql = String.format("Insert into address_book (fname, lname, address, city, state, zip, phone, email, date_added) values"
				+ "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s') ;)", fname, lname, address, city, state, zip, phone, email, dateAdded);
		
		try(Connection connection = this.getConnection()) 
		{
			addressBookDataPreparedStatement = connection.prepareStatement(sql);
			int rowsAffected = addressBookDataPreparedStatement.executeUpdate(sql, addressBookDataPreparedStatement.RETURN_GENERATED_KEYS);
			
			if(rowsAffected == 1)
			{
				ResultSet resultSet = addressBookDataPreparedStatement.getGeneratedKeys();
			
				if(resultSet.next())
				{
					contactId = resultSet.getInt("id");
				}
			}
			
			return new Contact(contactId, fname, lname, address, city, state, zip, phone, email);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
}
