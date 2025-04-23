package com.spookytea.project309

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.spookytea.project309.model.DB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.max
import kotlin.math.min

//https://developer.android.com/develop/background-work/services/fgs
class NeedService : Service() {


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notif_id = "NEED_DECAY_SERVICE"

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            NotificationChannel(notif_id, "Need Decay Service", IMPORTANCE_HIGH)
        )

        val notif = NotificationCompat.Builder(this, notif_id).build()

        startForeground(startId, notif)
        return START_STICKY
    }

    override fun onCreate(){
        CoroutineScope(Dispatchers.IO).launch {
            while(true){

                delay(20000)
                val db = DB.getDB(this@NeedService).creatureDao()
                db.getAllNotFlow().forEach {
                    val dt = LocalDateTime.ofEpochSecond(it.sleepUntil, 0, ZoneOffset.UTC)
                    if(dt.isBefore(LocalDateTime.now())){
                        db.update(
                            it.copy(
                                hungerLevel = max(1, it.hungerLevel - 20),
                                energyLevel = max(1, it.energyLevel - 20),
                                funLevel    = max(1, it.funLevel - 20)
                            )
                        )
                    } else {
                        db.update(
                            it.copy(
                                energyLevel = min(
                                    it.energyLevel +4,
                                    100
                                )
                            )
                        )
                        if(it.energyLevel == 100) db.update(it.copy(sleepUntil = 0))

                    }

                }

            }
        }
    }
}