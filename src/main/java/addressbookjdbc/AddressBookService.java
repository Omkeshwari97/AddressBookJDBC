package addressbookjdbc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressBookService 
{
	public enum IOService{FILE_IO, DB_IO, Rest_IO}
	private List<Contact> addressBookList;
	private static final Logger log = LogManager.getLogger(AddressBookDBService.class);
	private AddressBookDBService addressBookDBService = new AddressBookDBService();

	public AddressBookService() 
	{}
	
	public AddressBookService(List<Contact> addressBookList) 
	{
		this();
		this.addressBookList = addressBookList;
	}
	
	//uc16
	public List<Contact> readAddressBookData(IOService ioService) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			this.addressBookList = addressBookDBService.readData();
		}
		
		return addressBookList;
	}

	//uc17
	public void updateAddressBookEmail(String fname, String email) 
	{
		int result = addressBookDBService.updatePersonEmail(fname, email);
		
		if(result == 0)
		{
			return;
		}
		
		Contact contact = this.getAddressBookData(fname);
		
		if(contact != null)
		{
			contact.setEmail(email);
		}
	}

	private Contact getAddressBookData(String fname) 
	{
		Contact personContact = addressBookList.stream()
								.filter(c -> c.getFirstName().equals(fname))
								.findFirst()
								.orElse(null);
		
		return personContact;
	}

	public boolean checkAddressBookInSyncWithDB(String fname) 
	{
		List<Contact> addressBookDBList= addressBookDBService.getAddressBookData(fname);
		return addressBookDBList.get(0).equals(getAddressBookData(fname));
	}

	//uc18
	public List<Contact> readDataonDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			return addressBookDBService.readDataonDateRange(startDate, endDate);
		}
		
		return null;
	}

	//uc19
	public List<Contact> readDataForCity(String city) 
	{
		return addressBookDBService.readDataForCity(city);
	}

	public List<Contact> readDataForState(String state) 
	{
		return addressBookDBService.readDataForState(state);
	}

	//uc20
	public void addContactToAddressBook(String fname, String lname, String address, String city, String state, String zip, String phone, String email, String dateAdded, List<String> bookTypeList) 
	{
		List<Contact> contactList = addressBookDBService.addContactToAddressBook(fname, lname, address, city, state, zip, phone, email, dateAdded, bookTypeList);
		
		contactList.forEach(contactObj -> {		
			System.out.println("a: " + contactObj.firstName);
			this.addressBookList.add(contactObj);
		});
	}
	
	//uc21
	public void addContactsToAddressBook(List<Contact> contactList) 
	{	
		contactList.forEach(contactObj -> {
				this.addContactToAddressBook(contactObj.firstName, contactObj.lastName, contactObj.address, contactObj.city,contactObj.state, contactObj.zip, contactObj.phoneNumber, contactObj.email, contactObj.date_added, contactObj.bookTypeList);
			});
	}

	//uc22
	public int countEntries(IOService ioService) 
	{	
		if(ioService.equals(IOService.DB_IO))
		{
			return this.addressBookList.size();
		}
		
		return this.addressBookList.size();
	}
	
	//uc23
	public void addContactsToAddressBook(Contact contact, IOService ioService) 
	{
		if(ioService.equals(ioService.Rest_IO))
		{
			addressBookList.add(contact);
		}
		
		for(Contact objContact : addressBookList)
		{
			System.out.println(objContact.firstName);
		}
	}
	
	//uc24
	Contact getContactDetails(String name) 
	{
		Contact contact = this.addressBookList.stream()
								.filter(c -> c.firstName.equals(name))
								.findFirst()
								.orElse(null);
		return contact;
	}
}
