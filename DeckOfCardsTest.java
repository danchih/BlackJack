//Daniela Akemi Hayashi
//Flavia Cristina Medeiros
//Giovana Salazar Alarcon

import java.util.Scanner;

public class DeckOfCardsTest
{
   // execute application
   public static void main(String[] args)
   {
      char m;
      Scanner input = new Scanner(System.in); 
      do{
      BlackJack myBlackJack = new BlackJack();
      myBlackJack.jogar();
      System.out.print("\nJogar novamente (s/n): ");
      m = input.next().charAt(0);               // Faz a leitura da proxima linha
      }while(m == 's');
   } 
} // end class DeckOfCardsTest

