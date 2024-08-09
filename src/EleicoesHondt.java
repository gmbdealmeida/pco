import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa eleicoes em que os deputados eleitos
 * para cada partido sao calculados pelo metodo de
 * Hondt
 * 
 * Estah incompleta!
 * 
 * @author isabel nunes
 * @date Novembro 2019
 */
public class EleicoesHondt extends Eleicoes {

	/**
	 * Inicializar as eleicoes
	 * @param descricao A designacao destas novas eleicoes
	 * @param partidos As siglas dos partidos concorrentes
	 * @requires descricao != null && partidos != null && 
	 *           partidos.length > 0
	 */
    public EleicoesHondt(String descricao, List<String> partidos) {
        // COMPLETAR!
    }

	/**
     * O numero de deputados eleitos para cada partido
	 * @return pares contendo a sigla do partido e o numero de 
	 *         deputados eleitos
	 */
	@Override
	public Map<String, Double> valores() {
		// COMPLETAR!
	}

	/**
	 * Resultados: numero de deputados por partido pelo metodo de Hondt
	 * @return - Tabela contendo pares <part,valor> onde valor representa o numero 
	 *           de deputados eleitos para o partido part
	 */
	private Map<String, Integer> resultados() {
		// Numero total de deputados
		int max = numTotalDeputados();
        // Nomes dos partidos
		List<String> partidos = this.partidos();
		// Nomes dos circulos eleitorais
		List<String> circulos = this.circulosEleitorais();
		// Tabela de resultados: numero de deputados para cada partido
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (String part : partidos) {
			result.put(part, 0);
		}
		int [][]hondt = new int [max][partidos.size()];
		int [] depCirc = new int [partidos.size()];
		for (int i = 0; i < depCirc.length; i++) {
			depCirc[i] = 0;
		}
		for (String circ : circulos){
			int maxDep = this.deputadosAEleger(circ);
			int p = 0;
			for (String part : partidos) {
				hondt[0][p] = this.numeroVotos(circ, part);
				p++;
			}
			//
			for (int i = 1; i < maxDep; i++) {
				for (int j = 0; j < partidos.size(); j++) {
					hondt[i][j] = hondt[0][j]/(i+1);
				}
			}
			//
			for (int i = 0; i < maxDep; i++){
				int [] indMaior = indicesDoMax(hondt,maxDep,partidos.size());
				depCirc[indMaior[1]] += 1;
				hondt[indMaior[0]][indMaior[1]] = 0;			
			}
			//
			p = 0;
			for(String nomePart : result.keySet()) {
				int valor = result.get(nomePart) + depCirc [p];
 				result.put(nomePart, valor);
 				p++;
			}
			//
			for (int i = 0; i < depCirc.length; i++) {
				depCirc[i] = 0;
			}
		}		
		return result;
	}
		
	/**
	 * Resultados: linha e coluna do elemento maximo de uma matriz
	 * @param h - a matriz
	 * @param l - numero de linhas da matriz a considerar
	 * @param c - numero de colunas da matriz a considerar
	 * @return - vector com dois elementos: a linha e a coluna do 
	 *                                      maior elemento de h
	 */
	private static int [] indicesDoMax (int [][] h,int l,int c){
		int [] result = new int [2];
		int max = 0;
		int lMax = 0;
		int cMax = 0;
		for (int i = 1; i < l; i++)
			for (int j = 0; j < c; j++)
				if (h[i][j] > max){
					max = h[i][j];
					lMax = i;
					cMax = j;
				}
		result [0] = lMax;
		result [1] = cMax;
		return result;
	}

	/**
	 * Numero total de deputados a eleger
	 * @return
	 */
	private int numTotalDeputados() {
		int result = 0;
		for(String circ : this.circulosEleitorais()) {
			result += this.deputadosAEleger(circ);
		}
		return result;
	}
}
