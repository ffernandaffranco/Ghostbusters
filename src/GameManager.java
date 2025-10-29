import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import java.util.ArrayList;

public class GameManager {

    private PApplet sketch; // Conexión con el sketch de Processing.

    // ESTADOS DE JUEGO
    private final int ESTADO_MENU = 0;
    private final int ESTADO_JUGANDO = 1;
    private final int ESTADO_GAMEOVER = 2;
    private int estadoJuego;

    // ASSETS DEL MENU DE INICIO
    private PImage imgLogo;
    private PFont miFuente;

    // BOTONES DEL MENU DE INICIO
    private int btnIniciarAncho, btnIniciarAlto, btnIniciarY;
    private int btnEstadisticasAncho, btnEstadisticasAlto, btnEstadisticasY;
    private int btnSalirAncho, btnSalirAlto, btnSalirY;

    // INSTANCIAS
    private Leila jugador;
    private ArrayList<Personaje> enemigos;
    private ArrayList<Proyectil> proyectiles;

    // CONSTRUCTOR
    public GameManager(PApplet sketch) {
        this.sketch = sketch;
        this.enemigos = new ArrayList<Personaje>();
        this.proyectiles = new ArrayList<Proyectil>();

        // Cargar assets del menu
        try {
            imgLogo = sketch.loadImage("images/white_text_logo.png");
            miFuente = sketch.createFont("upheavtt.ttf", 30);

        } catch (Exception e) {
            System.err.println("Error cargando assets del menu");
            System.err.println("Tenes bien 'data/images/white_text_logo.png' y 'data/upheavtt.ttf'?");
            System.err.println(e.getMessage());
        }

        // Hitboxes de los botones del menú de inicio
        btnIniciarAncho = 261;
        btnIniciarAlto = 31;
        btnIniciarY = 400;

        btnEstadisticasAncho = 261;
        btnEstadisticasAlto = 31;
        btnEstadisticasY = 440;

        btnSalirAncho = 261;
        btnSalirAlto = 31;
        btnSalirY   = 480;

        // Arrancamos en el menú
        this.estadoJuego = ESTADO_MENU;
    }
    
    public void iniciarJuego() {
        enemigos.clear();
        proyectiles.clear();

        jugador = new Leila(sketch, 100, sketch.height / 2, null, 3, 5, 15, null); 

        enemigos.add(new Fantasma(sketch, sketch.width - 100, sketch.height / 2, null, 1, 2, 20, null)); 

        this.estadoJuego = ESTADO_JUGANDO;
    }

    /**
     * Metodo principal de lógica
     */
    public void actualizar() {
        if (estadoJuego == ESTADO_JUGANDO) {
            actualizarLogicaJuego();
        }
    }

