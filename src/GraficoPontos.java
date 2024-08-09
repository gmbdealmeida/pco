import java.util.List;
import java.util.Map;

/**
 * Representa objetos que sabem representar textualmente um 
 * grafico de pontos correspondente a uma sequencia de pares
 * 
 * Estah incompleta!
 * 
 * @author isabel nunes
 * @date Novembro 2019
 */
public class GraficoPontos implements OutputTextual {
	
	// Numero maximo de linhas (e de valores) do grafico
	public static final int MAXIMO_LINHAS = 20;
	// Numero maximo de colunas do grafico
	public static final int MAXIMO_COLUNAS = 80;
	
    // Numero de linhas que o grafico vai ter
	private int nLinhas;
    // Numero de colunas que o grafico vai ter
	private int nColunas;
	// O simbolo a ser usado para os pontos do grafico
	private char simbolo;
	// Os textos
	private String[] textos;
	// Os valores associados aos textos
	private double[] valores;
	// Maximo e minimo dos valores
	private double[] maxMin;
	// Numero de espacos atribuido a cada valor horizontalmente
	private int deltaX;	

	/**
	 * Inicializa o novo grafico com MAXIMO_LINHAS linhas,
	 * MAXIMO_COLUNAS colunas e '*' como simbolo
	 */
	public GraficoPontos() {
		this.nLinhas = MAXIMO_LINHAS;
		this.nColunas = MAXIMO_COLUNAS;
		this.simbolo ='*';
	}

	/**
	 * Permite definir valores diferentes dos que foram usados no 
	 * construtor, para o numero de linhas e colunas e simbolo 
	 * a usar
	 * @param linhas O numero de linhas que o grafico deve ter
	 * @param colunas O numero de colunas que o grafico deve ter
	 * @requires linhas > 1 && linhas <= MAXIMO_LINHAS &&
	 *           colunas > 1 && colunas <= MAXIMO_COLUNAS
	 */
	public void defineParametros(int linhas, int colunas, char simbolo) {
		this.nLinhas = linhas;
		this.nColunas = colunas;
		this.simbolo = simbolo;
	}	

	/**
	 * A representacao textual de um grafico de pontos de 
     * um dado Quantificavel
	 * @param q O Quantificavel
	 * @requires this.ehRepresentavel(q)
	 * @return A representacao textual de um grafico de pontos de q
	 */
	@Override
	public String outputTexto(Quantificavel q) {
        // COMPLETAR!

		// Invocar o metodo valores() sobre q para obter os
		// textos e os valores a representar
		
		// preencher os arrays this.textos e this.valores
		// a partir da informacao obtida em cima
		
		// Inicializar o array this.maxMin com os valores maximo
		// e minimo dos valores a representar (repare que existe
		// uma funcao double[] maxMin (double[] v) nesta classe

		// Inicializar o atributo this.deltaX com o resultado da
		// divisao de this.nColunas pelo numero de valores a 
		// representar

		// Finalmente, retornar o resultado de invocar o metodo
		// String plotRepresentation() definido nesta classe.
		
	}

	/**
	 * A representacao textual de um grafico de pontos de 
     * uma dada lista de pares
	 * @param l A lista de pares
	 * @requires this.ehRepresentavel(l)
	 * @return A representacao textual de um grafico de pontos delq
	 */
	@Override
	public String outputTexto(List<Par> l) {
		// Usar o mesmo raciocinio aplicado no metodo anterior
		
	}

	/**
	 * Um dado Quantificavel eh representavel?
	 * @param q O quantificavel
	 */
	@Override
	public boolean ehRepresentavel(Quantificavel q) {		
          // COMPLETAR!
	}

	/**
	 * Uma dada lista de pares eh representavel?
	 * @param l A lista de pares
	 */
	@Override
	public boolean ehRepresentavel(List<Par> l) {
        // COMPLETAR!
	}
	
	/**
	 * Representacao textual do eixo horizontal incluindo as legendas 
	 * correspondentes aos textos (eventualmente truncados)
	 * @param maxTamLegendaY Numero maximo de carateres ocupados pelas
	 *                       etiquetas do eixo vertical
	 * @return Representacao textual do eixo horizontal
	 */
	private String eixoDosX(int maxTamLegendaY) {
		// Objeto que nos vai ajudar a construir a representacao textual
		// do eixo horizontal
		StringBuilder sb = new StringBuilder();
		// Primeira linha
		sb.append(sequenciaChars(maxTamLegendaY + 2, ' '));
		sb.append("|");
		sb.append(sequenciaChars(MAXIMO_COLUNAS, '_'));
		sb.append("\n");
		// Segunda linha
		sb.append(sequenciaChars(maxTamLegendaY + 3, ' '));
		for(String texto : this.textos) {
			String reduzido = texto;
			if(texto.length() > deltaX - 2) {
				reduzido = texto.substring(0, this.deltaX - 2);
			}
			sb.append(sequenciaChars((this.deltaX - reduzido.length())/2, ' '));
			sb.append(reduzido);
			int compensa = (this.deltaX - reduzido.length()) % 2 == 0 ? 0 : 1;
			sb.append(sequenciaChars((this.deltaX - reduzido.length())/2 + compensa, ' '));
		}
		return sb.toString();
	}

