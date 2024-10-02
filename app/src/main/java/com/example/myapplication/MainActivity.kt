package com.example.myapplication
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.StandardCharsets
import kotlin.math.pow
import kotlin.random.Random
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.pgpainless.PGPainless
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var web3j: Web3j

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        web3j = Web3j.build(HttpService("https://mainnet.infura.io/v3/YOUR-PROJECT-ID"))
    }
}

data class Cmd(
    val data: Array<String>
)

data class PassObj(
    var charSet: String = "",
    var salt: ByteArray,
    var N: Double,
    var r: Int,
    var p: Int,
    var dkLen: Int,
    var passcode: String = "",
    var _passcode: String,
    var password: String
)

fun generatePassword(len: Int): String {
    val charset = getPasswordCharacterSet()
    var result = StringBuilder()

    for (i in 0 until len) {
        result.append(charset[Random.nextInt(charset.length)])
    }

    return result.toString()
}

fun getPasswordCharacterSet(): String {
    val characterSets = listOf(
        Triple(true, "Numbers", "0123456789"),
        Triple(true, "Lowercase", "abcdefghijklmnopqrstuvwxyz"),
        Triple(true, "Uppercase", "ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        Triple(true, "ASCII symbols", "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~")
    )

    var rawCharset = StringBuilder()

    characterSets.forEach { entry ->
        if (entry.first) {
            rawCharset.append(entry.third)
        }
    }

    return rawCharset.toString()
}

fun createNumberPasscode(passcode: String): PassObj {
    val salt = generatePassword((Random.nextDouble() * 64).toInt()).toByteArray(StandardCharsets.UTF_8)
    val N = 2.0.pow(7 + Random.nextInt(6))
    val r = Random.nextInt(10, 21)
    val p = Random.nextInt(10, 21)
    val dkLen = Random.nextInt(32, 65)

    return PassObj(
        charSet = "",
        salt = salt,
        N = N,
        r = r,
        p = p,
        dkLen = dkLen,
        passcode = "",
        _passcode = passcode,
        password = passcode
    )
}

suspend fun createAccount(cmd: Cmd){
    val passcode: String = cmd.data[0]

}
