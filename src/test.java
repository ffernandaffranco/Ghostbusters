public class test {
}


void draw() {
    switch (estadoJuego) {

        caso 0:

        case 1: // --- JUGANDO ---
            // ... (mover todo, dibujar todo, chequear colisiones...)

            // Revisas las colisiones del jugador
            // (Lógica para ver si un proyectil enemigo le da)
            if (jugador.colisionaCon(proyectilEnemigo)) {
                jugador.recibirDaño(1);
            }

            // !! AQUÍ ESTÁ LA LÓGICA CLAVE !!
            // Al final de todas las revisiones, preguntas por su estado:
            if (!jugador.estaVivo()) {
                // NO lo eliminas.
                // CAMBIAS EL ESTADO DEL JUEGO.
                estadoJuego = 2; // (Donde '2' es el estado de "Game Over")

                // Este es también el momento ideal para guardar estadísticas
                guardarEstadisticas();
            }
            break;

        case 2: // --- GAME OVER ---
            // Como estadoJuego ahora es 2, el juego salta aquí automáticamente.
            // Ya no se dibuja ni se mueve el jugador.
            dibujarPantallaGameOver();
            break;
    }
}