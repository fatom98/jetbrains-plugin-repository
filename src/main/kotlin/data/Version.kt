package data

import com.fasterxml.jackson.annotation.JsonValue

class Version(@JsonValue val value: String) : Comparable<Version>{

  override operator fun compareTo(other: Version): Int {
    return value.compareTo(other.value)
  }

}

