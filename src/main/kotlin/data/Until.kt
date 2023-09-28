package data

import com.fasterxml.jackson.annotation.JsonValue

class Until(value: String) : Comparable<Until> {

  @JsonValue
  val value = removeSpecialCharacters(value)

  override operator fun compareTo(other: Until): Int {
    if (value == "" && other.value == "") return 0
    if (value == "") return 1
    if (other.value == "") return -1

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

    private fun removeSpecialCharacters(until: String): String {
      var value = until
      specialCharacters.forEach { value = value.split(it)[0] }

      return value
    }
  }

}