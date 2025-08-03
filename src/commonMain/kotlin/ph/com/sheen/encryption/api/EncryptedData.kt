package ph.com.sheen.encryption.api

data class EncryptedData(
    val ciphertext: ByteArray,
    val iv: ByteArray, // Initialization Vector for symmetric encryption
    val tag: ByteArray? = null // Authentication tag (e.g., for AES-GCM)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as EncryptedData

        if (!ciphertext.contentEquals(other.ciphertext)) return false
        if (!iv.contentEquals(other.iv)) return false
        if (!tag.contentEquals(other.tag)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ciphertext.contentHashCode()
        result = 31 * result + iv.contentHashCode()
        result = 31 * result + (tag?.contentHashCode() ?: 0)
        return result
    }
}