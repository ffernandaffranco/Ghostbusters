import processing.core.PApplet;
import processing.core.PImage;

public class Fantasma extends Entidad {

    private int puntosDeVida;
    public int score;

    public Fantasma(PApplet sketch, float x, float y, PImage imagen, int velocidad, int puntosDeVida) {
        super(sketch, x, y, imagen, velocidad);
        this.puntosDeVida = puntosDeVida;
        this.score = score;
    }

    @Override
    public void mover() {
        this.x -= this.velocidad; // ejemplo boludo: el fantasma se mueve constantemente hacia la izquierda a una velocidad constante
    }

    public void recibirDaño() {
        this.puntosDeVida--;
    }

    public boolean estaVivo() {
        return this.puntosDeVida > 0;
    }

    public boolean estaFueraDePantalla() { // para saber si se fue de la pantalla, lo podemos usar para borrar entidades que no veamos
        return this.x < -this.getAncho()
    }

    public Proyectil disparar(PImage imagenProyectilEnemigo) {
        if (sketch.random(1) < 0.005) {
            float posX = this.x;
            float posY = this.y + this.getAlto() / 2;
            return new Proyectil(sketch, posX, posY, imagenProyectilEnemigo, 8, -1);
        }
        return null;
    }
}
