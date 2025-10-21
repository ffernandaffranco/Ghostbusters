import processing.core.PApplet;
import processing.core.PImage;

public class Leila extends Personaje {

    public static PImage spriteLeila;
    public static PImage spriteProyectil;
    // float x,y se carga llamando al constructor o lo definimos en los atributos de Cazafantasma?

    public Leila(PApplet sketch, float x, float y) {

        super(sketch, x, y, spriteLeila, 3 , 1 ,3)
        // vida = 3, velocidad = 1, radio = 3
    }

    @Override
    public void mover() {
        // Para cumplir con la herencia
        // Sería buena idea no heredar mover() si no vamos a usarlo? Borrarlo de Personaje y dejarlo solo en Fantasma
        // le podemos dar movimiento desde Processing con keyPressed()
    }

    @Override
    public Proyectil disparar() {
        // esto es generico porque no tenemos imagen todavia y no sabemos de donde sale el tiro
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
        // la posición y está entre el minimo y el maximo?
        // si se quiere pasar entonces lo frena
        // ver PApplet.constrain()
    }

    public int getVidas() {
        return this.vida;
    }
}
