package ph.com.sheen.encryption.api

import ph.com.sheen.encryption.model.CryptoKey

expect class Crypto() {
    val symmetricCrypto: SymmetricCrypto
}

interface SymmetricCrypto {
    fun generateKey(): CryptoKey
    fun encrypt(data: ByteArray, key: CryptoKey): String
    fun decrypt(ciphertext: ByteArray, key: CryptoKey): String
}