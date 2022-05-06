package presentation;
public final class ExplodingKittens {
    private ExplodingKittens() { }
    public static void main(final String[] args) {
        GameDesigner gd = new GameDesigner();
        try {
            gd.createGame();
        } catch (InvalidPlayerCountException e) {
            System.out.println(e.getMessage());
        }
    }
}
