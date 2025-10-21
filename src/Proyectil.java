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

    // preguntar si hacemos un if para borrar o no el proyectil, va adentro del metodo estaFueraDePantalla o va en draw() en Processing?

//    public boolean estaActivo() {
//        return estaActivo;
//    }

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


// podemos hacer dos arraylist proyectilesBuenos<Proyectil> y proyectilesMalos<Proyectil>? Pueden ser dos arrays con el mismo tipo de objeto? O es mejor separarlos en dos clases ProyectilBueno y ProyectilMalo?
