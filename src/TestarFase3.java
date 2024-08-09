import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
/**
 * Programa para testar os interfaces e classes feitos
 * pelos alunos na Fase 3 do trabalho de PCO
 * @author isabel nunes
 * @date Novembro 2019
 */
public class TestarFase3 {

	/**
	 * Comeca por ler a informacao de 4 ficheiros de texto dados:
	 * - umObjeto.txt contendo informacao sobre um objeto do
	 *   tipo ObjetoALeiloar;
	 * - objetosA.txt e objetosB.txt contendo cada um informacao sobre 
	 *   objetos para leilao;
	 * - circulos.txt contendo informacao sobre circulos eleitorais 
	 * 
	 * Com essas informacoes, sao criados objetos dos tipos ObjetoALeiloar,
	 * Circulo, Leilao e EleicoesHondt.
	 * 
	 * Estes objetos sao guardados em variaveis do tipo Quantificavel 
	 * (os 5 elementos de um array). 
	 * 
	 * Eh pedido ao utilizador o nome de uma classe representando um tipo
	 * especifico de OutputTextual; eh usado carregamento dinamico de 
	 * classes para criar um objeto dessa classe.
	 * 
	 * Finalmente eh pedido a esse objeto a representacao textual dos 
	 * 5 objetos Quantificavel e imprimem-se os resultados no ecra
	 * 
	 * @param args Nao utilizado
	 * @throws FileNotFoundException no caso de nao encontrar algum dos 
	 *                               ficheiros indicados
	 */
	public static void main(String[] args) throws FileNotFoundException {
	
		// O array de elementos do tipo Quantificavel
		Quantificavel[] myQuants = new Quantificavel[5];

		//Ler informacao para criacao de uma instancia de ObjetoALeiloar
		// A classe ObjetoALeiloar implements Quantificavel
		myQuants[0] = lerInfoUmObjeto("umObjeto.txt");
			
		// Construir dois leiloes
		// A classe Leilao implements Quantificavel
		myQuants[1] = constroiLeilao("objetosA.txt", "Leilao de mobilia",1);
		myQuants[2] = constroiLeilao("objetosB.txt", "Pecas avulso",0);

		// Ler informacao sobre circulos eleitorais a partir de ficheiro
		String[] partArray = {"PART 1","PART 2","PART 3","PART 4","PART 5"};
		List<Circulo> circulos = lerInfoCirculos(partArray,"circulos.txt");

		// A classe Circulo implements Quantificavel
		myQuants[3] = circulos.get(0);
			
		// Construir eleicoes usando esses circulos eleitorais e partidos
		// A classe Eleicoes implements Quantificavel
		myQuants[4] = constroiEleicoes(partArray, circulos, 
				                       "Eleitoralia-Hondt");

		// Se quiser imprimir o resultado do metodo toString sobre os   
		// objetos acabados de criar para verificar que tudo correu bem,
		// retire o comentario da proxima instrucao
		// imprimirComToString(myQuants);		
					
		// Pedir ao utilizador qual o tipo de OutputTextual que quer usar
		// para representar textualmente os quantificaveis e construir um
		// objeto desse tipo
		OutputTextual representador = pedirTipoOutput();	
			
		// Imprimir no System.out as representacoes dos varios Quantificaveis
		// usando o OutputTextual do tipo escolhido pelo utilizador
		for (Quantificavel quant : myQuants) {
		     System.out.println("=========  Representacao de: " + 
		                        quant.getClass() +
		                        " usando o OutputTextual " + 
		                        representador.getClass());
		     System.out.println(representador.outputTexto(quant));			
		}

	}// Fim do metodo main

/*****************************************************************************/
/***************************   OUTROS METODOS   ******************************/
/*****************************************************************************/

	/**
	 * Um objeto a leiloar lido a partir de um ficheiro de texto
	 * com um dado nome
	 * @param nomeFich Nome completo do ficheiro onde estah a informacao
	 *                 sobre o objeto a leiloar
	 * @return Um ObjetoALeiloar construido a partir de informacao lida do 
	 *         ficheiro de nome nomeFich
	 * @requires nomeFich != null
	 * @throws FileNotFoundException no caso de nao encontrar o 
	 *                               ficheiro indicado 
	 */
	private static ObjetoALeiloar lerInfoUmObjeto(String nomeFich) 
			                                 throws FileNotFoundException{
		Scanner leitor = new Scanner(new FileReader(nomeFich));
		ObjetoALeiloar resultado = null;
      
		String descricao = leitor.nextLine();
		double inicial = leitor.nextDouble();
		resultado = new ObjetoALeiloar(descricao,inicial);
		leitor.nextLine();
        while (leitor.hasNextLine()) {
    		    String cliente = leitor.nextLine();
    		    double licit = leitor.nextDouble();
    			if(leitor.hasNextLine()) {
    				leitor.nextLine();
    			}
    			resultado.novaLicitacao(cliente, licit);        	     
        }
        leitor.close();
        return resultado;		
	}

