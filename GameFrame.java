import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GamePanel panel;

    GameFrame(){
        panel = new GamePanel();
        add(panel);
        setTitle("Solitare");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pack();
    }
}
