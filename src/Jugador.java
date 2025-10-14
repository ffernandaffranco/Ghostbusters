import processing.core.PApplet;
import processing.core.PImage;

public class Jugador extends Entidad {

    private int vidas;
    private PImage imagenProyectil;

    public Jugador(PApplet sketch, float x, float y, PImage imagen, PImage imagenProyectil) {
        super(sketch, x, y, imagen, velocidad);
        this.vidas = vidas;
        this.imagenProyectil = imagenProyectil;
    }

    @Override
    public void mover() {
    }

    public Proyectil disparar() {
        // Aca hay que hacer aparecer el proyectil, tenemos q crear la instancia y devolverla
        // Hay que elegir bien la ubicacion (centrado en el personaje, a la derecha, mas o menos a la mitad de la imagen)
    }

    public void perderVida() {
        this.vidas--;
    }

    public boolean estarVivo() {
        if (this.vidas <= 0) {
            // hace algo
            return false;
        } else {
            return true;
        }
    }

    public int getVidas() {
        return this.vidas;
    }

    public void iniciarPartida() {
        // implementar
    }
}
