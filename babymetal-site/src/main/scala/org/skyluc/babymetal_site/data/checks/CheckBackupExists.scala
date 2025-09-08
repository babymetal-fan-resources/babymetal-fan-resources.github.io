package org.skyluc.babymetal_site.data.checks

import org.skyluc.fan_resources.data.checks as fr
import org.skyluc.fan_resources.yaml.Backup

object CheckBackupExists {
  def builder(backup: Backup): fr.CheckBackupExists = new fr.CheckBackupExists(backup)
}
