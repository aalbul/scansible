package org.nemesis.scansible.model

/**
 * Created by aalbul on 9/15/15.
 */
sealed trait HashBehaviour {
  val name: String
}

object HashBehaviour {

  case object Replace extends HashBehaviour {
    override val name: String = "replace"
  }

  case object Merge extends HashBehaviour {
    override val name: String = "merge"
  }

}