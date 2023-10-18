import java.util.Scanner;

public class BlackJack {
    private int num;
    private int max = 10;
    private Card[][] jogador;
    private int[] pontuacoes;
    private int[][] valorcartas;
    private boolean[] parou;
    private int maiorPontoEmpate;

    // METODO QUE SERA CHAMADO NA MAIN ==============================================================================================================
    public void jogar() {
        System.out.print("\n----------------------------------------------- BLACKJACK -----------------------------------------------\n");
        DeckOfCards myDeckOfCards = new DeckOfCards();
        myDeckOfCards.shuffle();                // Embaralha o baralho
        num = quantjogadores();                 // Recebe a quantidade de jogadores
        distribuiCartas(myDeckOfCards.deck);    // Distribui as cartas 
        int novaCartaIndex;                     // Se tiverem dois jogadores jogando, já terá sido distribuidas 4 cartas 

        // Realiza a lógica do jogo
        System.out.print("\n\n----------------------------------------- RECEBIMENTO DE CARTAS -----------------------------------------\n");
        while (true) {                          // Continua até que todos parem
            for (int i = 0; i < num; i++) {
                novaCartaIndex = 2;
                System.out.printf(" \nJogador %d: ", i+1);

                while (!parou[i]) {             // Verifica se o jogador na posicao i não parou 
                    if (deveParar(i)) {         // Verifica se jogador i deve parar, caso true imprime a mensagem abaixo
                        System.out.printf("%n- Parou %n", i+1);
                        parou[i] = true;        // Informa que o jogador i parou

                    } else {                    // Se o jogador não parou 
                        jogador[i][novaCartaIndex] = myDeckOfCards.dealCard();          // Distribui ao jogador uma carta que ainda não foi distribuida
                        valorcartas[i][novaCartaIndex] = jogador[i][novaCartaIndex].getValor(); // Atribui ao valor das cartas o valor da nova carta
                        pontuacoes[i] = calcularPontuacao(i);                           // Calcula a pontuacao do jogador
                        System.out.printf("%n- Recebeu carta: ", i+1);  // Imprime cartas recebida
                        System.out.print(jogador[i][novaCartaIndex]);
                        novaCartaIndex++;       // O numero da carta index recebe mais um
                    }
                }
            }

            int vencedor = determinarVencedor();        // Vencedor recebe index de quem ganhou
            if (vencedor != -1 || todosPararam()) {     // Se vencedor for diferente de -1, ou se todos pararem imprime os resultados
                exibirResultados(vencedor);
                break;
            }
        }
    }

    // METODO QUE VERIFICA A QUANTIDADE DO JOGADORES ================================================================================================
    public int quantjogadores() {
        String m;
        int n = 0;
        Scanner input = new Scanner(System.in); 

        do{    
            System.out.print("\nDigite a quantidade de jogadores (maximo 10): ");
            m = input.nextLine();               // Faz a leitura da proxima linha
            n = Integer.parseInt(m);            // Transforma o m em inteiro
        } while(n > 10 || n <= 0);              // quantidade maxima de jogadores permitida e 10
        return n;
    }

    // METODO QUE FAZ A DISTRIBUICAO DE CARTAS ======================================================================================================
    public void distribuiCartas(Card[] deck) {
        jogador = new Card[num][max];        // Máximo de 10 cartas por jogador
        valorcartas = new int[num][max];
        pontuacoes = new int[num];          // Pontuacao de cada jogador
        parou = new boolean[num];           // Se o jogador parou ou não
        int count = 0;

        for (int i = 0; i < num; i++) {                         // Faz a dustribuicao para cada jogador 
            System.out.printf("%n%nCartas jogador %d:", i+1);
            for(int j=0; j < 2; j++){
                jogador[i][j] = deck[count];                    // Os jogadores recebem suas duas primeiras cartas
                count++;                                        // count representa posicao já utilizada do vetor deck
                valorcartas[i][j] = deck[count].getValor();     // Recebe o valor das cartas de cada jogador
                System.out.println();
                System.out.print("- "+ deck[count]);            // Imprime as cartas
            }
            pontuacoes[i] = calcularPontuacao(i);               // Calcula pontuacao de cada jogador
        }
    }

