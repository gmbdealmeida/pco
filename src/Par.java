/**
 * Representa pares em que o primeiro elemento eh uma string e
 * o segundo elemento eh um double
 * @author in
 * @date Novembro 2019
 */
public class Par {
	
	private String primeiro;
	private double segundo;
	
	/**
	 * Inicializa os atributos do novo objeto
	 * @param prim O primeiro elemento para o novo par
	 * @param seg O segundo elemento para o novo par
	 * @ensures this.primeiro().equals(seg) && 
	 *          this.segundo() == seg
	 */
	public Par (String prim, double seg) {
		this.primeiro = prim;
		this.segundo = seg;
	}
	
	/**
	 * O primeiro elemento deste par
	 * @return O primeiro elemento deste par
	 */
	public String primeiro() {
		return this.primeiro;
	}
	
	/**
	 * O segundo elemento deste par
	 * @return O segundo elemento deste par
	 */
	public double segundo() {
		return this.segundo;
	}
	
	/**
	 * Representacao textual deste par
	 */
	public String toString() {
		return "(" + this.primeiro + "," + this.segundo + ")";
	}

}
