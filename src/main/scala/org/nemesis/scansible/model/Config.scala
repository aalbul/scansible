package org.nemesis.scansible.model

import Config._

/**
 * Created by aalbul on 9/15/15.
 */
object Config {
  def apply() = new Config(Map(), Map(), Map(), Map(), Map(), Map())

  implicit def booleanToString(bool: Boolean): String = bool.toString.toLowerCase.capitalize
}

class Config(val defaults: Map[String, String], val privilegeEscalation: Map[String, String],
             val paramikoConnection: Map[String, String], val sshConnection: Map[String, String],
             val accelerate: Map[String, String], val selinux: Map[String, String]) {

  def library(path: String) = customDefaults("library", path)
  def remoteTmp(path: String) = customDefaults("remote_tmp", path)
  def pattern(p: String) = customDefaults("pattern", p)
  def forks(num: Int) = customDefaults("forks", num.toString)
  def pollInterval(num: Int) = customDefaults("poll_interval", num.toString)
  def sudoUser(user: String) = customDefaults("sudo_user", user)
  def askSudoPass(ask: Boolean) = customDefaults("ask_sudo_pass", ask)
  def askPass(ask: Boolean) = customDefaults("ask_pass", ask)
  def transport(connection: AnsibleConnection) = customDefaults("transport", connection.name)
  def remotePort(port: Int) = customDefaults("remote_port", port.toString)
  def gathering(gathering: FactsGathering) = customDefaults("gathering", gathering.name)
  def rolesPath(paths: List[String]) = customDefaults("roles_path", paths.mkString(":"))
  def hostKeyChecking(check: Boolean) = customDefaults("host_key_checking", check)
  def callbackStdout(out: String) = customDefaults("callback_stdout", out)
  def callbackWhitelist(whitelist: List[String]) = customDefaults("callback_whitelist", whitelist.mkString(", "))
  def sudoExe(exe: String) = customDefaults("sudo_exe", exe)
  def sudoFlags(flags: List[String]) = customDefaults("sudo_flags", flags.mkString(" "))
  def timeout(timeout: Int) = customDefaults("timeout", timeout.toString)
  def remoteUser(user: String) = customDefaults("remote_user", user)
  def logPath(path: String) = customDefaults("log_path", path)
  def moduleName(name: String) = customDefaults("module_name", name)
  def executable(executable: String) = customDefaults("executable", executable)
  def hashBehaviour(behaviour: HashBehaviour) = customDefaults("hash_behaviour", behaviour.name)
  def privateRoleVars(vars: Boolean) = customDefaults("private_role_vars", vars)
  def jinja2Extensions(extensions: List[String]) = customDefaults("jinja2_extensions", extensions.mkString(", "))
  def privateKeyFile(path: String) = customDefaults("private_key_file", path)
  def ansibleManaged(string: String) = customDefaults("ansible_managed", string)
  def displaySkippedHosts(display: Boolean) = customDefaults("display_skipped_hosts", display)
  def errorOnUndefinedVars(error: Boolean) = customDefaults("error_on_undefined_vars", error)
  def systemWarnings(warnings: Boolean) = customDefaults("system_warnings", warnings)
  def deprecationWarnings(warnings: Boolean) = customDefaults("deprecation_warnings", warnings)
  def commandWarnings(warnings: Boolean) = customDefaults("command_warnings", warnings)
  def actionPlugins(paths: List[String]) = customDefaults("action_plugins", paths.mkString(":"))
  def callbackPlugins(paths: List[String]) = customDefaults("callback_plugins", paths.mkString(":"))
  def connectionPlugins(paths: List[String]) = customDefaults("connection_plugins", paths.mkString(":"))
  def lookupPlugins(paths: List[String]) = customDefaults("lookup_plugins", paths.mkString(":"))
  def varsPlugins(paths: List[String]) = customDefaults("vars_plugins", paths.mkString(":"))
  def filterPlugins(paths: List[String]) = customDefaults("filter_plugins", paths.mkString(":"))
  def binAnsibleCallbacks(enabled: Boolean) = customDefaults("bin_ansible_callbacks", enabled)
  def nocows(enabled: Boolean) = customDefaults("nocows", if (enabled) "1" else "0")
  def nocolor(enabled: Boolean) = customDefaults("nocolor", if (enabled) "1" else "0")
  def caFilePath(path: String) = customDefaults("ca_file_path", path)
  def httpUserAgent(agent: String) = customDefaults("http_user_agent", agent)
  def factCaching(cachingType: String) = customDefaults("fact_caching", cachingType)
  def retryFiles(enabled: Boolean) = customDefaults("retry_files_enabled", enabled)
  def retryFilesSavePath(path: String) = customDefaults("retry_files_save_path", path)

  def become(become: Boolean) = customPrivilegeEscalation("become", become)
  def becomeMethod(method: String) = customPrivilegeEscalation("become_method", method)
  def becomeUser(user: String) = customPrivilegeEscalation("become_user", user)
  def becomeAskPass(ask: Boolean) = customPrivilegeEscalation("become_ask_pass", ask)

  def recordHostKeys(record: Boolean) = customParamikoConnection("record_host_keys", record)
  def pty(pty: Boolean) = customParamikoConnection("pty", pty)

  def sshArgs(args: List[String]) = customSshConnection("ssh_args", args.mkString(" "))
  def controlPath(path: String) = customSshConnection("control_path", path)
  def pipelining(pipelining: Boolean) = customSshConnection("pipelining", pipelining)
  def scpIfSsh(enabled: Boolean) = customSshConnection("scp_if_ssh", enabled)
  def sftpBatchMode(enabled: Boolean) = customSshConnection("sftp_batch_mode", enabled)

  def acceleratePort(port: Int) = customAccelerate("accelerate_port", port.toString)
  def accelerateTimeout(timeout: Int) = customAccelerate("accelerate_timeout", timeout.toString)
  def accelerateConnectTimeout(timeout: Int) = customAccelerate("accelerate_connect_timeout", timeout.toString)
  def accelerateDaemonTimeout(timeout: Int) = customAccelerate("accelerate_daemon_timeout", timeout.toString)
  def accelerateMultiKey(accelerate: Boolean) = customAccelerate("accelerate_multi_key", accelerate)

  def specialContextFilesystems(filesystems: List[String]) = customAccelerate("special_context_filesystems", filesystems.mkString(","))

  def customDefaults(key: String, value: String) = {
    new Config(defaults + (key -> value), privilegeEscalation, paramikoConnection, sshConnection, accelerate, selinux)
  }

  def customPrivilegeEscalation(key: String, value: String) = {
    new Config(defaults, privilegeEscalation + (key -> value), paramikoConnection, sshConnection, accelerate, selinux)
  }

  def customParamikoConnection(key: String, value: String) = {
    new Config(defaults, privilegeEscalation, paramikoConnection + (key -> value), sshConnection, accelerate, selinux)
  }

  def customSshConnection(key: String, value: String) = {
    new Config(defaults, privilegeEscalation, paramikoConnection, sshConnection + (key -> value), accelerate, selinux)
  }

  def customAccelerate(key: String, value: String) = {
    new Config(defaults, privilegeEscalation, paramikoConnection, sshConnection, accelerate + (key -> value), selinux)
  }

  def customSelinux(key: String, value: String) = {
    new Config(defaults, privilegeEscalation, paramikoConnection, sshConnection, accelerate, selinux + (key -> value))
  }
}
