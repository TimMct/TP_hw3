package hw3.hw3.businessLayer.validators;

import hw3.hw3.model.Orderr;
/**
 * Obiect care valideaza componentele tipului orderr
 * @author Timotei
 *
 */
public class OrderValidator implements Validator<Orderr>{

	/**
	 * Metoda care arunca exceptii in cazul in care Orderr nu este valid
	 * @param obiect de tip Orderr care urmeaza sa fie verificat
	 * @throws IllegalArgumentException 
	 */
	public void validate(Orderr t) {
		if(t.getClientId() <= 0) {
			throw new IllegalArgumentException("Order with bad clientId!");
		}else if(t.getProductId() <= 0) {
			throw new IllegalArgumentException("Order with bad productId!");
		}else if(t.getQuantity() <= 0) {
			throw new IllegalArgumentException("Order with no quantity!");
		}
	}
}
