package hw3.hw3.businessLayer.validators;

import hw3.hw3.model.Product;
/**
 * Obiect care valideaza componentele tipului produs
 * @author Timotei
 *
 */
public class ProductValidator implements Validator<Product>{

	/**
	 * Metoda care arunca exceptii in cazul in care Produsul nu este valid
	 * @param obiect de tip Product care urmeaza sa fie verificat
	 * @throws IllegalArgumentException 
	 */
	public void validate(Product t) {
		if(t.getName().equals(null)) {
			throw new IllegalArgumentException("Product with no name!");
		}else if(t.getPrice() <= 0) {
			throw new IllegalArgumentException("Product with invalid price!");
		}else if(t.getQuantity() <= 0) {
			throw new IllegalArgumentException("Product with invalid quantity!");
		}
	}

}
