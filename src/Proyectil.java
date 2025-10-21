import processing.core.PApplet;
import processing.core.PImage;

public class Proyectil {

    private PApplet sketch;
    private float x, y;
    private PImage imagen;
    private int velocidad;
    private int radio;
    private int direccion;
    private boolean estaActivo;

    public Proyectil(PApplet sketch, float x, float y, PImage imagen, int velocidad, int radio, int direccion) {
        this.sketch = sketch;
        this.x = x;
        this.y = y;
        this.imagen = imagen;
        this.velocidad = velocidad;
        this.radio = radio;
        this.direccion = direccion;
        this.estaActivo = true;
    }

    public void mover() {
        this.x += this.velocidad * this.direccion;
    }

    public void dibujar() {
        if (imagen != null) {
            sketch.image(this.imagen, this.x, this.y);
        } else {
            sketch.noFill();
            sketch.stroke(0, 255, 0);
            sketch.ellipse(this.x, this.y, this.radio * 2, this.radio * 2);
            sketch.noStroke();
        }
    }

    public boolean estaFueraDePantalla() {
        return this.x < -this.radio || this.x > sketch.width + this.radio;
    }

    public boolean estaActivo() {
        return estaActivo;
    }

    public void destruir() {
        this.estaActivo = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getRadio() {
        return radio;
    }
}


/* para diferenciar los proyectiles buenos de los malos:
opcion 1: hacer dos arraylist separados que contengan objetos de la clase Proyectil
opcion 2: hacer dos arraylist separados. uno contiene objetos de la clase ProyectilAliado y el otro ProyectilEnemigo

cosas que varian por ahora entre proyectiles aliados y enemigos: imagen y dirección
 */