    // METODO QUE CALCULA A PONTUACAO ================================================================================================================
    public int calcularPontuacao(int jogadorNum) {
        int totalPontos = 0;                            // Inicializa os pontos
        int quantAces = 0;                              // Inicializa a quantidade de As 

        for (int i = 0; i < max; i++) {                 // Percorre os valores das cartas de cada jogador 
            if (valorcartas[jogadorNum][i] != 0) {      // Verifica se o valor da carta é diferente de zero, ou seja, um número valido
                int valor = valorcartas[jogadorNum][i];
                if (valor == 11) {                      // Se for um Ás, já que foi estabelecido que o valor dessa carta é 11
                    quantAces++;            
                    totalPontos += 11;                  // Acrescenta 11 ao total de pontos 
                } else {
                    totalPontos += valor;               // Soma ao total de pontos o valor da carta quando não for um As
                }
            }
        }

        // Verifica se ajustar o valor dos Aces é necessário
        while (totalPontos > 21 && quantAces > 0) {     // Verifica se o totaldepontos ultrapassa 21 e se a quantidade de Aces é maior que 0
            totalPontos -= 10;                          // Ajusta o valor do Ás de 11 para 1
            quantAces--;                                // Decrementa o número de Aces
        }

        return totalPontos;
    }

    // METODO PARA VERIFICAR SE O JOGADOR DEVE PARAR DE RETIRAR CARTAS ===============================================================================
    public boolean deveParar(int jogadorNum) {        
        return pontuacoes[jogadorNum] >= 17;        // O jogador para quando atinge 17 pontos ou mais
    }

     // METODO QUE RETORNA TRUE APENAS PARA OS JOGADORES QUE PARAREM =================================================================================
    public boolean todosPararam() {
        for (boolean parar : parou) {       // Para cada elemento do vetor parou verifica a condicao abaixo
            if (!parar) {                   // Verifica se um jogador não parou        
                return false;               // caso nao tenha parado, retorna false
            }
        }
        return true;                        // Se todos os jogadores pararem retorna true
    }

    // METODO QUE DETERMINA A MAIOR PONTUACAO DO VENCEDOR ============================================================================================
    public int determinarVencedor()
    {
        int maiorPontuacao = -1;                // Inicializa maiorPontuacao com -1, pois não existe essa pontuacao         
        maiorPontoEmpate = 0;
        for (int i = 0; i < num; i++)           // Verifica todos os jogadores
        {         
            int mEmpate = pontuacoes[i];  
            
            if(pontuacoes[i] > maiorPontuacao && pontuacoes[i] <= 21){   // Verifica se a pontuacao do jogador 'i' e maior que a pontuacao atual e se e menor que 21
                maiorPontuacao = pontuacoes[i];
            }
            for(int j=0; j < num; j++)          // Utiliza-se um segundo FOR para comparar a pontuacao do jogador atual com os demais jogadores
            {           
                if(pontuacoes[j] == mEmpate  && i != j && pontuacoes[j] <= 21){     // Caso a pontuacao de outros jogadores for igual a do jogador 'i',e menor que 21
                    if(mEmpate > maiorPontoEmpate)                                  // Compara se a pontuacao de empate e maior que a maior pontuacao
                        maiorPontoEmpate = mEmpate;                                 // Caso seja, guarda ela
                }
            }
        }    
        return maiorPontuacao;                  // Retorna a maior pontuacao
    }

    // METODO QUE IMPRIME OS RESULTADOS DO JOGO =======================================================================================================
    public void exibirResultados(int vencedor)
    {
        System.out.println("\n--------------------------------------------- RESULTADOS ---------------------------------------------\n");
        
        // Imprime a pontuacão de cada um dos jogadores
        for (int i = 0; i < num; i++) { 
            System.out.printf("Jogador %d - Pontuação: %d%n", i + 1, pontuacoes[i]);
        }

        if (vencedor != -1) {                                  // Verifica se existe algum vendedor   
            if(vencedor > maiorPontoEmpate){                   // Caso o a pontuacao do vencedor seja maior que o dos que empataram ele imprime o unico vencedor
                for (int i = 0; i < num; i++) {
                    if(pontuacoes[i] == vencedor)
        	            System.out.printf("%nO Jogador %d venceu com %d pontos!", i+1, vencedor);
                }
            }else{                                             // Caso contrario, imprime os jogadores que empataram
                System.out.print("\nOs Jogadores ");

                for (int i = 0; i < num; i++) {                // Faz a busca para encontrar os indices dos jogadores que empataram
                    if(pontuacoes[i] == maiorPontoEmpate)
                        System.out.printf("%d, ", i+1);     
                }
                System.out.printf("empataram com %d de pontos!", maiorPontoEmpate);
            }
        }  
        else{                                                 // caso nao tenha nenhum vencedor (vencedor = -1), imprime a mensagem
            System.out.println("Todos os jogadores estouraram! Não há vencedor.");
        }
        System.out.println("\n\n------------------------------------------------------------------------------------------------------");
    }

}// Fim da classe BackJack