package de.dhbw.src;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CustomerTest {

	@Test
	void getEmptyCustomerStatement_SHOULD_CONTAIN_AmountAndRenterPointsEqualZero() {
		//arrange
        Customer c1 = new Customer("joe");
        
        //act
        String statement = c1.statement();
        
        //assert
        assertTrue(statement.contains("Amount owed is 0.0"));
        assertTrue(statement.contains("You earned 0 frequent renter points"));
	}
	
	@Test
	void getCustomerStatementWithOneRental_SHOULD_CONTAIN_RentalWithCorrectNumbers() {
		//arrange
        Customer c1 = new Customer("joe");
        Movie m1 = new Movie("movie1", Movie.PriceCode.NEW_RELEASE);
        Rental r1 = new Rental(m1, 10);
        c1.addRental(r1);
        
        //act
        String statement = c1.statement();
        
        //assert
        assertTrue(statement.contains("movie1		10	30.0"));
        assertTrue(statement.contains("Amount owed is 30.0"));
        assertTrue(statement.contains("You earned 2 frequent renter points"));
	}
	
	@Test
	void getCustomerStatementWithMultipleRentals_SHOULD_CONTAIN_AllRentalsWithCorrectNumbers() {
		//arrange
        Customer c1 = new Customer("joe");
        Movie m1 = new Movie("movie1", Movie.PriceCode.NEW_RELEASE);
        Movie m2 = new Movie("movie2", Movie.PriceCode.CHILDREN);
        Movie m3 = new Movie("movie3", Movie.PriceCode.REGULAR);
        Rental r1 = new Rental(m1, 10);
        Rental r2 = new Rental(m2, 10);
        Rental r3 = new Rental(m3, 10);
        c1.addRental(r1);
        c1.addRental(r2);
        c1.addRental(r3);
        
        //act
        String statement = c1.statement();
        
        //assert
        assertTrue(statement.contains("movie1		10	30.0"));
        assertTrue(statement.contains("movie2		10	12.0"));
        assertTrue(statement.contains("movie3		10	14.0"));
        assertTrue(statement.contains("Amount owed is 56.0"));
        assertTrue(statement.contains("You earned 4 frequent renter points"));
	}

}
