package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.ChronologyMarkerCompiledDataGenerator
import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.Site
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Date
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.ChronologySection
import org.skyluc.fan_resources.html.component.ChronologySection.*
import org.skyluc.html.BodyElement

class MusicPage(years: Seq[ChronologyYear], description: PageDescription) extends SitePage(description) {

  var yearCounter: Boolean = false
  var monthCounter: Boolean = true
  var dayCounter: Boolean = false

  var blockId: Int = 0

  override def elementContent(): Seq[BodyElement[?]] = {
    Seq(
      ChronologySection.generate(years, true, true)
    )
  }

}

object MusicPage {

  val PAGE_PATH = Path("music")

  def pageFor(generator: CompiledDataGenerator): Seq[SitePage] = {

    val byYears = ChronologySection.compiledDataCategories(
      Date(2010, 1, 1),
      Date(2026, 1, 31),
      Seq("song", "album"),
      new ChronologyMarkerCompiledDataGenerator(generator),
      generator,
    )

    val mainPage =
      MusicPage(
        byYears,
        PageDescription(
          TitleAndDescription.formattedTitle(
            None,
            None,
            "Music",
            None,
            None,
            None,
          ),
          TitleAndDescription.formattedDescription(
            None,
            None,
            "Music",
            None,
            None,
            None,
          ),
          SitePage.absoluteUrl(Site.DEFAULT_COVER_IMAGE.source),
          SitePage.canonicalUrlFor(PAGE_PATH),
          PAGE_PATH.withExtension(Common.HTML_EXTENSION),
          None,
          None,
          false,
        ),
      )

    Seq(mainPage)
  }

}
