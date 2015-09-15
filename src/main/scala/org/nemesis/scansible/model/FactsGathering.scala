package org.nemesis.scansible.model

/**
 * Created by aalbul on 9/15/15.
 *
 *
 * Plays will gather facts by default, which contain information about the remote system
 */
sealed trait FactsGathering {
  val name: String
}

object FactsGathering {

  /**
   * Gather by default, but don't regather if already gathered
   */
  case object Smart extends FactsGathering {
    override val name: String = "smart"
  }

  /**
   * Gather by default, turn off with gather_facts: False
   */
  case object Implicit extends FactsGathering {
    override val name: String = "implicit"
  }

  /**
   * Do not gather by default, must say gather_facts: True
   */
  case object Explicit extends FactsGathering {
    override val name: String = "explicit"
  }
}