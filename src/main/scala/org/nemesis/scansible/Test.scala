package org.nemesis.scansible

import java.nio.file.Files
import java.util

import org.nemesis.scansible.model.AnsibleConnection.Local
import org.nemesis.scansible.model._
import Inventory._
import org.nemesis.scansible.service.ConfigService
import org.nemesis.scansible.yaml.PlaybookRepresenter
import org.yaml.snakeyaml.DumperOptions.{ScalarStyle, FlowStyle}
import org.yaml.snakeyaml.{DumperOptions, Yaml}
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.introspector.BeanAccess
import org.yaml.snakeyaml.nodes.{Tag, Node}
import org.yaml.snakeyaml.representer.{Represent, Representer}

/**
 * Created by aalbul on 9/14/15.
 */
object Test {

  def main(args: Array[String]) {
//    val host = Host("vagrant_vm", port = Some(225), user = Some("admin"), password = Some("querty"), connection = Some(Local),
//      privateKeyPath = Some("/home/test/a"), shellType = Some("bash"), pythonInterpreter = Some("/usr/local/python2"), otherInterpreters = Map("ruby" -> "/usr/local/ruby", "scala" -> "/usr/local/scala"))
//    val host2 = Host("vagrant_vm2")
//    val host3 = Host("vagrant_vm3")
//
//    val a = Inventory()
//      .withHost(host, Set("web-servers"))
//      .withHost(host2)
//      .withHost(host3, Set("web-servers"))
//
//
//    val config = Config().hostKeyChecking(false).recordHostKeys(true).privateKeyFile("/some/path").remoteUser("vagrant")
//    val path = Files.createTempFile("scansible", "conf")
//    ConfigService.write(config, path)
//    println(path)

    val options = new DumperOptions()
    options.setExplicitStart(true)
    options.setDefaultFlowStyle(FlowStyle.BLOCK)
    val representer = new PlaybookRepresenter

    val yaml = new Yaml(representer, options)
    yaml.setBeanAccess(BeanAccess.FIELD)
    val playbook = Playbook("jenkins").sudo(true).become(false).variable("a", "b").variable("c", "d").becomeUser("vagrant")

    println(yaml.dump(playbook))
  }
}