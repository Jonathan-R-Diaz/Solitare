import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Deck {
    protected Card[] cardArray = new Card[52];
    protected Stack<Card> cardStack = new Stack<>();
    private Boolean shuffled = false;

    Deck() {
        int card = 0;
        for (int v = 1; v < 14; v++) {
            for (int s = 1; s <= 4; s++) {
                cardArray[card++] = new Card(v, s);
            }
        }

        this.Shuffle(3);

        StackMaker();
        if (!shuffled)
            System.out.println("NOT SHUFFLED");
    }

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
        System.out.println("Shuffling");
        shuffled = true;
        //Declarations
        Random rand = new Random();
        boolean[][] cardsProduced = new boolean[13][4];
        int cardsFinished = 0;

        //Shuffle loop
        while (cardsFinished != 52) {
            //Trial card
            int v = rand.nextInt(13) + 1, s = rand.nextInt(4) + 1;
            Card tempCard = new Card(v, s);

            if (!cardsProduced[--v][--s]) {
                this.cardArray[cardsFinished++] = tempCard;
                cardsProduced[v][s] = true;
            }
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
