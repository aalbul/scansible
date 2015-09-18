package org.nemesis.scansible.service

import java.io.FileWriter
import java.nio.file.{Path, Files}

import org.nemesis.scansible.model.{Playbook, Config, Inventory}
import org.nemesis.scansible.yaml.PlaybookRepresenter
import org.yaml.snakeyaml.introspector.BeanAccess
import org.yaml.snakeyaml.{Yaml, DumperOptions}
import org.yaml.snakeyaml.DumperOptions.FlowStyle

import scala.sys.process._

/**
 * Created by aalbul on 9/16/15.
 */
object PlaybookExecutor {
  def execute(playbook: Playbook, config: Config, inventory: Inventory) = {
    val configPath = Files.createTempFile("scansible", "config").toAbsolutePath
    val inventoryPath = Files.createTempFile("scansible", "inventory").toAbsolutePath
    val playbookPath = Files.createTempFile("scansible", "playbook").toAbsolutePath

    writeTempFiles(playbook, config, inventory, playbookPath, configPath, inventoryPath)

    val builder = new StringBuilder()
    val proc = Process(Seq("ansible-playbook", "-i", inventoryPath.toString, playbookPath.toString), None,
      "ANSIBLE_CONFIG" -> configPath.toString)
    proc ! ProcessLogger((out) => builder.append(out).append("\n"))
    println(builder.toString())
  }

  private def writeTempFiles(playbook: Playbook, config: Config, inventory: Inventory, playbookPath: Path,
                             configPath: Path, inventoryPath: Path) {
    ConfigService.write(config, configPath)
    InventoryService.write(inventory, inventoryPath)
    writePlaybook(playbook, playbookPath)
  }

  private def writePlaybook(playbook: Playbook, path: Path) {
    val options = new DumperOptions()
    options.setExplicitStart(true)
    options.setDefaultFlowStyle(FlowStyle.BLOCK)
    val representer = new PlaybookRepresenter

    val yaml = new Yaml(representer, options)
    yaml.setBeanAccess(BeanAccess.FIELD)
    val writer = new FileWriter(path.toFile)
    try {
      yaml.dump(playbook, writer)
    } finally {
      writer.close()
    }
  }
}
