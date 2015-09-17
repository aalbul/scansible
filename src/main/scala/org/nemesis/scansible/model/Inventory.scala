package org.nemesis.scansible.model

/**
 * Created by aalbul on 9/14/15.
 */
object Inventory {
  implicit def stringToHost(string: String): Host = Host(string)
}

case class Inventory(hostGroups: Map[Host, Set[String]] = Map()) {
  def withHost(host: Host): Inventory = {
    withHost(host, Set())
  }

  def withHost(host: Host, groups: Set[String]): Inventory = {
    val newHostGroups = hostGroups.getOrElse(host, groups) ++ groups
    copy(hostGroups = hostGroups + (host -> newHostGroups))
  }
}