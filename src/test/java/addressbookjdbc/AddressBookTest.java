package addressbookjdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import addressbookjdbc.AddressBookService.IOService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookTest 
{
	/*
	//uc16
	@Test
	public void givenAddressBookDB_WhenRetrieved_ShouldMatchEmployeeCount()
	{
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		assertEquals(8, addressBookData.size());
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
		assertEquals(6, addressBookList.size());
	}
	
	//uc20
	@Test
	public void givenNewContact_WhenAdded_ShouldSynWithDB()
	{
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.addContactToAddressBook("Madhuri", "Pilare", "Maheshwari, Matunga", "Mumbai", "Maharashtra", "400018", "9876987600", "abc@gmail.com", "2020-03-15", Arrays.asList("Family", "Profession"));
		boolean result = addressBookService.checkAddressBookInSyncWithDB("Madhuri");
		assertTrue(result);
	}
	
	//uc21
	@Test
	public void givenMultipleContacts_WhenAdded_SHouldMatchPersonCount()
	{
		Contact arrayOfContacts[] = {
			new Contact("Lata", "Donadkar", "Kasturi Kunj, Sion", "Mumbai", "Maharashtra", "400022", "9876543210", "ladki@gmail.com", "2018-03-15", Arrays.asList("Family")),
			new Contact("Mital", "Donadkar", "Gandhi square", "Surat", "Gujarat", "500084", "9969646413", "mitz@gmail.com", "2019-03-15", Arrays.asList("Friends", "Profession")),
			new Contact("Ashwini", "Pilare", "MIDC, Chakan", "Pune", "Maharashtra", "400784", "9988776655", "ashp@gmail.com", "2019-03-15", Arrays.asList("Family", "Profession"))	
		};
		
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		Instant start = Instant.now();
		addressBookService.addContactsToAddressBook(Arrays.asList(arrayOfContacts));
		Instant end = Instant.now();
		System.out.println("Duration without Thread: " + Duration.between(start, end));
		int result = addressBookService.countEntries(IOService.DB_IO);
		assertEquals(9, result);
	}
	*/
	//JSON
	@Before
	public void setup()
	{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	
	private Contact[] getContactList() 
	{
		Response response = RestAssured.get("/contact");
		System.out.println("CONTACT ENTRIES IN JSONSERVER:\n" + response.asString());
		Contact arrayOfContacts[] = new Gson().fromJson(response.asString(), Contact[].class);
		return arrayOfContacts;
	}
	
	private Response addContactToJsonServer(Contact contact) 
	{
		String contactJson = new Gson().toJson(contact);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(contactJson);
		return requestSpecification.post("/contact");
	}
	
	//uc22
	@Test
	public void givenAddressBookDataInJSONServer_WhenRetrieved_ShouldMatchTheCount()
	{
		Contact arrayOfContacts[] = getContactList();
		AddressBookService addressBookService;
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		long entries = addressBookService.countEntries(IOService.Rest_IO);
		assertEquals(2, entries);
	}
	
	/*
	//uc23
	@Test
	public void givenNewContact_WhenAdded_ShouldMatch201sResponseAndCount()
	{	
		Contact arrayOfContacts[] = getContactList();
		AddressBookService addressBookService;
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		Contact contact = new Contact(0, "Vaidehi", "Naktode", "Dahanu", "Mumbai", "Maharsahtra", "400678", "9876543210", "van@gmail.com");
		Response response = addContactToJsonServer(contact);
		int statusCode = response.getStatusCode();
		assertEquals(201, statusCode);
		
		contact = new Gson().fromJson(response.asString(), Contact.class);
		addressBookService.addContactsToAddressBook(contact, IOService.Rest_IO);
		long entries = addressBookService.countEntries(IOService.Rest_IO);
		assertEquals(3, entries);
	}*/
	
	//uc24
	@Test
	public void givenNewPhoneNumber_WhenUpdated_ShouldMatch200Response()
	{
		Contact arrayOfContacts[] = getContactList();
		AddressBookService addressBookService;
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		
		addressBookService.updateAddressBookEmail("Thorvi", "thorvinaktd@gmail.com");
		Contact contact = addressBookService.getContactDetails("Thorvi");
		
		String contactJson = new Gson().toJson(contact);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(contactJson);
		
		Response response = requestSpecification.put("/contact/" + contact.id);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
	}
}