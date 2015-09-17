package org.nemesis.scansible.service

import java.nio.file.Files

import org.nemesis.scansible.model.{Config, Host, Inventory}

import scala.sys.process._

/**
 * Created by aalbul on 9/16/15.
 */
object CommandExecutor {
  def execute(config: Config, inventory: Inventory) = {
    val configPath = Files.createTempFile("scansible", "config").toAbsolutePath
    val inventoryPath = Files.createTempFile("scansible", "inventory").toAbsolutePath
    ConfigService.write(config, configPath)
    InventoryService.write(inventory, inventoryPath)

    val builder = new StringBuilder("")
    val proc = Process(Seq("ansible", "all", "-a", "uptime", "-i", inventoryPath.toString), None, "ANSIBLE_CONFIG" -> configPath.toString)
    proc ! ProcessLogger((out) => builder.append(out).append("\n"))
    println(builder.toString())
  }

  def main(args: Array[String]) {
    val config = Config()
      .hostKeyChecking(false)
      .remoteUser("vagrant")
      .privateKeyFile("/Users/aalbul/Documents/vm/ubuntu14.10/.vagrant/machines/default/virtualbox/private_key")

    val inventory = Inventory().withHost(Host("127.0.0.1", alias = Some("testserver"), port = Some(2222)))

    execute(config, inventory)
  }
}
