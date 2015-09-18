package org.nemesis.scansible.model.task

/**
 * Created by aalbul on 9/18/15.
 */
object Task {
  implicit def moduleBuilderToTask(builder: ModuleBuilder): Task = builder.task

  def apt(name: String) = AptModuleBuilder(name)
  def apply(name: String, taskType: String, attrs: Map[String, Any]) = new Task(name, taskType, attrs)
}

case class Task private(name: String, taskType: String, attrs: Map[String, Any], notifies: Option[List[String]] = None,
                remoteUser: Option[String] = None, sudo: Option[Boolean] = None, become: Option[Boolean] = None,
                becomeUser: Option[String] = None, becomeMethod: Option[String] = None) {
  @inline def attr(name: String, value: String): Task = copy(attrs = attrs + (name -> value))
  @inline def notify(name: String): Task = copy(notifies = Some(notifies.getOrElse(Nil)))
  @inline def remoteUser(user: String): Task = copy(remoteUser = Some(user))
  @inline def sudo(enabled: Boolean): Task = copy(sudo = Some(enabled))
  @inline def become(enabled: Boolean): Task = copy(become = Some(enabled))
  @inline def becomeUser(user: String): Task = copy(becomeUser = Some(user))
  @inline def becomeMethod(method: String): Task = copy(becomeMethod = Some(method))
}