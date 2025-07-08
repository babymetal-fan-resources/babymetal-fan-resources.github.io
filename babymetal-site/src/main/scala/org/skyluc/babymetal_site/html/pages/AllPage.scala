package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.data as d
import org.skyluc.babymetal_site.html.ChronologyMarkerCompiledDataGenerator
import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.Site
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.ChronologySection
import org.skyluc.fan_resources.html.component.ChronologySection.*
import org.skyluc.html.*

class AllPage(years: Seq[ChronologyYear], description: PageDescription) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    Seq(ChronologySection.generate(years, false, false))
  }

}

object AllPage {

  // val PAGE_PATH = Path("chronology") // TODO: decide what is the main page
  val PAGE_PATH = Path("index")

  val ID_CHRONOLOGY_SECTION = "chronology-section"
  val CLASS_CATEGORY_CHECK = "cat-check"
  val CLASS_CHRONOLOGY_CATERGORIES = "chronology-caterogies"
  val CLASS_CHRONOLOGY_BLOCK = "chronology-block"
  val CLASS_CHRONOLOGY_YEAR = "chronology-year"
  val CLASS_CHRONOLOGY_MONTH = "chronology-month"
  val CLASS_CHRONOLOGY_DAY = "chronology-day"
  val CLASS_CHRONOLOGY_BLOCK_LABEL = "chronology-block-label"
  val CLASS_CHRONOLOGY_BLOCK_CONTENT = "chronology-block-content"
  val CLASS_CHRONOLOGY_BLOCK_LABEL_BGLIGHT = "chronology-block-label-bglight"
  val CLASS_CHRONOLOGY_BLOCK_LABEL_BGDARK = "chronology-block-label-bgdark"

  // ----------

  def pageFor(chronologyPage: d.ChronologyPage, generator: CompiledDataGenerator): Seq[SitePage] = {

    val markers = chronologyPage.chronology.markers.map(generator.get(_))

    val byYears = ChronologySection.compiledData(
      chronologyPage.chronology.startDate,
      chronologyPage.chronology.endDate,
      markers,
      new ChronologyMarkerCompiledDataGenerator(generator),
      generator,
    )

    val mainPage =
      AllPage(
        byYears,
        PageDescription(
          TitleAndDescription.formattedTitle(
            None,
            None,
            "All",
            None,
            None,
            None,
          ),
          TitleAndDescription.formattedDescription(
            None,
            None,
            "All",
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
          true,
        ),
      )

    Seq(mainPage)
  }
}
