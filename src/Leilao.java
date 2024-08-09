import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Esta classe representa um leilao de varios objetos em simultaneo
 * 
 * Estah incompleta!
 * 
 * @author in
 * @date Novembro 2019
 */
public class Leilao implements Quantificavel {
	
	// Atributos de classe
	public static final double VALOR_MINIMO = 100;
	public static final double VALOR_MAXIMO = 500;
	public static final double AMPLITUDE_LOTE = 100;	
	// Atributos de instancia
	private String descricao;
	private Map<String,ObjetoALeiloar> objetos;
	private boolean aberto;
	
	/**
	 * Construtor, dada uma lista de nomes de pecas a leiloar e respetivos
	 * precos base de licitacao
	 * @param descricao O nome do leilao
	 * @param aLeilao As descricoes e valores base dos objetos a leiloar
	 * @requires Leilao.objetosValidos(aLeilao)
	 */
	public Leilao(String descricao, List<Par> aLeilao) {
		this.descricao = descricao;
		this.objetos = new HashMap<String,ObjetoALeiloar>();
		for(Par p : aLeilao) {
			ObjetoALeiloar objeto = new ObjetoALeiloar(p.primeiro(),p.segundo());
			this.objetos.put(p.primeiro(),objeto);
		}
		this.aberto = true;
	}

	/**
	 * Uma lista de nomes de pecas a leiloar e respetivos
	 * precos base de licitacao sao validos para um leilao?
	 * @param aLeilao As descricoes e valores base dos objetos a leiloar
	 * @returns true se aLeilao != null && aLeilao.size() > 0 && 
	 *          forall i aLeilao[i] != null && as descricoes dos objetos != null
	 *          && os valores base dos objetos pertencem a [VALOR_MINIMO,VALOR_MAXIMO]
	 *          && a diferenca entre os valores maximo e minimo dos objetos
	 *          a leiloar eh menor que AMPLITUDE_LOTE
	 */
	public static boolean objetosValidos(List<Par> aLeilao) {
		boolean result = aLeilao != null;
		result = result && aLeilao.size() > 0;
		double minimo = VALOR_MAXIMO;
		double maximo = VALOR_MINIMO;
		for(int i = 0 ; result && i < aLeilao.size() ; i++) {
			Par objeto = aLeilao.get(i);
			result = objeto != null;
			double valor = objeto.segundo();
			result = result && objeto.primeiro() != null && 
					 valor >= VALOR_MINIMO && valor <= VALOR_MAXIMO;
			if(result) {
				if (valor < minimo) {
					minimo = valor;
				}
				if (valor > maximo) {
					maximo = valor;
				}	
			}
		}
         result = result && (maximo - minimo <= AMPLITUDE_LOTE);
         return result;
	}

	/**
	 * A descricao deste leilao
	 * @return
	 */
	public String descricao() {
		return this.descricao;
	}
	
	/**
	 * Ainda se podem fazer licitacoes?
	 * @return
	 */
	public boolean estahActivo() {
		return this.aberto;
	}
	
	/**
	 * O objeto com uma dada descricao, ou null, se nao existir
	 * @param descricao
	 * @return
	 */
	private ObjetoALeiloar objeto(String descricao) {
		return this.objetos.get(descricao); // devolve null se nao existe
	}
	
	/**
	 * Um objeto com uma dada descricao estah em leilao?
	 * @param descricao
	 * @return
	 */
	public boolean estahEmLeilao(String descricao) {
		return objeto(descricao) != null;
	}
	
	/**
	 * Valor da licitacao mais recente que um objeto com uma dada
	 * descricao teve
	 * @param descricao A descricao do objeto
	 * @requires this.estahEmLeilao(descricao)
	 * @return Valor da licitacao mais recente que o objeto com a 
	 *         descricao dada teve; se ainda nao houve nenhuma, 
	 *         retorna o seu valor inicial de licitacao
	 */
	public double licitacaoMaisRecente(String descricao) {
		return objeto(descricao).valorLicitacaoMaisRecente();
	}
	
	/**
	 * Registar licitacao de um objeto com uma dada descricao
	 * @param descr A descricao do objeto a licitar
	 * @param valor Valor de licitacao
	 * @param cliente Nome do cliente que fez a licitacao
	 * @requires this.estahEmLeilao(descr) &&
	 *           valor > this.licitacaoMaisRecente(descr)
	 */
	public void licitarObjeto(String descr, double valor, String cliente) {
		objeto(descr).novaLicitacao(cliente,valor);
	}
	
	/**
	 * Registar licitacao do conjunto de objetos em leilao
	 * @param valor Valor da licitacao
	 * @param cliente Nome do cliente que fez a licitacao
	 * @requires valor > this.licitacaoConjuntoMaisRecente()
	 * @ensures uma parcela igual da diferenca entre o valor oferecido  
	 *          e a soma das ultimas licitacoes dos objetos eh 
	 *          adicionada ...
	 */
	public void licitarConjunto(double valor, String cliente) {
		double total = licitacaoConjuntoMaisRecente();
		double aMais = (valor - total) / objetos.size();
		for (String s : this.objetos.keySet()) {
			ObjetoALeiloar objeto = this.objetos.get(s);
			double valorIndividual = objeto.valorLicitacaoMaisRecente();
			objeto.novaLicitacao(cliente, valorIndividual + aMais); 
		}		
	}
	
	/**
	 * Valor total da licitacao mais recente dos objetos em leilao
	 * @return
	 */
	public double licitacaoConjuntoMaisRecente() {
		double total = 0;
		for (String s : this.objetos.keySet()) {
			ObjetoALeiloar objeto = this.objetos.get(s);
			total += objeto.valorLicitacaoMaisRecente();
		}
		return total;
	}
	
	/**
	 * Provoca a terminacao do leilao
	 */
	public void terminarLeilao() {
		this.aberto = false;
	}

	/**
	 * Todas as licitacoes de um dado objeto em leilao
	 * @param descricao O objeto de interesse
	 * @requires !this.estahActivo()
	 * @return
	 */
	public List<Par> licitacoes(String descricao) {
		return objeto(descricao).historico();		
	}

	/**
	 * As descricoes e valores base de licitacao deste leilao
	 * @return
	 */
	public List<Par> montra() {
		List<Par> result = new ArrayList<Par>();
		for(String s : this.objetos.keySet()) {
			ObjetoALeiloar objeto = this.objetos.get(s);
			result.add(new Par(objeto.descricao(), objeto.valorInicial()));
		}
		return result;
	}
	
	/**
	 * Representacao textual deste leilao
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Leilao " + this.descricao + "\n");
		for(String s : this.objetos.keySet()) {
			ObjetoALeiloar objeto = this.objetos.get(s);
			sb.append(objeto.toString() + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * A ultima licitacao de cada objeto em leilao 
	 * @requires !this.estahActivo()
	 * @return pares contendo cada um a descricao de um objeto e 
	 *         o cliente e o valor da sua ultima licitacao
	 */
	public Map<String,Par> ultimasLicitacoes() {
		Map<String,Par> result = new HashMap<String,Par>();
		for(String descr : this.objetos.keySet()) {
			ObjetoALeiloar objeto = this.objetos.get(descr);
			result.put(descr,new Par(objeto.clienteLicitacaoMaisRecente(), 
					             objeto.valorLicitacaoMaisRecente()));
		}
        return result;
	}

	/**
	 * A ultima licitacao de cada objeto em leilao 
	 * @return pares contendo cada um a descricao de um objeto e 
	 *         o valor da sua ultima licitacao
	 */
	@Override
	public Map<String, Double> valores() {
		//COMPLETAR!
	}
	
}
