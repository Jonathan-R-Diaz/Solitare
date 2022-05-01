import javax.swing.*;
import java.awt.*;

public class Card {
    private int v, s;
    private String value, suit;
    private boolean isRed;
    private JLabel front, back, current;
    private boolean isUp;

    Card(int v, int s){
        int vc = 0, sc = 0;
        //Sets value
        {
            this.v = v;
            if (1 < v & v < 11) {
                value = Integer.toString(v);
                vc = 3;
                if (v < 10)
                    vc++;
            }
            else {
                switch (v) {
                    case 1 -> value =  "ace";
                    case 11 -> value = "jack";
                    case 12 -> value = "queen";
                    case 13 -> value = "king";
                    default -> value = "JOKER";
                }
                switch (v) {
                    case 1 -> vc = 2;
                    case 11 -> vc = 1;
                    case 12 -> vc = 0;
                    case 13 -> vc = 1;
                    default -> vc = 0;
                }
            }
        }
        //Sets suit
        this.s = s;
        switch (s) {
            case 1 -> {
                suit = "hearts";
                isRed = true;
                sc = 2;
            }
            case 2 -> {
                suit = "diamonds";
                isRed = true;
                sc = 0;
            }
            case 3 -> {
                suit = "spades*";
                isRed = false;
                sc = 1;
            }
            case 4 -> {
                suit = "clovers*";
                isRed = false;
                sc = 1;
            }
            default -> {
                suit = "RED";
                isRed = true;
                sc = 5;
            }
        }
        for (int i = 0; i < sc + vc; i++){
            suit += ' ';
        }
    }

    public void Display(){
        if (isUp)
            System.out.print(value + " of " + suit);
        else
            System.out.print("Card is face down");
    }

    public void isUp(){
        isUp = true;
    }

    public void isDown(){
        isUp = false;
    }

    public boolean isCurrentlyUp(){
        return isUp;
    }
}
