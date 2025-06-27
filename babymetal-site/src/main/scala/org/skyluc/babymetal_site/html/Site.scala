package org.skyluc.babymetal_site.html

import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.LocalImageCopyCompiledData
import org.skyluc.fan_resources.html.Url

object Site {

  val MISSING_URL = Url("/")
  val MISSING_IMAGE_URL = Url(
    Path("asset", "image", "site", "patronus-fox-670.png")
  )
  val MISSING_IMAGE = LocalImageCopyCompiledData(MISSING_IMAGE_URL, Common.MISSING)

  val DEFAULT_COVER_IMAGE = LocalImageCopyCompiledData(
    MISSING_IMAGE_URL,
    "BABYMETAL fan resources logo",
  )
}
