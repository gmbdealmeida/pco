import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Esta classe representa um objeto a leiloar
 * 
 * Estah incompleta!
 * 
 * @author isabel nunes
 * @date Novembro 2019
 */
public class ObjetoALeiloar implements Quantificavel {

	private String descricao;
	private double valorInicial;
	private List<Par> ofertas;
	
	/**
	 * Inicializa atributos do novo objeto
	 * @param descricao A descricao do novo objeto
	 * @param valorBase O valor base de licitacao do novo objeto;
	 *                  considera-se que houve pelo menos uma 
	 *                  licitacao deste objeto, neste valor
	 */
	public ObjetoALeiloar(String descricao, double valorBase) {
		this.descricao = descricao;
		this.valorInicial = valorBase;
		this.ofertas = new ArrayList<Par>();
		ofertas.add(new Par("Owner", this.valorInicial));
	}
	
	/**
	 * A descricao deste objeto
	 * @return
	 */
	public String descricao() {
		return this.descricao;
	}
	
	/**
	 * O valor inicial de licitacao deste objeto
	 * @return
	 */
	public double valorInicial() {
		return this.valorInicial;
	}

	/**
     * Regista nova licitacao para este objeto
     * @param cliente O nome do cliente que licitou
     * @param valor O valor licitado
     * @requires valor > this.valorLicitacaoMaisRecente()
     */
	public void novaLicitacao(String cliente, double valor) {
		ofertas.add(new Par(cliente, valor));
	}
	
	/**
	 * O valor licitado mais recentemente para este objeto
	 * @return
	 */
	public double valorLicitacaoMaisRecente() {
		return ofertas.get(ofertas.size()-1).segundo();
	}
	
	/**
	 * O cliente que licitou este objeto mais recentemente
	 * @return
	 */
	public String clienteLicitacaoMaisRecente() {
		return ofertas.get(ofertas.size()-1).primeiro();
	}
	
	/**
	 * Uma copia do historico das ofertas a este objeto
	 * @return
	 */
	public List<Par> historico(){
		// nao eh preciso copiar os elementos de this.ofertas um a um,
		// pois Par eh imutavel
		return new ArrayList<Par>(this.ofertas);
	}

	/**
	 * Representacao textual deste objeto
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Descricao: " + this.descricao + "   Valor inicial: " +
		          this.valorInicial + "\n");
		sb.append("Ofertas: ");
		for(Par p : ofertas) {
			sb.append(p.primeiro() + ": " + p.segundo() + "\n");
		}
		return sb.toString();
	}

	/**
	 * As varias ofertas feitas a este objeto a leiloar
	 * @return pares contendo cada um o nome do cliente que fez a licitacao 
	 *         e o valor da mesma
	 */
	@Override
	public Map<String, Double> valores() {
		//COMPLETAR!
	}	
}
