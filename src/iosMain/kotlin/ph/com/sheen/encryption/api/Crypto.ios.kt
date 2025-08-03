package ph.com.sheen.encryption.api

import ph.com.sheen.encryption.model.CryptoKey

actual class Crypto {
    actual val symmetricCrypto: SymmetricCrypto = IosSymmetricCrypto()
}

class IosSymmetricCrypto : SymmetricCrypto {
    override fun generateKey(): CryptoKey {
        TODO("Not yet implemented")
    }

    override fun encrypt(
        data: ByteArray,
        key: CryptoKey
    ): String {
        TODO("Not yet implemented")
    }

    override fun decrypt(
        ciphertext: ByteArray,
        key: CryptoKey
    ): String {
        TODO("Not yet implemented")
    }

}