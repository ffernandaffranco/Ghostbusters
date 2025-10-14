import processing.core.PApplet;
import processing.core.PImage;

public class Fantasma extends Entidad {

    private int puntosDeVida;
    public int score;

    public Fantasma(PApplet sketch, float x, float y, PImage imagen, int velocidad, int puntosDeVida) {
        super(sketch, x, y, imagen, velocidad);
        this.puntosDeVida = puntosDeVida;
    }

    @Override
    public void mover() {
        this.x += this.velocidad; // este es un ejemplo boludo donde el fantasma se mueve recto de derecha a izquierda
    }

    public void recibirDaño() {
        this.puntosDeVida--;
    }

    public boolean estaVivo() {
        return this.puntosDeVida > 0;
    }

    public Proyectil disparar() {
        // aca hay q implementar el disparo enemigo como va a funcionar
    }



}
