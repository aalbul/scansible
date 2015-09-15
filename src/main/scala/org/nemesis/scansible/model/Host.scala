package org.nemesis.scansible.model

/**
 * Created by aalbul on 9/14/15.
 */
case class Host(address: String, alias: Option[String] = None, port: Option[Int] = None, user: Option[String] = None, password: Option[String] = None,
                connection: Option[AnsibleConnection] = None, privateKeyPath: Option[String] = None, shellType: Option[String] = None,
                 pythonInterpreter: Option[String] = None, otherInterpreters: Map[String, String] = Map()) {
  def withInterpreter(name: String, path: String) = {
    copy(otherInterpreters = otherInterpreters + (name -> path))
  }

  def resolvedAlias = alias.getOrElse(address)

  override def toString: String = {
    val hostStr = s" ansible_ssh_host=$address"
    val portStr = port.map(s => s" ansible_ssh_port=$s").getOrElse("")
    val userStr = user.map(s => s" ansible_ssh_user=$s").getOrElse("")
    val passStr = password.map(s => s" ansible_ssh_pass=$s").getOrElse("")
    val privateKeyStr = privateKeyPath.map(s => s" ansible_ssh_private_key_file=$s").getOrElse("")
    val connectionStr = connection.map(s => s" ansible_connection=${s.name}").getOrElse("")
    val shellStr = shellType.map(s => s" ansible_shell_type=$s").getOrElse("")
    val pythonInterpreterStr = pythonInterpreter.map(s => s" ansible_python_interpreter=$s").getOrElse("")
    val otherInterpretersStr = otherInterpreters.foldLeft("") { case (current, (name, value)) => s"$current ansible_${name}_interpreter=$value" }

    s"$resolvedAlias$hostStr$portStr$userStr$passStr$privateKeyStr$connectionStr$shellStr$pythonInterpreterStr$otherInterpretersStr"
  }
}