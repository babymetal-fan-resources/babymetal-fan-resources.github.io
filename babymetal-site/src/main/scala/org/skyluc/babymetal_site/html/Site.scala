package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.Main
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.ImageCompiledData
import org.skyluc.fan_resources.html.Url

object Site {

  val MISSING_URL = Url("/")
  val MISSING_IMAGE_URL = Url(
    Main.BASE_IMAGE_ASSET_PATH.resolve(Path("site", "manekineko-200px.png")) // TODO: bmfr page
  )
  val MISSING_IMAGE = ImageCompiledData(MISSING_IMAGE_URL, Common.MISSING, None)

  val DEFAULT_COVER_IMAGE = ImageCompiledData(
    Url(Main.BASE_IMAGE_ASSET_PATH.resolve(Path("manekineko-512px.png"))), // TODO: bmfr image
    "BABYMETAL fan resources logo",
    Some(Url("/")), // TODO: extract ?
  )
}
