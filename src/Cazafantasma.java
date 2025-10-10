import processing.core.PApplet;
import processing.core.PImage;

public class Cazafantasma extends Entidad {

    private int vidas;

    public Cazafantasma(float x, float y, PImage imagen, int velocidad, int vidas) {
        super(x, y, imagen, velocidad);
        this.vidas = vidas;

    }

    @Override
    public void mover() {
    }

    public void disparar() {

    }

    public void morir() {
        // Si la vida es 0 entonces mori
    }
}
