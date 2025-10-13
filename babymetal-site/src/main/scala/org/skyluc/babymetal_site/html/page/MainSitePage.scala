package org.skyluc.babymetal_site.html.page

import org.skyluc.babymetal_site.Config
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html as fr
import org.skyluc.html.*

import fr.component.NavigationItem
import Html.*

object TitleGenerator extends fr.page.TitleGenerator {
  override def groupName: String = "BABYMETAL"
  override def groupNameExt: String = "BABYMETAL"
}

case class MainSitePageConfiguration(
    title: String,
    description: String,
    canonicalUrl: String,
    imageUrl: String,
    isRoot: Boolean = false,
) extends fr.page.MainSitePageConfiguration {

  override val headConfiguration: fr.component.HeadConfiguration =
    MainSitePage.headConfiguration(
      title,
      description,
      canonicalUrl,
      imageUrl,
      isRoot,
    )

}

abstract class MainSitePage extends fr.page.MainSitePage {

  import MainSitePage.*

  override def headerContent(): Seq[BodyElement[?]] = fr.component.Header.generate(
    MainSitePage.imageNavBar,
    "BABYMETAL",
    mainNavItems,
    supportNavItems,
    outputPath.firstSegment(),
  )

  override def preMainContent(): Seq[BodyElement[?]] =
    Seq(
      div()
        .withClass(CLASS_TMP_NOTICE)
        .appendElements(
          text(
            "This is the initial version of a planned bigger repository of BABYMETAL information. As additional content is added, the design and usability will be improved. Thank you for your visit."
          )
        )
    )

  override def footerContent(): Seq[BodyElement[?]] = fr.component.Footer.generate("BABYMETAL")

  override def javascriptFiles(): Seq[Path] = fr.page.MainSitePage.JAVASCRIPT_FILES

}

object MainSitePage {

  def headConfiguration(
      title: String,
      description: String,
      canonicalUrl: String,
      imageUrl: String,
      isRoot: Boolean,
  ): fr.component.HeadConfiguration =
    fr.component.HeadConfiguration(
      DOMAIN_NAME,
      title,
      description,
      Config.current.baseUrl + canonicalUrl,
      Config.current.baseUrl + imageUrl,
      Config.current.baseUrl + MainSitePage.imageLogo.imageUrl,
      CSS_PATHS,
      GOOGLE_VERIFICATION_CODE,
      MICROSOFT_VERIFICATION_CODE,
      HREF_FONT_NOTO,
      isRoot,
      Config.current.isLocal,
    )

  val CLASS_TMP_NOTICE = "tmp-notice"

  val DOMAIN_NAME = "babymetal.fan-resources.net"

  val CSS_PATHS =
    Seq(Path("asset", "css", "fr-styles.css"), Path("asset", "css", "styles.css"))

  val HREF_FONT_NOTO = "https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@100..900&display=swap"

  val GOOGLE_VERIFICATION_CODE = "5DUf4g9lYSa_jzwy0JIrwsfTppM2uM5culgvgkbXj7U"
  val MICROSOFT_VERIFICATION_CODE = "B6C2BBE1BBDED01F740330EB10DEAEF8"

  val imageNavBar = fr.compileddata.MultimediaCompiledData(
    "image",
    "logo",
    Path("asset", "image", "site", "patronus-fox-670.png").toAbsoluteString(),
    "BABYMETAL Fan Resources logo",
    Common.MISSING_URL,
    Common.MISSING_DATE,
    "Designed for the site",
    true,
    None,
    Nil,
    fr.compileddata.Overlay(Common.MISSING_URL, Common.MISSING, false),
  )

  val imageLogo = fr.compileddata.MultimediaCompiledData(
    "image",
    "logo",
    Path("asset", "image", "event", "siteimages", "kitsune_cover.png").toAbsoluteString(),
    "BABYMETAL Fan Resources logo",
    Common.MISSING_URL,
    Common.MISSING_DATE,
    "Designed for the site",
    true,
    None,
    Nil,
    fr.compileddata.Overlay(Common.MISSING_URL, Common.MISSING, false),
  )

  val mainNavItems = Seq(
    NavigationItem(
      "Music",
      "/music.html",
      List("music.html", "song", "album"),
    ),
    NavigationItem(
      "Shows",
      "/shows.html",
      List("shows.html", "show", "tour"),
    ),
    NavigationItem(
      "Media",
      "/media.html",
      List("media.html", "media"),
    ),
    NavigationItem(
      "All",
      "/",
      List(),
    ),
  )

  val supportNavItems = Seq(
    NavigationItem(
      "Updates",
      "/updates.html",
      List("updates.html"),
    ),
    NavigationItem(
      "About",
      "/about.html",
      List("about.html"),
    ),
  )
}
