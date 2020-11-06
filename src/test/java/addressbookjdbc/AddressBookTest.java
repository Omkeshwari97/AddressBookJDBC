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
		assertEquals(4, addressBookData.size());
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
	public void givenCityName_WhenRetrieved_ShouldMatchPersonCount()
	{
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		List<Contact> addressBookList = addressBookService.readDataForCity("Mumbai");
		assertEquals(2, addressBookList.size());
	}
	
	public void givenStateName_WhenRetrieved_ShouldMatchPersonCount()
	{
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		List<Contact> addressBookList = addressBookService.readDataForState("Maharashtra");
		assertEquals(3, addressBookList.size());
	}
	
	//uc20
	public void givenNewContact_WhenAdded_ShouldSynWithDB()
	{
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		List<Contact> addressBookList= addressBookService.addContactToAddressBook("Suru", "Pilare", "Maheshwari, Matunga", "Mumbai", "Maharashtra", "400018", "9876987600", "abc@gmail.com", "2020-03-15");
		boolean result = addressBookService.checkAddressBookInSyncWithDB("Suru");
		assertTrue(result);
		assertEquals(4, addressBookList.size());
	}
}