package org.skyluc.babymetal_site.checks

import org.skyluc.babymetal_site.Main
import org.skyluc.babymetal_site.data.Processor
import org.skyluc.fan_resources.checks as fr
import org.skyluc.fan_resources.checks.CheckLocalAssetExists
import fr.CheckError
import org.skyluc.babymetal_site.data.ChronologyPage

object CheckLocalAssetExists
    extends fr.CheckLocalAssetExists(Main.BASE_IMAGE_ASSET_PATH)
    with Processor[Seq[CheckError]] {

  override def processChronologyPage(chronologyPage: ChronologyPage): Seq[CheckError] = Nil
}
