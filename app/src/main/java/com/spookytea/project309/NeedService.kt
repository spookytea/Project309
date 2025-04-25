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

//https://developer.android.com/develop/background-work/services/fgs
class NeedService : Service() {


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notif_id = "NEED_DECAY_SERVICE"

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            NotificationChannel(notif_id, "Need Decay Service", IMPORTANCE_LOW)
        )

        val notif = NotificationCompat.Builder(this, notif_id).build()

        startForeground(startId, notif)
        return START_STICKY
    }

    override fun onCreate(){
        CoroutineScope(Dispatchers.IO).launch {
            while(true){
                delay(1.25.minutes)
                val db = DB.getDB(this@NeedService).creatureDao()
                db.getAllNotFlow().forEach {
                    val dt = LocalDateTime.ofEpochSecond(it.sleepUntil, 0, ZoneOffset.UTC)
                    if(dt.isBefore(LocalDateTime.now())){
                        db.update(
                            it.copy(
                                hungerLevel = max(0, it.hungerLevel - 2),
                                energyLevel = max(0, it.energyLevel - 2),
                                funLevel    = max(0, it.funLevel - 2)
                            )
                        )
                    } else {
                        val newEnergy = min(it.energyLevel + 20, 100)
                        db.update(
                            it.copy(
                                energyLevel = newEnergy,
                                sleepUntil = if(newEnergy == 100) 0 else it.sleepUntil
                            )
                        )

                        if(newEnergy == 100) {
                            val notif_id = "NEED_DECAY_SERVICE_AWOKEN"

                            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                                NotificationChannel(notif_id, "Need Decay Service", IMPORTANCE_LOW)
                            )

                            val notif = NotificationCompat.Builder(this@NeedService, notif_id)
                                                          .setContentText("")
                                                          .build()

                            if(checkSelfPermission(POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
                                NotificationManagerCompat.from(this@NeedService).notify(notif_id, 100, notif)


                        }





                    }

                }

            }
        }
    }
}