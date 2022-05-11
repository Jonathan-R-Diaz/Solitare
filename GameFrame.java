import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GamePanel panel;


    GameFrame(){
        setLayout(new FlowLayout());
        panel = new GamePanel();
        add(panel);


        setBackground(new Color (GamePanel.TABLE_GREEN));
        setTitle("Solitare");
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        pack();
    }
}
