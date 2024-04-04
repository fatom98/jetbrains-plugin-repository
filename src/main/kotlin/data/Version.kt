package data

import com.fasterxml.jackson.annotation.JsonValue

@JvmInline
value class Version private constructor(@JsonValue val value: String) : Comparable<Version> {

    override operator fun compareTo(other: Version): Int {
        val splitThis = value.split(".")
        val splitOther = other.value.split(".")

        for ((t, o) in splitThis.zip(splitOther)) {
            if (t.toInt() > o.toInt()) return 1
            if (t.toInt() < o.toInt()) return -1
        }

        return splitThis.size - splitOther.size
    }

    companion object {

        private val specialCharacters = listOf('+', '-', '(')

        fun of(version: String): Version = Version(removeSpecialCharacters(version))

        private fun removeSpecialCharacters(version: String): String {
            var value = version
            specialCharacters.forEach { value = value.split(it)[0] }

            return value
        }
    }

}