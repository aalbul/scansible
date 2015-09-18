package org.nemesis.scansible.yaml

import org.nemesis.scansible.model.Playbook
import org.nemesis.scansible.model.task.Task
import org.yaml.snakeyaml.DumperOptions.FlowStyle
import org.yaml.snakeyaml.representer.Representer

/**
 * Created by aalbul on 9/17/15.
 */
class PlaybookRepresenter extends Representer { repr =>
  def representDataFunction = (data: AnyRef) => repr.representData(data)

  representers.put(classOf[Playbook], new RepresentPlaybook(representDataFunction))
  representers.put(classOf[Task], new RepresentTask(representDataFunction))
  setDefaultFlowStyle(FlowStyle.BLOCK)
}