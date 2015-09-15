package org.nemesis.scansible.service

import java.io.PrintWriter
import java.nio.file.{Files, Path}

import org.nemesis.scansible.model.Config

/**
 * Created by aalbul on 9/15/15.
 */
object ConfigService {
  def write(config: Config, configPath: Path): Unit = {
    Files.createDirectories(configPath.getParent)

    def writeSection(name: String, variables: Map[String, String])(implicit writer: PrintWriter): Unit = {
      if (variables.nonEmpty) {
        writer.println(s"[$name]")
        variables.foreach { case (key, value) => writer.println(s"$key=$value") }
        writer.println()
      }
    }

    implicit val writer = new PrintWriter(configPath.toFile)
    try {
      writeSection("defaults", config.defaults)
      writeSection("privilege_escalation", config.privilegeEscalation)
      writeSection("paramiko_connection", config.paramikoConnection)
      writeSection("ssh_connection", config.sshConnection)
      writeSection("accelerate", config.accelerate)
      writeSection("selinux", config.selinux)
    } finally {
      writer.close()
    }
  }
}