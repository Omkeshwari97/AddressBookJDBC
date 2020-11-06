package addressbookjdbc;

import java.util.List;

public class AddressBookService 
{
	public enum IOService{FILE_IO, DB_IO}
	private List<Contact> addressBookList;
	private AddressBookDBService addressBookDBService;

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
			this.addressBookList = new AddressBookDBService().readData();
		}
		
		return addressBookList;
	}
}
