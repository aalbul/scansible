package org.nemesis.scansible.model.task

import org.nemesis.scansible.model.task.AptModuleBuilder.PackageState.PackageState
import org.nemesis.scansible.model.task.AptModuleBuilder.UpgradeType.UpgradeType

/**
 * Created by aalbul on 9/18/15.
 */
object AptModuleBuilder {
  def apply(name: String) = new AptModuleBuilder(Task(name, "apt", Map[String, Any]()))

  object PackageState {
    sealed trait PackageState { val name: String }
    case object Latest extends PackageState { override val name: String = "latest" }
    case object Absent extends PackageState { override val name: String = "absent" }
    case object Present extends PackageState { override val name: String = "present" }
    case object BuildDep extends PackageState { override val name: String = "build-dep" }
  }

  object UpgradeType {
    sealed trait UpgradeType { val name: String }
    case object No extends UpgradeType { override val name: String = "no" }
    case object Yes extends UpgradeType { override val name: String = "yes" }
    case object Safe extends UpgradeType { override val name: String = "safe" }
    case object Full extends UpgradeType { override val name: String = "full" }
    case object Dist extends UpgradeType { override val name: String = "dist" }
  }
}

class AptModuleBuilder private(val task: Task) extends ModuleBuilder {
  def name(name: String) = withAttr("name", name)
  def cacheValidTime(seconds: Int) = withAttr("cache_valid_time", seconds)
  def deb(path: String) = withAttr("deb", path)
  def defaultRelease(release: String) = withAttr("default_release", release)
  def dpkgOptions(options: List[String]) = withAttr("dpkg_options", options.mkString(","))
  def force(flag: Boolean) = withAttr("force", flag)
  def installRecommends(flag: Boolean) = withAttr("install_recommends", flag)
  def state(state: PackageState) = withAttr("state", state.name)
  def updateCache(flag: Boolean) = withAttr("update_cache", flag)
  def upgrade(upgradeType: UpgradeType) = withAttr("upgrade", upgradeType.name)

  private def withAttr(name: String, value: Any) = new AptModuleBuilder(task.copy(attrs = task.attrs + (name -> value)))
}