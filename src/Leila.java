import processing.core.PApplet;
import processing.core.PImage;

public class Leila extends Personaje {
    public static PImage spriteProyectil;

    public Leila(PApplet sketch, float x, float y, PImage imagen, int vida, int velocidad, int radio, PImage spriteProyectil) {
        super(sketch, x, y, imagen, vida, velocidad, radio);
        this.spriteProyectil = spriteProyectil;
    }

    @Override
    public void mover() {
        // Para cumplir con la herencia
        // Sería buena idea no heredar mover() si no vamos a usarlo? Borrarlo de Personaje y dejarlo solo en Fantasma
        // le podemos dar movimiento desde Processing con keyPressed()
    }

    @Override
    public Proyectil disparar() {
        float posX = this.x + this.radio;
        float posY = this.y;

        int velocidadProyectil = 15;
        int radioProyectil = 8;
        int direccionProyectil = 1;

        return new Proyectil(sketch, posX, posY, spriteProyectil, velocidadProyectil, radioProyectil, direccionProyectil);
    }

    public void mover(int direccion) {
        this.y += this.velocidad * direccion;
        this.y = sketch.constrain(this.y, 0 + this.radio, sketch.height - this.radio);
        // constrain(valor, minimo, maximo)
        // si se quiere pasar entonces lo frena
        // ver PApplet.constrain()
    }

    public int getVidas() {
        return this.vida;
    }
}
