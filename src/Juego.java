public class Juego {
    public boolean isGameOver;
    public int puntaje; // usa el atributo publico score de fantasma
    public int vidas;
    public int fase;
    // listaFantasmas
    // listaProyectiles

    public void detectarColision() {
        // ver si coinciden las posiciones de 2 entidades y actuar dependiendo de que entidad es
        // una si o si es proyectil
    }

    public void GameOver() {
        // termina el juego si tenes 0 vidas (isGameOver = true)
        // muestra una pantalla de game over
    }

    public void CambiarDeFase() {
        // hace cambios dependiendo del score de Juego
        // aumenta la velocidad de los bichos, cambia el fondo, etc
    }

    public void borrarEntidad() {
        // parámetro: si estarVivo() es false entonces borrarEntidad()
        // elimina de las listas a las entidades que corresponda
        // ej: disparos que impactaron, enemigos que fueron eliminados, etc
    }

    public void guardarEstadisticas() {
        // se ejecuta cuando isGamerOver = true
        // actualiza el csv
    }

    public void mostrarEstadisticas() {
        // agarra el csv
        // muestra en pantalla distintas metricas
    }

    public void draw() {
        // DUDA: necesitamos un metodo que dibuje interfaces gráficas
        // fondos
        // colisiones????????????? Como juego gestiona las colisiones quien dibuja las colisiones, juego o las entidades colisionando?
        //
    }

    public void cargarImagen() {
        // El sistema carga las imagenes del fondo (?
        // Pero: Es necesario que este metodo este en entidad, para que todas las clases que heredan de ella puedan cargar sus imagenes?
    }
}