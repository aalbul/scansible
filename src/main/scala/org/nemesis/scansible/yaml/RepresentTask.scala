package org.nemesis.scansible.yaml

import org.nemesis.scansible.model.task.Task
import org.yaml.snakeyaml.nodes.Node
import org.yaml.snakeyaml.representer.Represent

import scala.collection.convert.wrapAsJava._

/**
 * Created by aalbul on 9/18/15.
 */
class RepresentTask(representDataFunction: (AnyRef) => Node) extends Represent {
  override def representData(data: scala.Any): Node = {
    val task = data.asInstanceOf[Task]
    val taskMap = List(
      Some("name" -> task.name),
      Some(task.taskType -> mapAsJavaMap(task.attrs)),
      task.notifies.map(notifies => "notify" -> seqAsJavaList(notifies)),
      task.remoteUser.map(remoteUser => "remote_user" -> remoteUser),
      task.sudo.map(sudo => "sudo" -> sudo),
      task.become.map(become => "become" -> become),
      task.becomeUser.map(becomeUser => "become_user" -> becomeUser),
      task.becomeMethod.map(becomeMethod => "become_method" -> becomeMethod)
    ).flatten.toMap
    representDataFunction(mapAsJavaMap(taskMap))
  }
}
