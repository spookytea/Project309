package com.spookytea.project309

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.spookytea.project309.model.DB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration.Companion.minutes

//Foreground services overview | Background work (no date) Android Developers. Available at: https://developer.android.com/develop/background-work/services/fgs.

//Service to manage need decay even when app closed
class NeedService : Service() {


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Notification required to run foreground service but permission not needed
        val notif_id = "NEED_DECAY_SERVICE"

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            NotificationChannel(notif_id, "Need Decay Service", IMPORTANCE_LOW)
        )

        val notif = NotificationCompat.Builder(this, notif_id).build()

        //Make service run as a foreground service (Persists after app close)
        startForeground(startId, notif)
        return START_STICKY
    }

    override fun onCreate(){
        CoroutineScope(Dispatchers.IO).launch {
            while(true){
                delay(5.minutes) //Update Delay so it slowly goes down
                val db = DB.getDB(this@NeedService).creatureDao()
                db.getAllNotFlow().forEach { //Updates all creatures
                    val dt = LocalDateTime.ofEpochSecond(it.sleepUntil, 0, ZoneOffset.UTC)
                    if(dt.isBefore(LocalDateTime.now())){ //Checks animal is not asleep
                        db.update(
                            it.copy(
                                hungerLevel = max(0, it.hungerLevel - 10),
                                energyLevel = max(0, it.energyLevel - 10),
                                funLevel    = max(0, it.funLevel - 10)
                            )
                        )
                    } else { // If animal asleep
                        val newEnergy = min(it.energyLevel + 4, 100) //Should take nearly 2 hours to finish from empty
                        db.update(
                            it.copy(
                                energyLevel = newEnergy,
                                sleepUntil = if(newEnergy == 100) 0 else it.sleepUntil //Stops sleeping when energy full
                            )
                        )

                        if(newEnergy == 100) { //
                            val notif_id = "NEED_DECAY_SERVICE_AWOKEN"

                            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                                NotificationChannel(notif_id, "Need Decay Service", IMPORTANCE_LOW)
                            )

                            val notif = NotificationCompat.Builder(this@NeedService, notif_id)
                                                          .setContentText(it.name + getString(R.string.has_awoken))
                                                          .build()

                            //Check for notification perm and send notification if done sleeping
                            if(checkSelfPermission(POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
                                NotificationManagerCompat.from(this@NeedService).notify(notif_id, 100, notif)

                        }

                    }

                }

            }
        }
    }
}