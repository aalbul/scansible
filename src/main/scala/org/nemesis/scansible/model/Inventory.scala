package org.nemesis.scansible.model

import org.nemesis.scansible.model.InventoryGroup.Group

/**
 * Created by aalbul on 9/14/15.
 */
object Inventory {
  implicit def stringToHost(string: String): Host = Host(string)
  implicit def stringToGroup(string: String): Group = Group(string)
}

case class Inventory(hostGroups: Map[Host, Set[Group]] = Map()) {
  def withHost(host: Host): Inventory = {
    withHost(host, Set())
  }

  def withHost(host: Host, groups: Set[Group]): Inventory = {
    val newHostGroups = hostGroups.getOrElse(host, groups) ++ groups
    copy(hostGroups = hostGroups + (host -> newHostGroups))
  }
}