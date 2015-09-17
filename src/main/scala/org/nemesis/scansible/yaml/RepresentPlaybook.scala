package org.nemesis.scansible.yaml

import org.nemesis.scansible.model.Playbook
import org.yaml.snakeyaml.nodes.Node
import org.yaml.snakeyaml.representer.Represent

import scala.collection.convert.wrapAsJava._

/**
 * Created by aalbul on 9/17/15.
 */
class RepresentPlaybook(representDataFunction: (AnyRef) => Node) extends Represent {
  override def representData(data: scala.Any): Node = {
    val playbook = data.asInstanceOf[Playbook]
    val playbookMap = List(
      Some("hosts" -> playbook.hosts.mkString(":")),
      playbook.vars.map(vars => "vars" -> mapAsJavaMap(vars)),
      playbook.remoteUser.map(remoteUser => "remote_user" -> remoteUser),
      playbook.sudo.map(sudo => "sudo" -> sudo),
      playbook.become.map(become => "become" -> become),
      playbook.becomeUser.map(becomeUser => "become_user" -> becomeUser),
      playbook.becomeMethod.map(becomeMethod => "become_method" -> becomeMethod)
    ).flatten.toMap

    representDataFunction(seqAsJavaList(Seq(mapAsJavaMap(playbookMap))))
  }
}