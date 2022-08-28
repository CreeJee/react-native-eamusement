package com.creejee.rneamusement.konamiCardX


object E {
    fun a(arg4: CharSequence): ByteArray {
//        if (arg4.length % 2 != 0) {
//            throw new d("Trailing nibble detected");
//        }

        val v1 = ByteArray(arg4.length / 2)
        var v0 = 0
        while (v0 < v1.size) {
            v1[v0] = (a(arg4[v0 * 2]).toInt() shl 4 or a(arg4[v0 * 2 + 1]).toInt()).toByte()
            ++v0
        }

        return v1
    }

    fun a(arg5: ByteArray): String {
        val v1 = "0123456789ABCDEF"
        val v2 = CharArray(arg5.size shl 1)
        var v0 = 0
        while (v0 < arg5.size) {
            v2[v0 * 2] = v1[(arg5[v0].toInt() and 255).ushr(4)]
            v2[v0 * 2 + 1] = v1[arg5[v0].toInt() and 15]
            ++v0
        }

        return String(v2)
    }

    private fun a(arg3: Char): Byte {
        val v0: Byte
        if (arg3.code < 48 || arg3.code > 57) {
            if (arg3.code in 65..70) {
                return (arg3.code - 55).toByte()
            }

            return if (arg3.code in 97..102) {
                (arg3.code - 87).toByte()
            } else 0x00

        } else {
            v0 = (arg3.code - 48).toByte()
        }

        return v0
    }
}