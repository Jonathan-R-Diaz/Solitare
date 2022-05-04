import javax.swing.*;import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.SystemColor.text;

public class Grid extends Game implements ActionListener{

        public class Button {
            private String fullname;
            private  char r, c;
            private int row, col;
            private boolean initialized = false, isWaste;

            public void FullInit(){
                r = fullname.charAt(1);
                c = fullname.charAt(4);
                col = c - 48;

                if (Character.isDigit(r))
                    row = r - 48;
                else
                    row = r - 55;

                if (row > table.findFirstCard(col))
                    row = table.findFirstCard(col);

                initialized = true;
                isWaste = false;
                //System.out.println("full name: " + fullname + "\nCHAR [r][c]: [" + r + "][" + c + "]" + "\nINT [rows][col]: [" + row + "][" + col + "]");
            }
            public void reset(){
                fullname = "";
                r = 0;
                c = 0;
                row = 0;
                col = 0;
                initialized = false;
                isWaste = false;
            }

            public void setWaste(){
                isWaste = true;
            }

            public boolean isWaste(){
                return isWaste;
            }

            public int getRow() {
                return row;
            }

            public int getCol(){
                return col;
            }

            public boolean isInitialized(){
                return initialized;
            }
        }

        Button button1 = new Button();
        Button button2 = new Button();


        int counter = 0;
        Grid() {
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GridLayout gridLayout = new GridLayout(14, 7);
            setLayout(gridLayout);
            //setLocationRelativeTo(null);

            //Pile Grid
            PileButtonAdder("Draw");
            PileButtonAdder("Waste");
            PileButtonAdder("Display");
            PileButtonAdder("Hearts");
            PileButtonAdder("Diamonds");
            PileButtonAdder("Spades*");
            PileButtonAdder("Clovers*");
            //Card Grid
            for (int rows = 0; rows < 13; rows++)
                for (int cols = 0; cols < 7; cols++) {
                    if (rows > 9)
                        CardButtonAdder((char)(rows+55), (char)(cols+48));
                    else
                        CardButtonAdder((char)(rows+48), (char)(cols+48));
                }
            pack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            buttonReader(cmd);
        }

        public void buttonReader(String cmd){

            if (cmd == "Draw") {
                System.out.println("Drawing from draw pile...");
                Draw();
            }

            else if (cmd == "Display") {
                System.out.println("Displaying board...");
                DisplayTableOld();
            }

            else if (cmd == "Waste" || (counter == 1 && button1.isWaste()))
                if (counter == 0 && stackSize() > 0) {
                    System.out.println("Placing from draw pile...");
                    button1.setWaste();
                    counter++;
                }
                else {
                    if (stackSize() == 0){
                        System.out.println("Draw pile empty");
                        button1.reset();
                        button2.reset();
                        System.out.println("Buttons reset");
                        DisplayTableOld();
                }
                    else {
                        button2.fullname = cmd;
                        button2.FullInit();
                        counter = 0;
                        DrawToBoard(button2.col);
                        button1.reset();
                        button2.reset();
                    }
                }

            else if (cmd.charAt(0) == '[') {
                //A card has been clicked
                if (counter == 0)
                {
                    button1.fullname = cmd;
                    button1.FullInit();
                    counter++;
                    System.out.print("Card selected: ");
                    cardsOnTable[button1.row][button1.col].Display();
                    System.out.println();
                }
                else
                {
                    if (cmd.charAt(0) == '[' && !button1.isWaste())
                    {
                        button2.fullname = cmd;
                        button2.FullInit();
                        counter = 0;
                        moveExecuter();
                    }
                }
            }
            else
            {
                moveIntoFoundation(button1.row, button1.col, cmd);
                button1.reset();
                button2.reset();
                counter = 0;
                System.out.println("Both buttons and counter have been reset");
            }
        }

        public void moveExecuter(){
            moveCard(button1.row, button1.col, button2.col);
            button1.reset();
            button2.reset();
        }

        //Constructor button adders
        public void PileButtonAdder(String type){
            JButton button = new JButton();
            button.addActionListener(this);
            button.setText(type);
            add(button);
        }

        public void CardButtonAdder(char i, char j) {
            JButton button = new JButton();
            button.addActionListener(this);
            button.setText("[" + i + "][" + j + "]");
            add(button);
        }

    }

