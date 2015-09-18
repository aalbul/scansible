package org.nemesis.scansible.model

import org.nemesis.scansible.model.task.Task

/**
 * Created by aalbul on 9/17/15.
 */
object Playbook {
  def apply(host: String): Playbook = Playbook(host :: Nil)
}

case class Playbook private(hosts: List[String], vars: Option[Map[String, Any]] = None, remoteUser: Option[String] = None,
                            sudo: Option[Boolean] = None, become: Option[Boolean] = None, becomeUser: Option[String] = None,
                            becomeMethod: Option[String] = None, tasks: Option[List[Task]] = None) {
  @inline def host(host: String): Playbook = copy(hosts = host :: hosts)
  @inline def variable(key: String, value: String): Playbook = copy(vars = Some(vars.getOrElse(Map()) + (key -> value)))
  @inline def remoteUser(user: String): Playbook = copy(remoteUser = Some(user))
  @inline def sudo(enabled: Boolean): Playbook = copy(sudo = Some(enabled))
  @inline def become(enabled: Boolean): Playbook = copy(become = Some(enabled))
  @inline def becomeUser(user: String): Playbook = copy(becomeUser = Some(user))
  @inline def becomeMethod(method: String): Playbook = copy(becomeMethod = Some(method))
  @inline def task(task: Task): Playbook = copy(tasks = Some(task :: tasks.getOrElse(Nil)))
}