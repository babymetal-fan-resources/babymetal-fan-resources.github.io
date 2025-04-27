package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.data as d
import org.skyluc.babymetal_site.html.ChronologyMarkerCompiledDataGenerator
import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.Site
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.MarkerCompiledData
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.MarkerCard
import org.skyluc.html.*

import Html.*

class ChronologyPage(
    markersCompiledData: Seq[MarkerCompiledData],
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    markersCompiledData.map { m =>
      MarkerCard.generate(m.marker)
    }
  }
}

object ChronologyPage {

  val PAGE_PATH = Path("chronology")

  // ----------

  def pageFor(chronologyPage: d.ChronologyPage, generator: CompiledDataGenerator): Seq[SitePage] = {

    val markerGenerator = new ChronologyMarkerCompiledDataGenerator(generator)

    val markersCompiledData = chronologyPage.chronology.markers.map(_.process(markerGenerator))

    val mainPage =
      ChronologyPage(
        markersCompiledData,
        PageDescription(
          TitleAndDescription.formattedTitle(
            None,
            None,
            "Chronology",
            None,
            None,
            None,
          ),
          TitleAndDescription.formattedDescription(
            None,
            None,
            "Chronology",
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
