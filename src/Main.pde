// Este es Main.pde !! Para que corra hay que crear la pestaña Main en Processing y copiar y pegar esto.

GameManager gameManager;

public static void main(String[] args) {
    PApplet.main("Main");
}

@Override
public void settings() {
    size(800, 600);
}


@Override
public void setup() {
    imageMode(CENTER); // Para centrar las imágenes
    gameManager = new GameManager(this);
}

/* Se ejecuta 60 veces por segundo por defecto en Processing pero lo podemos cambiar.
Le delega toda la lógica y el dibujado al GameManager.
 */
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