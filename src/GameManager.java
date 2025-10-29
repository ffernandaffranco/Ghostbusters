import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.video.Movie;
import java.util.ArrayList;

public class GameManager {

    private PApplet sketch;

    // ESTADOS DE JUEGO
    private final int ESTADO_MENU = 0;
    private final int ESTADO_JUGANDO = 1;
    private final int ESTADO_GAMEOVER = 2;
    private int estadoJuego;

    // ASSETS
    PImage imgLogo;
    PFont miFuente;
    PImage spriteProyectilLeila;
    PImage spriteProyectilFantasma;
    PImage spriteLeila;
    PImage spriteFantasma;

    // FONDO JUGANDO
    private Movie videoFondo;
    
    // TAMAÑO SPRITES
    private final float TAM_LEILA = 80;       
    private final float TAM_FANTASMA = 90;
    private final float TAM_PROYECTIL = 30;

    // HITBOXES DE BOTONES
    int botonIniciarAncho = 261;
    int botonIniciarAlto = 31;
    int botonIniciarY = 400;

    int botonEstadisticasAncho = 261;
    int botonEstadisticasAlto = 31;
    int botonEstadisticasY = 440;

    int botonsSalirAncho = 261;
    int botonSalirAlto = 31;
    int botonSalirY = 480;

    // INSTANCIAS
    private Leila jugador;
    private ArrayList<Personaje> enemigos;
    private ArrayList<Proyectil> proyectiles;

    // CONSTRUCTOR
    public GameManager(PApplet sketch,
                       PImage imgLogo,
                       PFont miFuente,
                       PImage spriteProyectilLeila,
                       PImage spriteProyectilFantasma,
                       PImage spriteLeila,
                       PImage spriteFantasma,
                       Movie videoFondo) {

        this.sketch = sketch;
        this.imgLogo = imgLogo;
        this.miFuente = miFuente;
        this.spriteProyectilLeila = spriteProyectilLeila;
        this.spriteProyectilFantasma = spriteProyectilFantasma;
        this.spriteLeila = spriteLeila;
        this.spriteFantasma = spriteFantasma;
        this.videoFondo = videoFondo;

        this.enemigos = new ArrayList<Personaje>();
        this.proyectiles = new ArrayList<Proyectil>();

        this.estadoJuego = ESTADO_MENU;
    }
    
    public void iniciarJuego() {
        enemigos.clear();
        proyectiles.clear();

        jugador = new Leila(sketch, 100, sketch.height / 2, spriteLeila, 3, 5, 15, spriteProyectilLeila); 

        enemigos.add(new Fantasma(sketch, sketch.width - 100, sketch.height / 2, spriteFantasma, 1, 2, 20, spriteProyectilFantasma)); 

        this.estadoJuego = ESTADO_JUGANDO;
    }

    public void actualizar() {
        if (estadoJuego == ESTADO_JUGANDO) {
            actualizarLogicaJuego();
        }
    }

    public void dibujar() {
        sketch.background(0);
        if (estadoJuego == ESTADO_JUGANDO && videoFondo != null) {
            sketch.image(videoFondo, sketch.width/2, sketch.height/2, sketch.width, sketch.height);
        }

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

    private void actualizarLogicaJuego() {
        // Mover y disparar Leila
        if (jugador != null) {
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

    private void dibujarJuego() {
        // Dibujar jugador (Leila)
        if (jugador != null) {
            dibujarSpriteEscalado(spriteLeila, jugador.x, jugador.y, TAM_LEILA);
        }

        // Dibujar enemigos (fantasmas)
        for (Personaje enemigo : enemigos) {
            dibujarSpriteEscalado(spriteFantasma, enemigo.x, enemigo.y, TAM_FANTASMA);
        }

        // Dibujar proyectiles
        for (Proyectil p : proyectiles) {
            PImage sprite = p.esAliado() ? spriteProyectilLeila : spriteProyectilFantasma;
            dibujarSpriteEscalado(sprite, p.getX(), p.getY(), TAM_PROYECTIL);
          }

        // UI Vidas
        if (jugador != null) {
            sketch.fill(255);
            sketch.textSize(20);
            sketch.textAlign(PApplet.LEFT, PApplet.TOP);
            sketch.text("Vidas: " + jugador.getVidas(), 10, 10);
        }
    }
    
    private void dibujarSpriteEscalado(PImage img, float x, float y, float tamObjetivo) {
        if (img == null) return;

        float escala = tamObjetivo / Math.max(img.width, img.height);
        float ancho = img.width * escala;
        float alto = img.height * escala;

        sketch.image(img, x, y, ancho, alto);
    }

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

            if (clickEnBoton(sketch.mouseX, sketch.mouseY, botonIniciarY, botonIniciarAncho, botonIniciarAlto)) {
                sketch.fill(255, 255, 160);
            } else {
                sketch.fill(255);
            }
            sketch.text("Iniciar partida", sketch.width / 2, botonIniciarY);

            if (clickEnBoton(sketch.mouseX, sketch.mouseY, botonEstadisticasY, botonEstadisticasAncho, botonEstadisticasAlto)) {
                sketch.fill(255, 255, 160);
            } else {
                sketch.fill(255);
            }
            sketch.text("Estadisticas", sketch.width / 2, botonEstadisticasY);

            if (clickEnBoton(sketch.mouseX, sketch.mouseY, botonSalirY, botonsSalirAncho, botonSalirAlto)) {
                sketch.fill(255, 255, 160);
            } else {
                sketch.fill(255);
            }
            sketch.text("salir", sketch.width / 2, botonSalirY);
        } else {
            sketch.fill(255);
            sketch.textSize(30);
            sketch.text("Iniciar partida (FUENTE NO ENCONTRADA)", sketch.width / 2, botonIniciarY);
            sketch.text("Estadisticas", sketch.width / 2, botonEstadisticasY);
            sketch.text("salir", sketch.width / 2, botonSalirY);
        }
    }

    private boolean clickEnBoton(int x, int y, int botonY, int botonAncho, int botonAlto) {
        float medioAncho = botonAncho / 2.0f;
        float medioAlto = botonAlto / 2.0f;
        int botonX = sketch.width / 2;

        return (x > botonX - medioAncho && x < botonX + medioAncho &&
                y > botonY - medioAlto  && y < botonY + medioAlto);
    }

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

    public void manejarMousePressed(int mouseX, int mouseY) {
        if (estadoJuego == ESTADO_MENU) {
            if (clickEnBoton(mouseX, mouseY, botonIniciarY, botonIniciarAncho, botonIniciarAlto)) {
                System.out.println("Click en Iniciar");
                iniciarJuego();
            }

            if (clickEnBoton(mouseX, mouseY, botonEstadisticasY, botonEstadisticasAncho, botonEstadisticasAlto)) {
                System.out.println("Click en Estadisticas");
            }

            if (clickEnBoton(mouseX, mouseY, botonSalirY, botonsSalirAncho, botonSalirAlto)) {
                System.out.println("Click en Salir");
                sketch.exit();
            }
        }
    }

    public void manejarKeyReleased(char key, int keyCode) {
        if (jugador != null) {
            jugador.manejarKeyReleased(keyCode);
        }
    }
}
