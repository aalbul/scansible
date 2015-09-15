package org.nemesis.scansible.model

/**
 * Created by aalbul on 9/14/15.
 */
sealed trait AnsibleConnection {
  val name: String
}

object AnsibleConnection {

  object Smart extends AnsibleConnection {
    override val name: String = "smart"
  }

  object Local extends AnsibleConnection {
    override val name: String = "local"
  }

  object Ssh extends AnsibleConnection {
    override val name: String = "ssh"
  }

  object Paramiko extends AnsibleConnection {
    override val name: String = "paramiko"
  }

}