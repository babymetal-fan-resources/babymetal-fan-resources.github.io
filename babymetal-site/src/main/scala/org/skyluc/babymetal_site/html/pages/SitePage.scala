package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.Config
import org.skyluc.babymetal_site.html.components.NavigationBar
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html as fr
import org.skyluc.html.*

import Html.*
import fr.component.Head as HeadComponents
import fr.component.OpenGraphSection
import fr.Url
import fr.component.ExtraSection
import fr.component.MainIntro

abstract class SitePage(override val description: PageDescription) extends fr.SitePage {

  import SitePage._

  def elementContent(): Seq[BodyElement[?]]

  override def javascriptFiles(): Seq[Url] = JAVASCRIPT_FILES

  override def headContent(): Seq[HeadElement[?]] = {
    Seq(
      HeadComponents.charsetUtf8,
      HeadComponents.googleFonts(HREF_GOOGLE_FONT_NOTO),
      if (description.isRoot) {
        HeadComponents.searchEngineVerification(GOOGLE_VERIFICATION_CODE, MICROSOFT_VERIFICATION_CODE)
      } else { Nil },
      if (description.isDark) {
        HeadComponents.css(CSS_PATH, CSS_DARK_PATH)
      } else {
        HeadComponents.css(CSS_PATH)
      },
      HeadComponents.icons(HREF_ICON_512),
      OpenGraphSection.generate(description),
      HeadComponents.statistics(Config.current.isLocal, DOMAIN_NAME_BMFR),
    ).flatten
  }

  override def headerContent(): Seq[BodyElement[?]] =
    NavigationBar.generate(outputPath) // (site.navigation, outputPath)

  override def mainContent(): Seq[BodyElement[?]] = {
    val extraSection = description.extraPage.map(ExtraSection.generate(_)).getOrElse(Nil)

    val notice = MainIntro.generate(
      div()
        .withClass(CLASS_TMP_NOTICE)
        .appendElements(
          text(
            "This is the initial version of a planned bigger repository of BABYMETAL information. As additional content is added, the design and usability will be improved. Thank you for your visit."
          )
        )
    )

    Seq(notice) ++
      elementContent()
      ++ extraSection
  }

  override def footerContent(): Seq[BodyElement[?]] =
    Seq(text("Footer"))
    // Footer.generate(description.oppositePage)

}

object SitePage {

  val CLASS_TMP_NOTICE = "tmp-notice"

  val HREF_GOOGLE_FONT_NOTO = "https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@100..900&display=swap"

  val CSS_PATH = Url(Path("asset", "css", "styles.css"))
  val CSS_DARK_PATH = Url(Path("asset", "css", "dark.css"))

  val HREF_ICON_512 = Url(Path("head400.png"))
  val DOMAIN_NAME_BMFR = "babymetal.fan-resources.com"

  val GOOGLE_VERIFICATION_CODE = "DrE-ZbcyBV3lPatFCBja2O4ymKzfqFXDZjkfkTpvY_8"
  val MICROSOFT_VERIFICATION_CODE = "B6C2BBE1BBDED01F740330EB10DEAEF8"

  // javascript
  private val SRC_CONTENT_JAVASCRIPT = "/asset/javascript/content.js"
  private val SRC_FR_MAIN_JAVASCRIPT = "/asset/javascript/frmain.js"
  private val SRC_OVERLAY_JAVASCRIPT = "/asset/javascript/overlay.js"
  val JAVASCRIPT_FILES = Seq(Url(SRC_FR_MAIN_JAVASCRIPT), Url(SRC_CONTENT_JAVASCRIPT), Url(SRC_OVERLAY_JAVASCRIPT))

  val DARK_PATH = Path("dark")

  def urlFor(path: Path): Url = Url(path.withExtension(Common.HTML_EXTENSION))

  // TODO: this is quite ugly
  def absoluteUrl(url: Url): Url =
    if (url.text.startsWith("http")) {
      url
    } else {
      if (url.text.startsWith("/")) {
        Url(Config.current.baseUrl, url.text.substring(1))
      } else {
        Url(Config.current.baseUrl, url.text)
      }
    }

  def canonicalUrlFor(path: Path): Url = Url(Config.current.baseUrl, path.withExtension(Common.HTML_EXTENSION))

}
