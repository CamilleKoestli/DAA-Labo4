/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier définit la classe App, qui étend Application et initialise les ressources
 * globales nécessaires à l'application. Elle configure un scope de coroutine pour exécuter des opérations
 * en arrière-plan et crée une instance paresseuse de la base de données Room.
 */

package ch.heigvd.iict.daa.template

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class App : Application() {

    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database: NotesDatabase by lazy { NotesDatabase.getDatabase(this) }
}
