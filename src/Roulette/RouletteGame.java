package Roulette;

public class RouletteGame {
    private wheelSpin wheel;
    private ColorDeterminer colorDeterminer;

    public RouletteGame() {
        this.wheel = new wheelSpin();
        this.colorDeterminer = new ColorDeterminer();
    }

}
