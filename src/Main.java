// Este es Main.pde !! Para que corra hay que crear la pestaña Main en Processing y copiar y pegar esto.

GameManager gameManager;

public static void main(String[] args) {
    PApplet.main("Main");
}

@Override
public void settings() {
    size(800, 600); // Tamaño de la ventana.
}

/* Se ejecuta una sola vez al inicio del programa.
* Aca iría toda la configuración estática, en este caso crea la instancia del GameManager.*/
@Override
public void setup() {
    imageMode(CENTER);
    gameManager = new GameManager(this);
}

/* Se ejecuta 60 veces por segundo por defecto en Proccessing pero lo podemos cambiar.
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