package hw3.hw3.businessLayer.validators;

//am folosit cod de pe BitBucket, utcn_dsrl
/**
 * Interfata pentru validarea unor obiecte de tip generic
 * @author Timotei
 *
 * @param <T> tipul generic de validat : Client, Product, Orderr
 */
public interface Validator<T> {
	/**
	 * Metoda care valideaza obiectul de tip T
	 * @param t
	 */
	public void validate(T t);
}