	/**
	 * Linha horizontal correspondente a um dado intervalo
	 * @param simbolo O simbolo para representar os valores
	 * @param inferior O limite inferior do intervalo desta linha
	 * @param superior O limite superior do intervalo desta linha
	 * @param tamanhoLegendaY Numero de carateres ocupados pela legenda
	 *                        da linha
	 * @requires superior >= inferior
	 * @return Representacao textual da linha horizontal correspondente 
     *         ao intervalo definido por inferior e superior
	 */            
	private String linha(char simbolo, double inferior, double superior,
			                                      int tamanhoLegendaY) {
		// Objeto que nos vai ajudar a construir a representacao textual
		// da linha
		StringBuilder linha = new StringBuilder();
        // Acrescenta a etiqueta vertical
		linha.append(etiquetaVertical(inferior, superior, tamanhoLegendaY));
		// Acrescenta a linha do grafico propriamente dita
		for(double val : valores) {
			int espacos = this.deltaX / 2 - (this.deltaX % 2 == 0 ? 1 : 0);
			linha.append(sequenciaChars(espacos, ' '));
			if(val < superior && 
			   (val > inferior || Math.abs(val - inferior) < 0.01)) {
				linha.append(simbolo);
			} else {
				linha.append(" ");
			}
			linha.append(sequenciaChars(this.deltaX / 2, ' '));
		}		
		return linha.toString();
	}
	
	/**
	 * A etiqueta do eixo vertical correspondente a um dado intervalo
	 * de valores
	 * @param inferior Limite inferior do intervalo
	 * @param superior Limite superior do intervalo
	 * @param tamanhoLegendaY Numero maximo de carateres ocupados por 
	 *                        qualquer etiqueta vertical deste grafico
	 * @requires superior >= inferior
	 * @return A etiqueta do eixo vertical correspondente ao intervalo 
	 *         [inferior,superior[
	 */
	private String etiquetaVertical(double inferior, double superior, 
			                        int tamanhoLegendaY) {
		String intervalo = "[" + arredonda(inferior, 2) + "," + 
			                        arredonda(superior, 2) + "[";
		String espacosEsq = 
				sequenciaChars(tamanhoLegendaY - intervalo.length(), ' ');
		return espacosEsq + intervalo + " -|";
	}
	
	/**
	 * Valor arredondado com um dado número maximo de casas decimais
	 * @param val O valor a arredondar
	 * @param casas O numero maximo de casas decimais
	 * @requires casas >= 0
	 * @return O valor val arredondado a um maximo de casas casas decimais
	 */
	private static double arredonda(double val, int casas) {
		int arredondado = (int)((val + 0.005) * Math.pow(10, casas));
		return arredondado / Math.pow(10, casas);
	}

	/**
	 * O maximo e o minimo de um vetor de numeros
	 * @param v O vetor
	 * @return um vetor de duas posicoes cujo primeiro elemento eh o maximo
	 *         de v e o segundo elemento eh o minimo de v
	 */
	private static double[] maxMin (double[] v) {
		double[] resultado = new double[2];
		resultado[0] = v[0];
		resultado[1] = v[0];
		for(double d : v) {
			if (d > resultado[0]) {
				resultado[0] = d;
			}
			if (d < resultado[1]) {
				resultado[1] = d;
			}
		}
		return resultado;
	}
	
	/**
	 * Uma sequencia com um dado numero de carateres iguais a um dado char	
	 * @param n O numero de carateres da sequencia desejada
	 * @param c O carater a ser repetido
	 * @requires n > 0
	 * @return Uma sequencia com um n carateres iguais a c
	 */
	 private static String sequenciaChars(int n, char c) {
		StringBuilder sb = new StringBuilder();
		for(int i = 1 ; i <= n ; i++) {
			sb.append(c);
		}
		return sb.toString();
	 }

	/**
	 * Representacao em grafico de valores correspondentes a dados textos.
	 */
	private String plotRepresentation() {
		// O objeto que nos vai ajudar a construir a string com a
		// representacao textual do grafico
		StringBuilder resultado = new StringBuilder();
		// Intervalo numerico entre valores no eixo dos ys (valores)
		double deltaY = (this.maxMin[0] - this.maxMin[1]) / (this.nLinhas - 1);
		// Numero de carateres maximo ocupado pelas legendas do eixo vertical:
		// primeiro +3 eh do ponto e dois decimais; +1 eh para completar o
		// valor de log10 no calculo do numero de algarismos do inteiro;
		// *2 eh porque sao dois numeros; ultimo +3 eh dos parentesis e da
		// virgula
		int maxTamLegendaY = 
				  2 * (3 + (int) Math.log10(this.maxMin[0] + deltaY) + 1) + 3;
		double inferior = this.maxMin[0];
		// Construir grafico em texto, linha a linha
		for(int i = this.nLinhas ; i >= 1 ; i--) {
			resultado.append(linha(this.simbolo, 
					               arredonda(inferior, 2), 
					               arredonda(inferior + deltaY, 2), 
					               maxTamLegendaY));
			resultado.append("\n");
			inferior -= deltaY;
		}
		// Acrescentar eixo horizontal
		resultado.append(eixoDosX(maxTamLegendaY));
		resultado.append("\n");
		
		return resultado.toString();
	}
}
