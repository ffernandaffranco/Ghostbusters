import processing.core.PApplet;
import processing.core.PImage;

public class Proyectil extends Entidad {

    private int direccion;

    public Proyectil(PApplet sketch, float x, float y, PImage imagen, int velocidad, int direccion) {
        super(sketch, x, y, imagen, velocidad);
        this.direccion = direccion;
    }

    @Override
    public void mover() {
        this.x += velocidad * direccion;
    }

    public boolean estaFueraDePantalla() {
        return (this.x < -this.getAncho() || this.x > sketch.width);
    }
}
