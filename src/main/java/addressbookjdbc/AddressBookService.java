package addressbookjdbc;

import java.util.List;

public class AddressBookService 
{
	public enum IOService{FILE_IO, DB_IO}
	private List<Contact> addressBookList;
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
}
