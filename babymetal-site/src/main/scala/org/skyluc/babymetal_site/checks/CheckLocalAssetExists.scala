package org.skyluc.babymetal_site.checks

import org.skyluc.babymetal_site.data.ChronologyPage
import org.skyluc.babymetal_site.data.Processor
import org.skyluc.fan_resources.checks as fr
import org.skyluc.fan_resources.checks.CheckLocalAssetExists
import org.skyluc.fan_resources.data.Path

import fr.CheckError

class CheckLocalAssetExists(baseAssetImagePath: Path)
    extends fr.CheckLocalAssetExists(baseAssetImagePath)
    with Processor[Seq[CheckError]] {

  override def processChronologyPage(chronologyPage: ChronologyPage): Seq[CheckError] = Nil
}
