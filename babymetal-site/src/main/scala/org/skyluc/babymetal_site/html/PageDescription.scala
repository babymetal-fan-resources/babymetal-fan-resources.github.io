package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.Config
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html as fr

import fr.Url

case class PageDescription(
    title: String,
    description: String,
    image: Url,
    canonicalUrl: Url,
    outputPath: Path,
    oppositePage: Option[Url],
    extraPage: Option[Url],
    isDark: Boolean,
    isRoot: Boolean = false,
) extends fr.PageDescription {

  import PageDescription._

  val ogType: String = VALUE_TYPE
  val logo: Url = Url(Config.current.baseUrl, "head400.png")
  val locale: String = Common.VALUE_LOCALE
}

object PageDescription {
  val VALUE_TYPE = "website"
}
