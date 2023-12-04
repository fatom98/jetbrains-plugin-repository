package data

import com.fasterxml.jackson.annotation.JsonValue

data class Version(private val _value: String) : Comparable<Version> {

  @JsonValue
  val value = removeSpecialCharacters(_value)

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

    private fun removeSpecialCharacters(version: String): String {
      var value = version
      specialCharacters.forEach {value = value.split(it)[0]}

      return value
    }
  }

}
