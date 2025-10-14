import processing.core.PApplet;
import processing.core.PImage;import processing.core.PImage;

public abstract class Entidad {

    protected float x,y;
    protected PImage imagen;
    protected PApplet sketch; // Ni idea que es esto, gemini dice que es esencial para que Processing pueda dibujar
    protected int velocidad;

    public Entidad(PApplet sketch, float x, float y, PImage imagen, int velocidad) {
        this.sketch = sketch;
        this.x = x;
        this.y = y;
        this.imagen = imagen;
        this.velocidad = velocidad;
    }

    public abstract void mover();

    public abstract void estarVivo();

    public void dibujar() {
        sketch.image(this.imagen, this.x, this.y);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public int getAncho { return imagen.width; }
    public int getAlto { return imagen.height; }
}
