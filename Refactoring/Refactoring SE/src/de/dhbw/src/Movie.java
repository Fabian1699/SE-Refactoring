package de.dhbw.src;

public class Movie {
    private String title;
    private PriceCode priceCode;
    
    public Movie(String newtitle, PriceCode newpriceCode) {
        title = newtitle;
        priceCode = newpriceCode;
    }
    
    public PriceCode getPriceCode() {
        return priceCode;
    }
    
    public void setPriceCode(PriceCode newPriceCode) {
        priceCode = newPriceCode;
    }
    
    public String getTitle(){
        return title;
    }
    
    enum PriceCode{
    	CHILDREN, REGULAR, NEW_RELEASE
    }
} 