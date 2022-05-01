public class Game {
    Table table = new Table();

    Game(){
        table.DisplayTable();
        for (int i = 0; i < 28; i++)
            if (!table.down.empty())
                Draw();
    }

    public void Draw() {
        if (table.down.isEmpty()) {
            table.hasCardTop[0][0] = false;
            return;
        } else {
            if (table.hasCardTop[2][1]) {
                table.up[2].isDown();
                table.throwaway.push(table.up[2]);
            }
            if (table.down.isEmpty())
                table.hasCardTop[0][0] = false;

            table.up[2] = table.up[1];
            table.up[1] = table.up[0];
            table.down.peek().isUp();
            table.up[0] = table.down.peek();
            table.hasCardTop[0][1] = true;

            table.down.pop();
            table.DisplayTable();
        }
    }
}
