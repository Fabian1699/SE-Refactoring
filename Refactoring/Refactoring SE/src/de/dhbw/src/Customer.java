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
        Enumeration enum_rentals = rentals.elements();	    
        String result = "Rental Record for " + this.getName() + "\n";
        result += "\t" + "Title" + "\t" + "\t" + "Days" + "\t" + "Amount" + "\n";

        while (enum_rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental rental = (Rental) enum_rentals.nextElement();
            //determine amounts for each line
            thisAmount = amountFor(rental);
            // add frequent renter points
            frequentRenterPoints ++;
            // add bonus for a two day new release rental
            if ((rental.getMovie().getPriceCode() == Movie.PriceCode.NEW_RELEASE) && rental.getDaysRented() > 1) 
                frequentRenterPoints ++;
            //show figures for this rental
            result += "\t" + rental.getMovie().getTitle()+ "\t" + "\t" + rental.getDaysRented() + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        
        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }

    private double amountFor(Rental rental) {
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