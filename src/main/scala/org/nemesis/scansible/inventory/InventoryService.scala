package org.nemesis.scansible.inventory

import java.io.PrintWriter
import java.nio.file.{Files, Path}
import org.nemesis.scansible.model.Inventory

/**
 * Created by aalbul on 9/15/15.
 */
object InventoryService {
  def writeToTempFile(inventory: Inventory): Path = {
    val file = Files.createTempFile("scansible", "repo")
    write(inventory, file)
    file
  }

  def write(inventory: Inventory, inventoryFile: Path): Unit = {
    Files.createDirectories(inventoryFile.getParent)

    val writer = new PrintWriter(inventoryFile.toFile)
    try {
      inventory.hostGroups.keys.foreach(writer.println)
      val hostsToGroup = inventory
        .hostGroups
        .toList
        .flatMap { case (host, groups) => groups.map(group => group -> host).toList }
        .groupBy { case (group, _) => group }
        .map { case (group, hosts) => group -> hosts.map { case (_, host) => host } }
        .foreach { case (group, hosts) =>
          writer.println()
          writer.println(s"[${group.name}]")
          hosts.foreach(h => writer.println(h.resolvedAlias))
        }
    } finally {
      writer.close()
    }
  }

}