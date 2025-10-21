import processing.core.PApplet;
import processing.core.PImage;

public class Personaje {

    protected PApplet sketch;
    protected float x, y;
    protected PImage imagen;
    protected int vida;
    protected int velocidad;
    protected int radio;

    public Personaje(PApplet sketch, float x, float y, PImage imagen, int vida, int velocidad, int radio) {
        this.sketch = sketch;
        this.x = x;
        this.y = y;
        this.imagen = imagen;
        this.vida = vida;
        this.velocidad = velocidad;
        this.radio = radio;
    }

    public abstract void mover();

    public abstract Proyectil disparar();

    public void dibujar() {
        if (imagen != null) {
            sketch.image(this.imagen, this.x, this.y);
        } else {
            // dibuja un circulo si no hay imagen, para que podamos probar las hitbox
            sketch.noFill();
            sketch.stroke(255, 0, 0);
            sketch.ellipse(this.x, this.y, this.radio * 2, this.radio * 2);
            sketch.noStroke();
        }
    }

    public boolean hayColision(Proyectil p) {
        /* esto es lo que explico el profesor para gestionar las colisiones. las hitbox de los objetos son circulares,
        entonces desde cualquier punto del borde al centro hay la misma distancia (radio r). hay colision entre dos
        objetos si la distancia entre ambos centros es menor a la suma de los dos radios.
         */
        float distancia = sketch.dist(this.x, this.y, p.getX(), p.getY());
        return distancia < this.radio + p.getRadio();
    }

    public void recibirDaño(int danio) {
        this.vida -= danio;
    }

//    public boolean estaVivo() {
//        return this.vida > 0;
//    }

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