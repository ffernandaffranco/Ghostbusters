import processing.core.PApplet;
import processing.core.PImage;

public class Leila extends Personaje {

    private PImage spriteProyectil;

    public Leila(PApplet sketch, float x, float y, PImage imagen, int vida, int velocidad, int radio, PImage spriteProyectil) {
        super(sketch, x, y, imagen, vida, velocidad, radio);
        this.spriteProyectil = spriteProyectil;
    }

    @Override
    public void mover() {
        /* Cuando declaro en GameManager la lista 'ArrayList<Personaje> enemigos' el compilador sabe que tengo una lista
        de objetos que son instancias de una clase hija de Personaje.

        Durante la ejecución del juego esa lista podría contener Fantasma, Zombie, Vampiro, o cualquier tipo de Personaje.
        Si yo quiero mover a todos los enemigos al mismo tiempo independientemente de su clase entonces voy a tener que
        usar un bucle 'for (Personaje enemigo : enemigos)' y llamar a mover(). Como la lista es una lista de enemigos que
        heredan de Personaje, el compilador sabe que están obligados a implementar el metodo mover(). Es por esto que puedo
        usar esta lógica de mover a todos los enemigos al mismo tiempo.

        ¿Qué pasa si borro 'abstract void mover()' de Personaje? El compilador ahora no sabe si todos los enemigos
        tienen mover(), y no puedo llamar a mover() directamente.

        Es por eso que no borré el metodo abstracto mover() de Personaje.

        ¿Cómo se puede solucionar esto?

        Opcion 1: Podemos sacar el metodo de Personaje y cambiar la lógica de movimiento de los enemigos durante la ejecución
        del juego. La idea sería la siguiente: para cada enemigo de la lista enemigos preguntarle si pertenece a una clase
        en particular que si tenga mover(). Esto es fácil ahora que solo tenemos una clase de enemigos (Fantasma) pero se puede
        complicar a medida que vayamos añadiendo otros tipos de enemigos. De ambas formas no creo que lleguemos a tener
        más de 2 o 3 así que es una opción a considerar.

        Por cada enemigo de la lista {
            Es un fantasma? {
                mover() }
            Es un zombie? {
                mover() }
            Es un vampiro? {
                mover() }
        }

        Y así tendríamos que hacer con cada clase de enemigos.

        Opcion 2: Hacer que los enemigos y Leila no hereden de la misma clase. De esa forma podríamos implementar una
        clase Enemigo con un metodo abstracto mover(), y hacer de Leila su propia clase que no hereda de nadie.
         */
    }

    @Override
    public Proyectil disparar() {
        float posX = this.x + this.radio;
        float posY = this.y;

        int velProyectil = 15;
        int radioProyectil = 8;
        int dirProyectil = 1;
        boolean esAliado = true;

        return new Proyectil(sketch, posX, posY, this.spriteProyectil, velProyectil, radioProyectil, dirProyectil, esAliado);
    }

    public void mover(int direccion) {
        this.y += this.velocidad * direccion;
        this.y = PApplet.constrain(this.y, 0 + this.radio, sketch.height - this.radio);
    }

    public int getVidas() {
        return this.vida;
    }
}