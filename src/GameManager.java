import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class GameManager {

    private PApplet sketch; // Conexión con el sketch de Processing.

    // ESTADOS DE JUEGO
    private final int ESTADO_MENU = 0;
    private final int ESTADO_JUGANDO = 1;
    private final int ESTADO_GAMEOVER = 2;
    private int estadoJuego;

    // INSTANCIAS
    private Leila jugador;
    private ArrayList<Personaje> enemigos;
    private ArrayList<Proyectil> proyectiles;

    // CONSTRUCTOR
    public GameManager(PApplet sketch) {
        this.sketch = sketch;

        // Arranca las listas vacías.
        this.enemigos = new ArrayList<Personaje>();
        this.proyectiles = new ArrayList<Proyectil>();

        // Prepara la partida.
        iniciarJuego();
        this.estadoJuego = ESTADO_JUGANDO;
    }

    public void iniciarJuego() {
        enemigos.clear(); // Vacía las listas por si quedaron cosas de la partida anterior.
        proyectiles.clear();

        // Crea al jugador
        jugador = new Leila(sketch, 100, sketch.height / 2, null,
                3, 5, 15, null);

        // Crea un único enemigo de Prueba
        enemigos.add(new Fantasma(sketch, sketch.width - 100, sketch.height / 2, null,
                1, 2, 20, null));
    }

    /**
     * Metodo principal de lógica ejecutado 60 veces por segundo desde Main.draw().
     * Decide qué hacer según el estado del juego.
     */
    public void actualizar() {
        if (estadoJuego == ESTADO_JUGANDO) {
            actualizarLogicaJuego();
        }
    }

    /**
     * Metodo principal de dibujo ejecutado 60 veces por segundo desde Main.draw().
     * Decide que pantalla dibujar segun el estado del juego.
     */
    public void dibujar() {
        sketch.background(0);
        if (estadoJuego == ESTADO_JUGANDO) {
            dibujarJuego();
        }
        if (estadoJuego == ESTADO_GAMEOVER) {
            sketch.fill(255, 0, 0);
            sketch.textSize(50);
            sketch.textAlign(PApplet.CENTER, PApplet.CENTER);
            sketch.text("GAME OVER", sketch.width/2, sketch.height/2);
        }
    }

    /**
     * Contiene toda la lógica que ocurre durante el juego.
     * Mover, disparar, colisionar, limpiar listas, chequear game over.
     */
    private void actualizarLogicaJuego() {

        // Mover y disparar enemigos.
        for (Personaje enemigo : enemigos) {
            enemigo.mover();
            Proyectil p = enemigo.disparar();
            if (p != null) {
                proyectiles.add(p);
            }
        }

        // Mover proyectiles.
        for (Proyectil p : proyectiles) {
            p.mover();
        }

        // Revisar colisiones.
        /* Esto es lo que habiamos hablado. Si es un proyectil aliado, compara las colisiones contra enemigos. Si es un
        proyectil enemigo entonces compara las colisiones solo contra el jugador.
         */
        for (Proyectil p : proyectiles) {
            if (p.esAliado()) {
                for (Personaje enemigo : enemigos) {
                    if (enemigo.hayColision(p)) {
                        p.destruir();
                    }
                }
            } else {
                if (jugador.hayColision(p)) {
                    p.destruir();
                }
            }
        }

        // Limpieza de listas.
        proyectiles.removeIf(p -> !p.estaActivo() || p.estaFueraDePantalla());
        enemigos.removeIf(e -> e.vida <= 0);

        // Chequear game over (Por ahora, solo si la vida es <= 0).
        if (jugador.vida <= 0) {
            estadoJuego = ESTADO_GAMEOVER;
        }
    }

    /**
     * Dibuja todos los elementos del juego (jugador, enemigos, proyectiles, UI).
     */
    private void dibujarJuego() {
        jugador.dibujar();

        for (Personaje enemigo : enemigos) {
            enemigo.dibujar();
        }

        for (Proyectil p : proyectiles) {
            p.dibujar();
        }

        sketch.fill(255);
        sketch.textSize(20);
        sketch.textAlign(PApplet.LEFT, PApplet.TOP);
        sketch.text("Vidas: " + jugador.getVidas(), 10, 10);
    }

    /**
     * Recibe input del teclado desde Main.pde y actúa
     */
    public void manejarInput(char key, int keyCode) {
        if (estadoJuego == ESTADO_JUGANDO) {
            if (keyCode == PApplet.UP) {
                jugador.mover(-1);
            } else if (keyCode == PApplet.DOWN) {
                jugador.mover(1);
            }
            if (key == ' ') {
                Proyectil p = jugador.disparar();
                proyectiles.add(p);
            }
        }
    }
}