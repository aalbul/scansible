package org.nemesis.scansible.model

/**
 * Created by aalbul on 9/14/15.
 */
sealed trait InventoryGroup {
  val name: String
}

case object * extends InventoryGroup {
  override val name: String = "*"
}

case class Group(name: String) extends InventoryGroup