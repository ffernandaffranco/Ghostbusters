import processing.core.PApplet;
import processing.core.PImage;

public class Leila extends Personaje {

    private PImage spriteProyectil;

    private boolean arribaPresionado = false;
    private boolean abajoPresionado = false;
    private boolean izquierdaPresionado = false;
    private boolean derechaPresionado = false;
    
    private int tiempoUltimoDisparo = 0;
    private final int COOLDOWN_DISPARO = 20; // frames de espera entre disparo y disparo


    public Leila(PApplet sketch, float x, float y, PImage imagen, int vida, int velocidad, int radio, PImage spriteProyectil) {
        super(sketch, x, y, imagen, vida, velocidad, radio);
        this.spriteProyectil = spriteProyectil;
    }

    @Override
    public void mover() {
        int direccionX = 0;
        int direccionY = 0;

        if (arribaPresionado) {
            direccionY = -1;
        }
        if (abajoPresionado) {
            direccionY = 1;
        }
        if (izquierdaPresionado) {
            direccionX = -1;
        }
        if (derechaPresionado) {
            direccionX = 1;
        }
        
        if (direccionX != 0 || direccionY != 0) {
            this.x += this.velocidad * direccionX;
            this.x = PApplet.constrain(this.x, 0 + this.radio, sketch.width - this.radio);

            this.y += this.velocidad * direccionY;
            this.y = PApplet.constrain(this.y, 0 + this.radio, sketch.height - this.radio);
        }
    }

    @Override
    public Proyectil disparar() {
        if (sketch.keyPressed && sketch.key == ' ') { 
            
            int frameActual = sketch.frameCount;
            if (frameActual - tiempoUltimoDisparo >= COOLDOWN_DISPARO) {
                
                tiempoUltimoDisparo = frameActual; 

                float posX = this.x + this.radio;
                float posY = this.y;
                int velProyectil = 15;
                int radioProyectil = 8;
                int dirProyectil = 1;
                boolean esAliado = true;
                return new Proyectil(sketch, posX, posY, this.spriteProyectil, velProyectil, radioProyectil, dirProyectil, esAliado);
            }
        } 
        return null; 
    }

    public void manejarKeyPressed(int keyCode) {
        if (keyCode == PApplet.UP) {
            arribaPresionado = true;
        } else if (keyCode == PApplet.DOWN) {
            abajoPresionado = true;
        } else if (keyCode == PApplet.LEFT) {
            izquierdaPresionado = true;
        } else if (keyCode == PApplet.RIGHT) {
            derechaPresionado = true;
        }
    }

    public void manejarKeyReleased(int keyCode) {
         if (keyCode == PApplet.UP) {
            arribaPresionado = false;
        } else if (keyCode == PApplet.DOWN) {
            abajoPresionado = false;
        } else if (keyCode == PApplet.LEFT) {
            izquierdaPresionado = false;
        } else if (keyCode == PApplet.RIGHT) {
            derechaPresionado = false;
        }
    }

    public int getVidas() {
        return this.vida;
    }
}
