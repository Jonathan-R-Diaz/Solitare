import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements Runnable{
    static final int MAX_ROWS = 20;
    static final int MAX_COLS = 7;
    private final ActionListener AL = new AL();

    JButton [] cardsOnTop = new JButton[7];
    JButton [][] cardsOnTable = new JButton[MAX_ROWS][MAX_COLS];

    public class Button {
        private int row, col;
        private boolean initialized = false, isWaste;

        public void FullInit(int r, int c){
            row = r;
            col = c;
            initialized = true;
            isWaste = false;
            //System.out.println("full name: " + fullname + "\nCHAR [r][c]: [" + r + "][" + c + "]" + "\nINT [rows][col]: [" + row + "][" + col + "]");
        }
        public void reset(){
            row = 0;
            col = 0;
            initialized = false;
            isWaste = false;
        }

        public void cout(){
            System.out.println("\trow: " + row);
            System.out.println("\tcol: " + col);
            System.out.println("\tinitialized: " + initialized);
            System.out.println("\tisWaste: " + isWaste);
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
    Executables TABLE = new Executables();

    int counter = 0;
    GamePanel() {

        TABLE.DisplayTable();
        setLayout(new GridLayout(MAX_ROWS + 1, MAX_COLS));

        //Pile Grid
        for (int i = 0; i < 7; i++){
            String buttonName = "";
            switch(i) {
                case 0:
                    buttonName = "Draw";
                    break;
                case 1:
                    buttonName = "Empty";
                    break;
                case 2:
                    buttonName = "Display";
                    break;
                case 3:
                    buttonName = "Hearts";
                    break;
                case 4:
                    buttonName = "Diamonds";
                    break;
                case 5:
                    buttonName = "Spades*";
                    break;
                case 6:
                    buttonName = "Clovers*";
                    break;
            }
            PileButtonAdder(buttonName, i);
        }

        //Card Grid
        for (int rows = 0; rows < MAX_ROWS; rows++) {
            for (int cols = 0; cols < MAX_COLS; cols++) {
                JButton button = new JButton();
                button.addActionListener(AL);
                cardsOnTable[rows][cols] = button;

                if (TABLE.hasCardTable[rows][cols])
                    button.setText(TABLE.cardsOnTable[rows][cols].getCardName());

                else
                    button.setVisible(false);
                add(button);
            }
        }
    }


    public void updateGrid() {
        System.out.println("updateGrid called");

        //TABLE
        for (int rows = 0; rows < MAX_ROWS; rows++)
            for (int cols = 0; cols < MAX_COLS; cols++) {
                if (TABLE.hasCardTable[rows][cols]) {
                    cardsOnTable[rows][cols].setText(TABLE.cardsOnTable[rows][cols].getCardName());
                    cardsOnTable[rows][cols].setVisible(true);
                }
                else {
                    cardsOnTable[rows][cols].setText("");
                    cardsOnTable[rows][cols].setVisible(false);
                }
            }

        //TOP
        for (int i = 0; i < 7; i++) {
            //Defaults to foundations
            if (i == 0) {
                cardsOnTop[1].setText("Empty");
                cardsOnTop[3].setText("Hearts");
                cardsOnTop[4].setText("Diamonds");
                cardsOnTop[5].setText("Spades*");
                cardsOnTop[6].setText("Clovers*");
            }

            //DRAW PILE
            if (TABLE.hasCardTop[0][0])
                cardsOnTop[0].setText("Draw");
            else
                cardsOnTop[0].setText("Draw\n(Empty)");

            //WASTE FOUNDATIONS
            if (TABLE.hasCardTop[0][i]) {
                switch (i) {
                    case 1:
                        cardsOnTop[1].setText(TABLE.up[0].getCardName());
                        break;
                    case 3:
                        cardsOnTop[3].setText(TABLE.hearts.peek().getCardName());
                        break;
                    case 4:
                        cardsOnTop[4].setText(TABLE.diamonds.peek().getCardName());
                        break;
                    case 5:
                        cardsOnTop[5].setText(TABLE.spades.peek().getCardName());
                        break;
                    case 6:
                        cardsOnTop[6].setText(TABLE.clovers.peek().getCardName());
                        break;
                }
            }
        }
        counter = 0;

    }
    public void buttonReader(String cmd){
        System.out.println("In buttonReader, cmd: " + cmd);
    }

    public void moveExecuter(){
        TABLE.moveCard(button1.row, button1.col, button2.col);
        button1.reset();
        button2.reset();
        updateGrid();
    }

    //Constructor button adders
    public void PileButtonAdder(String type, int pile){
        JButton button = new JButton();
        button.setText(type);
        button.addActionListener(AL);
        cardsOnTop[pile] = button;
        add(button);
    }

    @Override
    public void run() {

    }

    public class AL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Draw Button

            if (e.getSource() == cardsOnTop[0]) {
                TABLE.Draw();
                updateGrid();
            }
            else if (e.getSource() == cardsOnTop[1]) {
                System.out.println("Waste was clicked");
            }
            else if (e.getSource() == cardsOnTop[2]) {
                TABLE.DisplayTable();
                updateGrid();
            }
            else
            {

                //Finds which button was pressed
                int r = 0, c = 0;
                for (int rows = 0; rows < MAX_ROWS; rows++)
                {
                    for (int cols = 0; cols < MAX_COLS; cols++){
                        if (e.getSource() == cardsOnTable[rows][cols]){
                            r = rows;
                            c = cols;
                        }
                    }
                }

                if (!button1.isInitialized()) {
                    if (TABLE.cardsOnTable[r][c].isCurrentlyUp()) {
                        button1.FullInit(r, c);
                    } else {
                        System.out.println("Can't pick that card, face down");
                    }
                }
                else{
                    if (TABLE.cardsOnTable[r][c].isCurrentlyUp()) {
                        button2.FullInit(r, c);
                        moveExecuter();
                    }
                    else{
                        System.out.println("Can't pick that card, face down");
                        button1.reset();
                        button2.reset();
                    }

                }

            }
            System.out.println("Leaving action reader");

            if (button1.isInitialized()){
                System.out.println("Button 1");
                button1.cout();
            }

            if (button2.initialized){
                System.out.println("Button 2");
                button2.cout();
            }
        }

    }
}
