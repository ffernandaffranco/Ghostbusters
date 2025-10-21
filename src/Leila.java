import processing.core.PApplet;
import processing.core.PImage;

public class Leila extends Personaje {

    private int vidas = 3;
    private int velocidad = 1;
    private int radio = 3;
    // private PImage spriteDeLeila
    // private PImage spriteDelProyectilBueno
    // float x,y se carga dentro de draw() llamando al constructor o lo definimos en los atributos de Cazafantasma

    public Leila(PApplet sketch, float x, float y, PImage imagen, int vida, int velocidad, int radio) {
        this.vidas = vida;
        this.x = x;
        this.y = y;
        this.sketch = sketch;
        this.imagen = imagen;
        this.velocidad = velocidad;
        this.radio = radio;
    }

    public Proyectil disparar() {
        // el proyectil se crea delante del jugador
        // podriamos asignar un shotpoint directamente
        // esto es generico porque no tenemos imagen todavia y no sabemos de donde sale el tiro
        float posX = this.x + this.radio
        float posY = this.y

        return new Proyectil(sketch, posX, posY, imagenProyectil, 15, 1);
        // return new ProyectilBueno(sketch, posX, posY, imagenProyectil, 15, 1);
    }

    public void perderVida() {
        this.vidas--;
    }

    public boolean estaVivo() {
        return this.vidas > 0;
    }

    public int getVidas() {
        return this.vidas;
    }

    @Override
    public void mover() {
        // Para cumplir con la herencia
        // Sería buena idea no heredar mover() si no vamos a usarlo?
        // le podemos dar movimiento desde Processing con keyPressed()
    }

    public void mover(int direccion) {
        this.y = this.y + (velocidad * direccion);
        this.y = PApplet.constrain(this.y, 0, sketch.height - this.getAlto());
        // lo ponemos, tenemos que profundizar
        // la última línea es para definir límites en el movimiento del jugador para que no se vaya de la pantalla
        // ver PApplet.constrain()
    }
}
