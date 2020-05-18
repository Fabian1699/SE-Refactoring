package de.dhbw.src;

import java.lang.*;
import java.util.*;

class Customer {
    private String name;
    private Vector rentals = new Vector();
    
    public Customer (String newname){
        name = newname;
    }
    
    public void addRental(Rental rental) {
        rentals.addElement(rental);
    }
    
    public String getName(){
        return name;
    }
    
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String rentalList = "";
        Enumeration enum_rentals = rentals.elements();	    
        
        while (enum_rentals.hasMoreElements()) {
        	Rental rental = (Rental) enum_rentals.nextElement();
            double thisAmount = getAmountForRental(rental);
            frequentRenterPoints = increaseRenterPoints(frequentRenterPoints, rental);
            //show figures for this rental
            rentalList += "\t" + rental.getMovie().getTitle()+ "\t" + "\t" + rental.getDaysRented() + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        return generateResultString(totalAmount, frequentRenterPoints, rentalList);
    }

	private int increaseRenterPoints(int frequentRenterPoints, Rental rental) {
		if (isNewReleaseAndRentedForMoreThanOneDay(rental)) {
			frequentRenterPoints+=2;	
		}else {
		    frequentRenterPoints ++;
		}
		return frequentRenterPoints;
	}

	private boolean isNewReleaseAndRentedForMoreThanOneDay(Rental rental) {
		return (rental.getMovie().getPriceCode() == Movie.PriceCode.NEW_RELEASE) && rental.getDaysRented() > 1;
	}
    
    private String generateResultString(double totalAmount, int frequentRenterPoints, String rentalList) {
        String result = "Rental Record for " + this.getName() + "\n";
        result += "\t" + "Title" + "\t" + "\t" + "Days" + "\t" + "Amount" + "\n";
        
        //add rental list 
        result += rentalList;
        
        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }

    private double getAmountForRental(Rental rental) {
        double thisAmount = 0;
        switch (rental.getMovie().getPriceCode()) {
            case REGULAR:
			thisAmount = getRegularAmount(rental, thisAmount);
                break;
            case NEW_RELEASE:
			thisAmount = getNewReleaseAmount(rental, thisAmount);
                break;
            case CHILDREN:
			thisAmount = getChildrenAmount(rental, thisAmount);
                break;
        }
        return thisAmount;
    }

	private double getChildrenAmount(Rental rental, double thisAmount) {
		thisAmount += 1.5;
		if (rental.getDaysRented() > 3)
		    thisAmount += (rental.getDaysRented() - 3) * 1.5;
		return thisAmount;
	}

	private double getNewReleaseAmount(Rental rental, double thisAmount) {
		thisAmount += rental.getDaysRented() * 3;
		return thisAmount;
	}

	private double getRegularAmount(Rental rental, double thisAmount) {
		thisAmount += 2;
		if (rental.getDaysRented() > 2)
		    thisAmount += (rental.getDaysRented() - 2) * 1.5;
		return thisAmount;
	}
} 