package org.nemesis.scansible

import java.nio.file.Files

import org.nemesis.scansible.model.AnsibleConnection.Local
import org.nemesis.scansible.model._
import Inventory._
import org.nemesis.scansible.service.ConfigService

/**
 * Created by aalbul on 9/14/15.
 */
object Test {
  def main(args: Array[String]) {
    val host = Host("vagrant_vm", port = Some(225), user = Some("admin"), password = Some("querty"), connection = Some(Local),
      privateKeyPath = Some("/home/test/a"), shellType = Some("bash"), pythonInterpreter = Some("/usr/local/python2"), otherInterpreters = Map("ruby" -> "/usr/local/ruby", "scala" -> "/usr/local/scala"))
    val host2 = Host("vagrant_vm2")
    val host3 = Host("vagrant_vm3")

    val a = Inventory()
      .withHost(host, Set("web-servers"))
      .withHost(host2)
      .withHost(host3, Set("web-servers"))

//    println(InventoryService.writeToTempFile(a))

    val config = Config().hostKeyChecking(false).recordHostKeys(true).privateKeyFile("/some/path").remoteUser("vagrant")
    val path = Files.createTempFile("scansible", "conf")
    ConfigService.write(config, path)
    println(path)
  }
}