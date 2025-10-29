// Este es Main.pde !! Para que corra hay que crear la pestaña Main en Processing y copiar y pegar esto.
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.video.Movie; 

GameManager gameManager;

PImage imgLogo;
PFont miFuente;
PImage spriteProyectilLeila;
PImage spriteProyectilFantasma;
PImage spriteLeila;
PImage spriteFantasma;
Movie videoFondo;

public static void main(String[] args) {
    PApplet.main("Main");
}

@Override
public void settings() {
    size(800, 600);
}


@Override
public void setup() {
    imageMode(CENTER);

    try {
        imgLogo = loadImage("images/white_text_logo.png");
        miFuente = createFont("upheavtt.ttf", 30);

        spriteProyectilLeila = loadImage("images/proyectilLeila.png");
        spriteProyectilFantasma = loadImage("images/proyectilFantasma.png");
        spriteLeila = loadImage("images/spriteLeila.png");
        spriteFantasma = loadImage("images/spriteFantasma.png");

        videoFondo = new Movie(this, "images/fondo_jugando.mp4");
        videoFondo.loop();
        videoFondo.play();

    } catch (Exception e) {
        System.err.println("Error cargando assets en setup()");
        e.printStackTrace();
    }

    gameManager = new GameManager(this, imgLogo, miFuente, spriteProyectilLeila, spriteProyectilFantasma,
            spriteLeila, spriteFantasma, videoFondo);
}

/* Se ejecuta 60 veces por segundo por defecto en Processing pero lo podemos cambiar.
Le delega toda la lógica y el dibujado al GameManager.
 */

public void movieEvent(Movie m) {
    if (m == videoFondo) {
        m.read();
    }
}

@Override
public void draw() {
    gameManager.actualizar();
    gameManager.dibujar();
}

/* Es para la lógica de movimiento de Leila */
@Override
public void keyPressed() {
    gameManager.manejarInput(key, keyCode);
}

/* Pasa los clics del mouse al GameManager */
@Override
public void mousePressed() {
    gameManager.manejarMousePressed(mouseX, mouseY);
}

/* Se fija si se solto una tecla */
@Override
public void keyReleased() {
    gameManager.manejarKeyReleased(key, keyCode);
}

