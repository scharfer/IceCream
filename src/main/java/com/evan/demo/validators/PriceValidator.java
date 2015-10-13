package com.evan.demo.validators;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validate the price
 * @author escharfer
 *
 */
@Component
public class PriceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }   

	@Override
	/**
	 * Validate the big decimal to make sure it is postive and a number
	 */
	public void validate(Object arg0, Errors e) {
		String priceStr = (String)arg0;
		BigDecimal bigD = null;
		try {
			// check if can be converted to Big D
			bigD = new BigDecimal(priceStr);
		} catch (NumberFormatException nfe) {
			e.reject("price","no a number");
		}
		// Prices must be positive
		if (bigD != null && bigD.doubleValue() < 0) {
			e.reject("price", "price must be postive");			
		}
		
	}

}