    /**
     * Metodo principal de dibujo
     */
    public void dibujar() {
        sketch.background(0);

        if (estadoJuego == ESTADO_MENU) {
            dibujarMenu();
        }
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
     * Lógica que ocurre durante el juego
     */
    private void actualizarLogicaJuego() {

        // Mover y disparar Leila
        if (jugador != null) { // Chequeo de seguridad
             jugador.mover();
             Proyectil pJugador = jugador.disparar();
             if (pJugador != null) {
                 proyectiles.add(pJugador);
             }
        }

        // Mover y disparar enemigos
        for (Personaje enemigo : enemigos) {
            enemigo.mover();
            Proyectil pEnemigo = enemigo.disparar();
            if (pEnemigo != null) {
                proyectiles.add(pEnemigo);
            }
        }

        // Mover proyectiles
        for (Proyectil p : proyectiles) {
            p.mover();
        }

        // Chequear colisiones
        for (Proyectil p : proyectiles) {
            if (p.esAliado()) {
                for (Personaje enemigo : enemigos) {
                    if (enemigo.hayColision(p)) {
                        p.destruir(); 
                    }
                }
            } else {
                if (jugador != null && jugador.hayColision(p)) {
                    p.destruir();
                }
            }
        }

        // Limpiar listas
        proyectiles.removeIf(p -> !p.estaActivo() || p.estaFueraDePantalla());
        enemigos.removeIf(e -> e.vida <= 0 || e.estaFueraDePantalla());

        // Game Over
        if (jugador != null && jugador.vida <= 0) {
            estadoJuego = ESTADO_GAMEOVER;
        }
    }

    /**
     * Dibuja todos los elementos del juego
     */
    private void dibujarJuego() {
        if (jugador != null) jugador.dibujar();

        for (Personaje enemigo : enemigos) {
            enemigo.dibujar();
        }

        for (Proyectil p : proyectiles) {
            p.dibujar();
        }

        // UI Vidas
        if (jugador != null) {
            sketch.fill(255);
            sketch.textSize(20);
            sketch.textAlign(PApplet.LEFT, PApplet.TOP);
            sketch.text("Vidas: " + jugador.getVidas(), 10, 10);
        }
    }


    /**
     * Dibuja la pantalla del menu
     */
    private void dibujarMenu() {
        sketch.textAlign(PApplet.CENTER, PApplet.CENTER);
        
        // Logo
        if (imgLogo != null) {
            float nuevoAncho = imgLogo.width * 1.5f;
            float nuevoAlto = imgLogo.height * 1.5f;
            sketch.image(imgLogo, sketch.width / 2, 200, nuevoAncho, nuevoAlto);

        } else {
            sketch.fill(255);
            sketch.textSize(40);
            sketch.text("GHOSTBUSTERS (LOGO NO ENCONTRADO)", sketch.width/2, 200);
        }
        
        // Botones
        if (miFuente != null) {
            sketch.textFont(miFuente);

            if (clickEnBoton(sketch.mouseX, sketch.mouseY, btnIniciarY, btnIniciarAncho, btnIniciarAlto)) {
                sketch.fill(255, 255, 160);
            } else {
                sketch.fill(255);
            }
            sketch.text("Iniciar partida", sketch.width / 2, btnIniciarY);

            if (clickEnBoton(sketch.mouseX, sketch.mouseY, btnEstadisticasY, btnEstadisticasAncho, btnEstadisticasAlto)) {
                sketch.fill(255, 255, 160);
            } else {
                sketch.fill(255);
            }
            sketch.text("Estadisticas", sketch.width / 2, btnEstadisticasY);

            if (clickEnBoton(sketch.mouseX, sketch.mouseY, btnSalirY, btnSalirAncho, btnSalirAlto)) {
                sketch.fill(255, 255, 160);
            } else {
                sketch.fill(255);
            }
            sketch.text("salir", sketch.width / 2, btnSalirY);

        } else {
            sketch.fill(255);
            sketch.textSize(30);
            sketch.text("Iniciar partida (FUENTE NO ENCONTRADA)", sketch.width / 2, btnIniciarY);
            sketch.text("Estadisticas", sketch.width / 2, btnEstadisticasY);
            sketch.text("salir", sketch.width / 2, btnSalirY);
        }
    }

    /**
     * Ve si el click cae daentro de un botón
     */
    private boolean clickEnBoton(int x, int y, int by, int bw, int bh) {
        float medioAncho = bw / 2.0f;
        float medioAlto = bh / 2.0f;
        int bx = sketch.width / 2;

        return (x > bx - medioAncho && x < bx + medioAncho &&
                y > by - medioAlto  && y < by + medioAlto);
    }

    /**
     * Recibe input del teclado desde Main.
     * Pasa el evento keyPressed a Leila.
     * Maneja el reinicio desde GameOver.
     */
    public void manejarInput(char key, int keyCode) {
        if (estadoJuego == ESTADO_JUGANDO && jugador != null) {
             jugador.manejarKeyPressed(keyCode);
        }

        if (estadoJuego == ESTADO_GAMEOVER) {
            if (key == ' ' || keyCode == PApplet.ENTER) {
                this.estadoJuego = ESTADO_MENU;
            }
        }
    }

    /**
     * Recibe clicks del mouse desde Main
     */
    public void manejarMousePressed(int mouseX, int mouseY) {
        if (estadoJuego == ESTADO_MENU) {

            if (clickEnBoton(mouseX, mouseY, btnIniciarY, btnIniciarAncho, btnIniciarAlto)) {
                System.out.println("Click en Iniciar");
                iniciarJuego();
            }

            if (clickEnBoton(mouseX, mouseY, btnEstadisticasY, btnEstadisticasAncho, btnEstadisticasAlto)) {
                System.out.println("Click en Estadisticas");
            }

            if (clickEnBoton(mouseX, mouseY, btnSalirY, btnSalirAncho, btnSalirAlto)) {
                System.out.println("Clic en Salir");
                sketch.exit();
            }
        }
    }

    /**
     * Recibe el evento de soltar tecla desde Main y lo pasa al jugador.
     */
    public void manejarKeyReleased(char key, int keyCode) {
        if (jugador != null) {
            jugador.manejarKeyReleased(keyCode);
        }
    }
}
