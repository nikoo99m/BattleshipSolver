public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showMenu();
        while (true) {
            switch (menu.selectOption()) {
                case 0:
                    menu.showMenu();
                    break;
                case 1:
                    Game game = new Game();
                    game.startOfTheGame();
                    game.playGame();
                    break;
                case 2:
                    menu.showRules();
                    break;
            }
        }
    }
}