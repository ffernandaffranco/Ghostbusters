import processing.core.PApplet;
import processing.core.PImage;

public class Jugador extends Entidad {

    private int vidas = 3;
    private PImage imagenProyectil;

    public Jugador(PApplet sketch, float x, float y, PImage imagen, PImage imagenProyectil) {
        super(sketch, x, y, imagen, velocidad);
        this.vidas = vidasIniciales;
        this.imagenProyectil = imagenProyectil;
    }

    public Proyectil disparar() {
        // el proyectil se crea delante del jugador
        float posX = this.x + this.getAncho();
        float posY = this.y + this.getAlto() / 2;

        return new Proyectil(sketch, posX, posY, imagenProyectil, 15, 1)
    }

    public void perderVida() {
        this.vidas--;
    }

    public boolean estaVivo() {
        return this.vidas > 0;
    }

    public int getVidas() {
        return this.vidas;
    }

    @Override
    public void mover() {
        // Para cumplir con la herencia
        // Sería buena idea no heredar mover() si no vamos a usarlo?
    }

    public void mover(int direccion) {
        this.y += velocidad * direccion;
        this.y = PApplet.constrain(this.y, 0, sketch.height - this.getAlto());
        // la última línea es para definir límites en el movimiento del jugador para que no se vaya de la pantalla
        // ver PApplet.constrain()
    }
}
