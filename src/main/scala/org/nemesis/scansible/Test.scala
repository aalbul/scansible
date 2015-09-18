package org.nemesis.scansible

import org.nemesis.scansible.model._
import org.nemesis.scansible.model.task.AptModuleBuilder.PackageState
import org.nemesis.scansible.model.task.Task._
import org.nemesis.scansible.service.PlaybookExecutor

/**
 * Created by aalbul on 9/14/15.
 */
object Test {

  def main(args: Array[String]) {
    val playbook = Playbook("jenkins")
      .host("testserver")
      .task(apt("install apache2").name("apache2").state(PackageState.Latest).sudo(true))

    val config = Config()
      .hostKeyChecking(false)
      .remoteUser("vagrant")
      .privateKeyFile("/Users/aalbul/Documents/vm/ubuntu14.10/.vagrant/machines/default/virtualbox/private_key")

    val inventory = Inventory().withHost(Host("127.0.0.1", alias = Some("testserver"), port = Some(2222)))

    println(PlaybookExecutor.execute(playbook, config, inventory))
  }
}