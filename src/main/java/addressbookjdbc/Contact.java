package addressbookjdbc;

import java.util.List;
import java.util.Objects;

public class Contact
{
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public String zip;
    public String phoneNumber;
    public String email;
	public int id;
	public String date_added;
	public List<String> bookTypeList;

    public Contact(String firstName,String lastName,String address,String city,String state,String zip,String phoneNumber,String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Contact(int id, String fname, String lname, String address, String city, String state, String zip, String phone, String email) 
    {
    	this(fname, lname, address, city, state, zip, phone, email);
    	this.id = id;
	}

	public Contact(String fname, String lname, String address, String city, String state, String zip, String phone, String email, String date_added, List<String> bookTypeList) 
	{
		this(fname, lname, address, city, state, zip, phone, email);
		this.date_added = date_added;
		this.bookTypeList = bookTypeList;
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(firstName, lastName);
	}
	
	@Override
    public boolean equals(Object other)
    {
        boolean result = false;

        if(other == this)
        {
            return true;
        }

        if(other == null)
        {
            return false;
        }

        Contact contact = (Contact)other;

        if(contact.firstName.equals(this.firstName))
        {
            result = true;
        }

        return result;
    }

	@Override
	public String toString() 
	{
		return "ID : " + id + " First Name : " + firstName + " Last Name: " + lastName + " Address: " + address + " City: " + city + " State: " + state + " Zip: " + zip + " Phone Number: " + phoneNumber + " Email: " + email;
	}
	
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email)
    {
        this.email=email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getZip()
    {
        return zip;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }
}