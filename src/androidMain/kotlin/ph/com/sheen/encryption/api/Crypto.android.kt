@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package ph.com.sheen.encryption.api

import android.security.keystore.KeyProperties
import android.util.Base64
import ph.com.sheen.encryption.model.CryptoKey
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

private const val AES_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
private const val TRANSFORMATION = "$AES_ALGORITHM/$BLOCK_MODE/$PADDING"

actual class Crypto {
    actual val symmetricCrypto: SymmetricCrypto = AndroidSymmetricCrypto()
}

class AndroidSymmetricCrypto : SymmetricCrypto {

    val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
    val cryptoKey = generateKey()
    val ivSpec = IvParameterSpec(cryptoKey.raw)

    override fun generateKey(): CryptoKey {
        val random = SecureRandom()
        val iv = ByteArray(cipher.blockSize)
        random.nextBytes(iv)
        return CryptoKey(iv)
    }

    override fun encrypt(data: ByteArray, key: CryptoKey): String {
        try {

            val secretSpec = SecretKeySpec(key.raw, AES_ALGORITHM)

            cipher.init(Cipher.ENCRYPT_MODE, secretSpec, ivSpec)

            val encryptedBytes = cipher.doFinal(data)
            val encryptedDataWithIV = cryptoKey.raw + encryptedBytes

            return Base64.encodeToString(encryptedDataWithIV, Base64.DEFAULT)
        } catch (e: Exception) {
//            TODO() //handle the exception
            return e.toString()
        }
    }

    override fun decrypt(
        ciphertext: ByteArray,
        key: CryptoKey
    ): String {
        val encryptedDataWithIV = Base64.decode(ciphertext, Base64.DEFAULT)
        val iv = encryptedDataWithIV.copyOfRange(0, cipher.blockSize)
        val encryptedData =
            encryptedDataWithIV.copyOfRange(cipher.blockSize, encryptedDataWithIV.size)

        val secretSpec = SecretKeySpec(key.raw, AES_ALGORITHM)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, secretSpec, ivSpec)

        val decryptedBytes = cipher.doFinal(encryptedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}