	/**
	 * Lista de Circulos lidos a partir de um ficheiro de texto
	 * com um dado nome
	 * @param partidos Os nomes dos partidos
	 * @param nomeFich Nome completo do ficheiro onde estah a informacao
	 *                 sobre os circulos
	 * @return Informacao lida do ficheiro de nome nomeFich, numa lista de 
	 *         objetos do tipo Circulo
	 * @requires nomeFich != null
	 * @throws FileNotFoundException no caso de nao encontrar o 
	 *                               ficheiro indicado 
	 */
	private static List<Circulo> lerInfoCirculos(String[] partidos,String nomeFich) 
			                                           throws FileNotFoundException{
		Scanner leitor = new Scanner(new FileReader(nomeFich));
		ArrayList<Circulo> resultado = new ArrayList<Circulo>();
      
        while (leitor.hasNextLine()) {
			String descricao = leitor.nextLine();
			int numDeps = leitor.nextInt();
			Map<String,Integer> votos = new HashMap<String,Integer>();
			for (String part : partidos) {
				int votosPart = leitor.nextInt();
				votos.put(part, votosPart);
			}
			if(leitor.hasNextLine()) {
				leitor.nextLine();
			}
	        Circulo circ = new Circulo(descricao,numDeps,votos);
	        resultado.add(circ);
        }
        leitor.close();
        return resultado;		
	}

	/**
	 * Lista de pares (string, double) lidos a partir de um ficheiro de 
	 * texto com um dado nome
	 * @param nomeFich Nome completo do ficheiro
	 * @return Informacao lida do ficheiro de nome nomeFich, numa lista de 
	 *         pares (String, double)
	 * @requires nomeFich != null
	 * @throws FileNotFoundException no caso de nao encontrar o 
	 *                               ficheiro indicado 
	 */
	private static ArrayList<Par> lerInfoObjetos(String nomeFich) 
			                            throws FileNotFoundException {
		Scanner leitor = new Scanner(new FileReader(nomeFich));
		ArrayList<Par> resultado = new ArrayList<Par>();

        while (leitor.hasNextLine()) {
			String descricao = leitor.nextLine();
			double valor = leitor.nextDouble();
			leitor.nextLine();
			resultado.add(new Par(descricao, valor));
        }
        leitor.close();
        return resultado;
	}

	/**
	 * Simular 17 licitacoes aleatorias (individuais ou de conjunto).
     * O valor da licitacao eh igual ao valor da licitacao mais recente
	 * adicionado de um valor aleatorio que depende daquele valor
	 * Consideramos que as descricoes dos objetos em leilao sao a palavra 
	 * "Objeto" seguida de um espaco e um inteiro de 1 a um numero dado.
	 * Consideramos que existem 8 clientes no leilao.
	 * @param leilao O leilao onde licitar
	 * @param semente A semente para o gerador de aleatorios
	 * @param nrObjetos Numero de objetos em leilao
	 */
	private static void fazerLicitacoes(Leilao leilao, int semente, 
			                            int nrObjetos) {
		// usamos semente para garantir valores
        // iguais em cada execução do programa
		Random gerador = new Random(semente);  
		for(int i = 1 ; i <= 17 ; i++) {
			boolean objetoOuConjunto = gerador.nextBoolean();
			double aumento = gerador.nextDouble();
			String cliente = "Cli" + (gerador.nextInt(8) + 1);
			if(objetoOuConjunto) {
				double maisRecente = leilao.licitacaoConjuntoMaisRecente();
				double oferta = maisRecente + aumento * maisRecente;
				leilao.licitarConjunto(oferta, cliente);
			} else {
				String objeto = "Objeto " + (gerador.nextInt(nrObjetos) + 1);
				double maisRecente = leilao.licitacaoMaisRecente(objeto);
				double oferta = maisRecente + aumento * maisRecente;
				leilao.licitarObjeto(objeto, oferta, cliente);
			}
		}
	}

