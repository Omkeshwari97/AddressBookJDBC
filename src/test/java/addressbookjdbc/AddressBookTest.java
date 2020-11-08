package addressbookjdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
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
		assertEquals(5, addressBookData.size());
	}
	
	//uc17
	@Test
	public void givenNewEmailForPerson_WhenUpdated_ShouldSyncWithDB()
	{
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.updateAddressBookEmail("Piyush", "piush@gmail.com");
		boolean result = addressBookService.checkAddressBookInSyncWithDB("Piyush");
		assertTrue(result);
	}
	
	//uc18
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchPersonCount()
	{
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2019, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<Contact> addressBookList = addressBookService.readDataonDateRange(IOService.DB_IO, startDate, endDate);
		assertEquals(3, addressBookList.size());
	}
	
	//uc19
	@Test
	public void givenCityName_WhenRetrieved_ShouldMatchPersonCount()
	{
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		List<Contact> addressBookList = addressBookService.readDataForCity("Mumbai");
		assertEquals(2, addressBookList.size());
	}
	
	@Test
	public void givenStateName_WhenRetrieved_ShouldMatchPersonCount()
	{
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		List<Contact> addressBookList = addressBookService.readDataForState("Maharashtra");
		assertEquals(4, addressBookList.size());
	}
	
	//uc20
	@Test
	public void givenNewContact_WhenAdded_ShouldSynWithDB()
	{
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		List<Contact> addressBookList= addressBookService.addContactToAddressBook("Madhuri", "Pilare", "Maheshwari, Matunga", "Mumbai", "Maharashtra", "400018", "9876987600", "abc@gmail.com", "2020-03-15", Arrays.asList("Family", "Profession"));
		boolean result = addressBookService.checkAddressBookInSyncWithDB("Madhuri");
		assertTrue(result);
		assertEquals(6, addressBookList.size());
	}
}