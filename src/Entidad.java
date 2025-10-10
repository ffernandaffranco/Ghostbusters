import processing.core.PApplet;
import processing.core.PImage;import processing.core.PImage;

public abstract class Entidad {
    // Atributos comunes a todas las entidades.

    protected float x,y; // Posición en la pantalla
    protected int ancho, alto; // Dimensiones para la hitbox
    protected PImage imagen;
    protected int velocidad;

    public Entidad(float x, float y, PImage imagen, int ancho, int alto, int velocidad) {
        this.x = x;
        this.y = y;
        this.imagen = imagen;
        this.ancho = imagen.width;
        this.alto = imagen.weigth;
        int velocidad;
    }

    public abstract void mover();
    // Después hay que implementarlo
    public void dibujar();
    // Después hay que implementarlo

}
