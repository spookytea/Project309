package com.spookytea.project309.viewmodel

import android.app.Application
import android.telephony.SmsManager
import android.util.Base64
import com.spookytea.project309.model.Creature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Cipher.ENCRYPT_MODE
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class SMSViewModel(app:Application) : PagerViewModel(app) {
    //SmsManager | API reference (no date) Android Developers. Available at: https://developer.android.com/reference/android/telephony/SmsManager.
    fun send(destination: String) = runBlocking(Dispatchers.IO){
        val current = dao.getCurrent(selectedIndex)
        val sms = app.applicationContext.getSystemService(SmsManager::class.java)

        val creatureEnc = "Add \"${current.name}\" to your app:\n${encryptString(Json.encodeToString(current))}"

        sms.sendMultipartTextMessage( //Sends message split to avoid char limits
            destination,
            null,
            sms.divideMessage(creatureEnc),
            null,
            null
        )
    }

    fun import(str: String) = runBlocking(Dispatchers.IO) {
        dao.add(Json.decodeFromString(decryptString(str)))//Imports animal from encrypted JSON
    }

    //Java AES Encryption and Decryption | Baeldung (2020). Available at: https://www.baeldung.com/java-aes-encryption-decryption.
    private fun getCipher(mode: Int): Cipher =
        Cipher.getInstance("AES/CBC/PKCS5Padding").apply {
            //Uses hardcoded iv and passcode to ensure can be used without passing args over sms
            val pass = "Creature_PASSCODE569qwer".encodeToByteArray()
            val iv = byteArrayOf(0,6,7,8,3,8,9,9,7,1,2,5,3,2,1,0) //Required for CBC
            init(mode, SecretKeySpec(pass, "AES"), IvParameterSpec(iv))
        } //Gets cipher for encryption

    private fun encryptString(str: String): String = URLEncoder.encode(
        Base64.encodeToString(getCipher(ENCRYPT_MODE).doFinal(str.encodeToByteArray()), Base64.DEFAULT),
        Charsets.UTF_8
    ) //Encrypts string, encodes to base64 and then URL encodes to get code for user

    private fun decryptString(str: String): String = getCipher(DECRYPT_MODE).doFinal(
        Base64.decode(URLDecoder.decode(str, Charsets.UTF_8), Base64.DEFAULT),
    ).decodeToString() // Reverse of above

    override fun isAsleep(creature: Creature) = false //Disables sleep check in SMS Dialogs







}