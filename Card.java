public class Card 
{
   private final String face; // face of card ("Ace", "Deuce", ...)
   private final String suit; // suit of card ("Hearts", "Diamonds", ...)
   private final int valor;

   // two-argument constructor initializes card's face and suit
   public Card(String face, String suit, int valor)
   {
      this.face = face;
      this.suit = suit; 
      this.valor = valor;
   } 

   public int getValor() {
      return valor;
   }

   // return String representation of Card
   @Override
   public String toString() 
   { 
      return face + " of " + suit;
   } 
} // end class Card