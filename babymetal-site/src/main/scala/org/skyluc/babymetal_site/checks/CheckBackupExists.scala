package org.skyluc.babymetal_site.checks

import org.skyluc.babymetal_site.data.ChronologyPage
import org.skyluc.babymetal_site.data.Processor
import org.skyluc.fan_resources.BaseError
import org.skyluc.fan_resources.checks as fr
import org.skyluc.fan_resources.yaml.Backup

class CheckBackupExists(backup: Backup) extends fr.CheckBackupExists(backup) with Processor[Option[BaseError]] {

  override def processChronologyPage(chronologyPage: ChronologyPage): Option[BaseError] = None

}

object CheckBackupExists {
  def builder(backup: Backup): CheckBackupExists = new CheckBackupExists(backup)
}
