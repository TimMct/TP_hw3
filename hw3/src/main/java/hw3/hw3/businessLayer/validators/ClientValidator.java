package hw3.hw3.businessLayer.validators;

import hw3.hw3.model.Client;

//am folosit cod de pe BitBucket, utcn_dsrl
/**
 * Obiect care valideaza componentele tipului client
 * @author Timotei
 *
 */
public class ClientValidator implements Validator<Client>{

	private static final int MIN_AGE = 15;
	private static final int MAX_AGE = 90;

	/**
	 * Metoda care arunca exceptii in cazul in care Clientul nu este valid
	 * @param obiect de tip Client care urmeaza sa fie verificat
	 * @throws IllegalArgumentException 
	 */
	public void validate(Client t) {
		
		if(t.getName().equals(null)) {
			throw new IllegalArgumentException("Client with no name!");
		}else if(t.getAddress().equals(null)) {
			throw new IllegalArgumentException("Client with no address!");
		}else if (t.getEmail().equals(null) || !t.getEmail().contains("@")) {
			throw new IllegalArgumentException("Client with bad email!");
		}else if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
			throw new IllegalArgumentException("Client needs age between "+MIN_AGE+" and "+MAX_AGE+"!");
		}
	}
}
