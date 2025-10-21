public class test {
}

//
//void draw() {
//    switch (estadoJuego) {
//
//        caso 0:
//        case 1: // --- JUGANDO ---
//            // ... (mover todo, dibujar todo, chequear colisiones...)
//
//            // Revisas las colisiones del jugador
//            // (Lógica para ver si un proyectil enemigo le da)
//            if (jugador.colisionaCon(proyectilEnemigo)) {
//                jugador.recibirDaño(1);
//            }
//
//            // !! AQUÍ ESTÁ LA LÓGICA CLAVE !!
//            // Al final de todas las revisiones, preguntas por su estado:
//            if (!jugador.estaVivo()) {
//                // NO lo eliminas.
//                // CAMBIAS EL ESTADO DEL JUEGO.
//                estadoJuego = 2; // (Donde '2' es el estado de "Game Over")
//
//                // Este es también el momento ideal para guardar estadísticas
//                guardarEstadisticas();
//            }
//            break;
//
//        case 2: // --- GAME OVER ---
//            // Como estadoJuego ahora es 2, el juego salta aquí automáticamente.
//            // Ya no se dibuja ni se mueve el jugador.
//            dibujarPantallaGameOver();
//            break;
//    }
//}


// Bucle principal del juego (se ejecuta 60 veces por segundo)
void draw() {
    // 1. Limpia la pantalla en cada frame
    background(0); // O dibuja la imagen de fondo

    // 2. LA MÁQUINA DE ESTADOS
    // El switch decide qué lógica y qué pantalla ejecutar
    switch (estadoJuego) {

        case ESTADO_MENU:
            // Si estamos en el menú, solo dibuja el menú
            dibujarMenu();
            break;

        case ESTADO_JUGANDO:
            // Si estamos jugando, ejecuta la lógica Y dibuja el juego
            actualizarLogicaJuego();
            dibujarJuego();
            break;

        case ESTADO_GAMEOVER:
            // Si perdimos, solo dibuja la pantalla de Game Over
            dibujarGameOver();
            break;
    }
}

/**
 * Actualiza toda la lógica del juego:
 * - Mueve todas las entidades.
 * - Gestiona los disparos de los enemigos.
 * - Revisa todas las colisiones.
 * - Revisa si el jugador está vivo.
 * - Limpia las listas de entidades "muertas".
 */
void actualizarLogicaJuego() {

    // --- 1. Mover todas las entidades ---
    // (El mover() del jugador está vacío, pero se lo podría llamar por consistencia)

    for (Fantasma f : fantasmas) {
        f.mover();
        // Gestionar disparos de fantasmas
        Proyectil pEnemigo = f.disparar(); // (Este método tiene un random, puede devolver null)
        if (pEnemigo != null) {
            proyectilesEnemigos.add(pEnemigo);
        }
    }

    for (Proyectil p : proyectilesAliados) {
        p.mover();
    }

    for (Proyectil p : proyectilesEnemigos) {
        p.mover();
    }

    // --- 2. Revisar colisiones y salidas de pantalla ---

    // A. Proyectiles aliados vs. Fantasmas
    for (Proyectil pAliado : proyectilesAliados) {
        for (Fantasma f : fantasmas) {
            if (f.colisionaCon(pAliado)) {
                f.recibirDaño(1); // El fantasma recibe daño
                pAliado.destruir();   // El proyectil se marca para borrar

                if (!f.estaVivo()) {
                    puntaje += f.score; // Suma el puntaje si el fantasma murió
                }
            }
        }
    }

    // B. Proyectiles enemigos vs. Jugador
    for (Proyectil pEnemigo : proyectilesEnemigos) {
        if (jugador.colisionaCon(pEnemigo)) {
            jugador.recibirDaño(1); // El jugador recibe daño
            pEnemigo.destruir();    // El proyectil se marca para borrar
        }
    }

    // C. Proyectiles que se salen de la pantalla
    for (Proyectil p : proyectilesAliados) {
        if (p.estaFueraDePantalla()) {
            p.destruir();
        }
    }
    for (Proyectil p : proyectilesEnemigos) {
        if (p.estaFueraDePantalla()) {
            p.destruir();
        }
    }

    // D. Fantasmas que se salen de la pantalla (si un fantasma llega al final)
    for (Fantasma f : fantasmas) {
        if (f.estaFueraDePantalla()) {
            // Opcional: El jugador pierde una vida si un fantasma "escapa"
            // jugador.recibirDaño(1);
            f.recibirDaño(100); // Lo marca para borrar
        }
    }

    // --- 3. Limpieza de listas ---
    // Elimina de forma segura todos los objetos marcados como "inactivos" o "muertos"
    proyectilesAliados.removeIf(p -> !p.estaActivo());
    proyectilesEnemigos.removeIf(p -> !p.estaActivo());
    fantasmas.removeIf(f -> !f.estaVivo());

    // --- 4. Revisión de condición de fin de juego ---
    // Revisa si el jugador sigue vivo DESPUÉS de todas las colisiones
    if (!jugador.estaVivo()) {
        estadoJuego = ESTADO_GAMEOVER; // ¡Cambia el estado!
        // guardarEstadisticas(); // Este sería un buen lugar para llamar a esto
    }

    // Opcional: Lógica para crear nuevos fantasmas (spawner)
    // if (frameCount % 120 == 0) { // Cada 2 segundos, por ejemplo
    //   spawnNuevoFantasma();
    // }
}

/**
 * Dibuja todas las entidades del juego y la interfaz (UI).
 */
void dibujarJuego() {
    // Dibuja todas las entidades
    jugador.dibujar();

    for (Fantasma f : fantasmas) {
        f.dibujar();
    }

    for (Proyectil p : proyectilesAliados) {
        p.dibujar();
    }

    for (Proyectil p : proyectilesEnemigos) {
        p.dibujar();
    }

    // Dibuja la Interfaz de Usuario (UI)
    fill(255);
    textSize(24);
    textAlign(LEFT, TOP);
    text("Puntaje: " + puntaje, 10, 10);

    textAlign(RIGHT, TOP);
    // Asumiendo que añades un getVidas() a Personaje y Cazafantasma
    // text("Vidas: " + jugador.getVidas(), width - 10, 10);

    // Restaura la alineación por si acaso
    textAlign(CENTER, CENTER);
}