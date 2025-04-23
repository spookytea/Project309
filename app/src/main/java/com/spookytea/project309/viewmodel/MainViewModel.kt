package com.spookytea.project309.viewmodel

import android.app.Application
import android.telephony.SmsManager
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spookytea.project309.R
import com.spookytea.project309.model.Creature
import com.spookytea.project309.model.DB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.crypto.Cipher
import javax.crypto.Cipher.ENCRYPT_MODE
import javax.crypto.spec.SecretKeySpec
import kotlin.math.min

class MainViewModel(private val app: Application) : AndroidViewModel(app){


    private val dao = DB.getDB(app.applicationContext).creatureDao()
    var creatures = dao.getAll()
        private set

    var selectedIndex by  mutableIntStateOf(0)
    val creatureCount = dao.rowCount()



    fun getArt(creature: Creature): String =
        app.applicationContext.resources.getStringArray(R.array.animals)[creature.artIndex]


    val isEmpty = dao.rowCount().map { it == 0 }

    fun addCreature(name:String, artIndex: Int, hue: Float) = viewModelScope.launch {
        Creature(name = name, artIndex = artIndex, hue = hue).apply { dao.add(this) }
    }

    fun upHunger() = viewModelScope.launch(Dispatchers.IO) {
        val current = dao.getCurrent(selectedIndex)
        dao.update(current.copy(hungerLevel = min(current.hungerLevel + 20, 100)))
    }


    @Suppress("unused")
    fun send(destination: String) = viewModelScope.launch(Dispatchers.IO){
        val current = dao.getCurrent(selectedIndex)
        val sms = app.applicationContext.getSystemService(SmsManager::class.java)

        val creature_enc = "Add \"${current.name}\" to your app:\n${encrypt_string(Json.encodeToString(current))}"

        sms.sendMultipartTextMessage(
            destination,
            null,
            sms.divideMessage(creature_enc),
            null,
            null
        )



    }

    private fun encrypt_string(str: String): String{
        val passcode = "Creature_PASSCODE569qwer".encodeToByteArray()

        Cipher.getInstance("AES/CBC/PKCS5Padding").apply{
            init(ENCRYPT_MODE, SecretKeySpec(passcode, "AES"))
            return URLEncoder.encode(
                Base64.encodeToString(
                    doFinal(str.encodeToByteArray()),
                    Base64.DEFAULT
                ),
                Charsets.UTF_8
            )
        }
    }

    fun sleep() = viewModelScope.launch(Dispatchers.IO){
        dao.update(
            dao.getCurrent(selectedIndex).copy(
                sleepUntil = LocalDateTime.now().plusHours(2).toEpochSecond(ZoneOffset.UTC)
            )
        )
    }

    fun is_asleep(creature: Creature) =
        LocalDateTime.now().isBefore(
            LocalDateTime.ofEpochSecond(creature.sleepUntil, 0, ZoneOffset.UTC)
        )




}