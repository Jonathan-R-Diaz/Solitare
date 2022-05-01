import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;

public class Game {
    Table table = new Table();

    Game(){
        table.DisplayTable();
        for (int i = 0; i < 19000; i++)
            table.Draw();
    }


}
