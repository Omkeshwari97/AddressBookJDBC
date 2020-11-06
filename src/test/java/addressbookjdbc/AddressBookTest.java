package addressbookjdbc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import addressbookjdbc.AddressBookService.IOService;

public class AddressBookTest 
{
	//uc16
	@Test
	public void givenAddressBookDB_WhenRetrieved_ShouldMatchEmployeeCount()
	{
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		assertEquals(4, addressBookData.size());
	}
}