	/**
	 * Constroi um leilao a partir de informacao num ficheiro sobre objetos
	 * a leiloar; Simula licitacoes e termina o leilao
	 * @param nomeFich O nome do ficheiro de texto
	 * @param nomeLeilao O nome a dar ao leilao
	 * @param semente A semente para o gerador de numeros aleatorios a usar
	 *                para criar licitacoes aleatorias
	 * @return Um novo leilao
	 * @throws FileNotFoundException no caso de nao encontrar o 
	 *                               ficheiro indicado  
	 */
	private static Leilao constroiLeilao(String nomeFich, String nomeLeilao, 
			                             int semente) 
			                            		 throws FileNotFoundException {
		//Ler informacao sobre os objetos a leiloar
		List<Par> objetosLeilao = lerInfoObjetos(nomeFich);

		// Criacao do leilao
		Leilao leilao = new Leilao(nomeLeilao, objetosLeilao);

		// Fazer licitacoes aleatorios no novo leilao
		fazerLicitacoes(leilao, semente, leilao.montra().size());

		// Dar o leilao por terminado
		leilao.terminarLeilao();

		return leilao;
	}

	/**
	 * Constroi uma instancia de EleicoesHondt a partir de informacao sobre
	 * os nomes dos partidos concorrentes, os circulos eleitorais e o nome
	 * das eleicoes
	 * @param nomesPartidos Os nomes dos partidos concorrentes
	 * @param circulos Os circulos eleitorais
	 * @param nomeEleicoes A descricao das eleicoes
	 * @return nomesPartidos != null && circulos != null 
	 */	
	private static Eleicoes constroiEleicoes(String[] nomesPartidos, 
			                                 List<Circulo> circulos,
			                                 String nomeEleicoes) {
		// Criacao de um objeto do tipo Eleicoes
		List<String> partidos = Arrays.asList(nomesPartidos);
		Eleicoes eleicao = new EleicoesHondt(nomeEleicoes, partidos);
		// Adicionar circulos ao objeto eleicoes
		for (Circulo circ : circulos) {
			eleicao.addCirculo(circ);
		}
		return eleicao;
	}

	/**
	 * Pedir ao utilizador qual o tipo de OutputTextual que quer usar
	 * e construir e devolver um objeto desse tipo
	 * @return Um objeto do tipo OutputTextual cujo subtipo eh decidido
	 *         pelo utilizador
	 */
	private static OutputTextual pedirTipoOutput() {
		Scanner leitor = new Scanner(System.in);		
		System.out.println("Tipos de OutputTextual acessíveis:");
		// invoca metodo que retorna lista de OutputTextuais existentes
		List<String> tiposOutput = tiposOutputExistentes();
		for(String s : tiposOutput) {
			System.out.println(s);
		}
		System.out.println("Qual é o que que deseja?");
		String nome = leitor.nextLine();
		leitor.close();
		return obtemOutputTextual(nome);
	}

	/**
	 * Lista dos nomes das subclasses de OutputTextual existentes, que
	 * estao referidas no ficheiro configuracao.properties
	 * @return
	 */
	private static List<String> tiposOutputExistentes() {
		
	    	String classesOutputTextuais = "";
	    	String[] nomes = {"GraficoPontos"};
	    	Properties prop = new Properties ();	
	  	try {
			prop.load(new FileInputStream("configuracao.properties") );
			classesOutputTextuais = prop.getProperty("outputTexters");	
		  	nomes = classesOutputTextuais.split(";");
		} catch (IOException e1) {
			System.out.println(classesOutputTextuais);
		}	
		return Arrays.asList(nomes);
	}

	/**
	 * Constroi um OutputTextual a partir do nome da classe
	 * (carregamento dinamico de classes)
	 * @param nome O nome do OutputTextual a criar
	 * @requires nome != null
	 * @return Um OutputTextual do tipo indicado por nome, caso exista; 
	 *         caso contrario eh retornado um do tipo dado por omissao
	 */
	private static OutputTextual obtemOutputTextual(String tipoOutput) {
		OutputTextual result;
		try {
			// Quando as classes estao no default package.
			// Se nao estiverem, terah que se acrescentar o path.
			// Exemplo: "projeto3." + tipoOutput
			result = (OutputTextual) Class.forName(tipoOutput).newInstance();
	   	} catch (Exception e) {	
			System.out.println("Exception " + tipoOutput);
			result = new TextoCorrido();		
	  	}
		return result;
	}

	/**
	 * Imprimir varios objetos usando o resultado do seu metodo toString
	 * @param aImprimir Os objetos a imprimir
	 * @requires aImprimir != null
	 */
	private static void imprimirComToString(Quantificavel[] aImprimir) {
		for (Quantificavel quant : aImprimir) {
		     System.out.println("===============  toString de: " + 
		                                 quant.getClass() + " ============");
		     System.out.println(quant.toString());			
		}
	}

}
