import processing.core.PApplet;
import processing.core.PImage;

public class Fantasma extends Personaje {

    public static PImage spriteFantasma;
    public static PImage spriteProyectilEnemigo;
    public int score;

    public Fantasma(PApplet sketch, float x, float y) {

        // imagen = spriteFantasma, vida = 1, velocidad = 2, radio = 20
        super(sketch, x, y, spriteFantasma, 1, 2, 20);
        this.score = 10;
    }

    @Override
    public void mover() {
        this.x -= this.velocidad; // ejemplo: el fantasma se mueve hacia la izquierda a velocidad constante
    }

    @Override
    public Proyectil disparar() {
        if (sketch.random(1) < 0.005) { // probabilidad de %0.5 en cada frame de disparar
            float posX = this.x - this.radio;
            float posY = this.y;

            int velProyectil = 8;
            int radioProyectil = 8;
            int dirProyectil = -1;

            return new Proyectil(sketch, posX, posY, spriteProyectilEnemigo, velProyectil, radioProyectil, dirProyectil);
        }

        // si no dispara, no devuelve nada
        return null;
    }

    public boolean estaFueraDePantalla() { // para saber si se fue de la pantalla, lo podemos usar para borrarlos
        return this.x < -this.radio();
    }

}
