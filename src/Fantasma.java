import processing.core.PApplet;
import processing.core.PImage;

public class Fantasma extends Personaje {

    private PImage spriteProyectilEnemigo;
    public int score;

    public Fantasma(PApplet sketch, float x, float y, PImage imagen, int vida, int velocidad, int radio, PImage spriteProyectilEnemigo) {
        super(sketch, x, y, imagen, vida, velocidad, radio);
        this.spriteProyectilEnemigo = spriteProyectilEnemigo;
        this.score = 10;
    }

    @Override
    public void mover() {
        this.x -= this.velocidad;
    }

    @Override
    public Proyectil disparar() {
        if (sketch.random(1) < 0.005) { // Dame un numero del 0 al 1. Si es menor a 0.005 entonces dispará.
            // Esto sería un %0.05 de disparar en cada frame.
            float posX = this.x - this.radio;
            float posY = this.y;

            int velProyectil = 8;
            int radioProyectil = 8;
            int dirProyectil = -1;
            boolean esAliado = false;

            return new Proyectil(sketch, posX, posY, this.spriteProyectilEnemigo, velProyectil, radioProyectil, dirProyectil, esAliado);
        }
        return null; // Si no dispara devuelve null. El GameManager tiene que saber que hacer con este null porque sino nos
        // va a tirar error.
    }

    public boolean estaFueraDePantalla() {
        return this.x < -this.radio;
    }
}