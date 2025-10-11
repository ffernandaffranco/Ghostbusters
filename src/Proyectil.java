import processing.core.PApplet;
import processing.core.PImage;

public class Proyectil extends Entidad {

    private int velocidad;
    private int direccion;

    public Proyectil(PApplet sketch, float x, float y, PImage imagen, int velocidad, int direccion) {
        super(sketch, x, y, imagen);
        this.velocidad = velocidad;
        this.direccion = direccion;
    }

    @Override
    public void mover() {
        // multiplicamos velocidad x direccion y nos queda para donde se mueve
    }
}
