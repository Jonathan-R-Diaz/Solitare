import java.util.Random;
import java.util.Stack;

public class Deck {
    protected Card[] cardArray = new Card[52];
    protected Stack<Card> cardStack = new Stack<>();
    private Boolean shuffled = false;

    Deck() {
        int card = 0;
        for (int v = 13; v > 0; v--) {
            for (int s = 1; s <= 4; s++) {
                cardArray[card++] = new Card(v, s);
            }
        }

        Shuffle(1000);

        StackMaker();
        if (!shuffled)
            System.out.println("NOT SHUFFLED");

    }

    Deck (int i){}

    public void Display(){
        for (int i = 0; i < 52; i++)
            cardArray[i].Display();
    }

    public void DisplayStack(){
        Stack<Card> temp = new Stack<>();

        while (!cardStack.empty()) {
            cardStack.peek().Display();
            temp.push(cardStack.peek());
            cardStack.pop();
        }

        StackMaker();
    }

    public void Shuffle(int count) {
        shuffled = true;
        //Declarations
        Random rand = new Random();
        boolean[][] cardsProduced = new boolean[13][4];
        int cardsFinished = 0;

        //Shuffle Loop
        for (int i = 0; i < cardArray.length; i++){
            int bound = cardArray.length - i;
            int index = rand.nextInt(bound);
            Card temp = cardArray[bound - 1];
            cardArray[bound - 1] = cardArray[index];
            cardArray[index] = temp;
        }

        //Ruffle Shuffle
        Card [] tempArray = new Card[cardArray.length];

        for (int i = 0; i < cardArray.length; i++)
            tempArray[i] = cardArray[i];

        int j = 0;
        for (int i = cardArray.length - 1; i >= 0; i--){
            if (i % 2 == 0)
                cardArray[i] = tempArray[i/2];
            else
                cardArray[i] = tempArray[i + j++];
        }
        if (count > 1)
            Shuffle(count - 1);
        else
            return;
    }

    public void StackMaker() {
        cardStack.clear();
        for (int i = 51; i >= 0; i--){
            cardStack.push(cardArray[i]);
        }
    }
}
