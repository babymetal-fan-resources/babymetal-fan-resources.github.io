package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Tour
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.ElementCompiledData
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.LargeDetails
import org.skyluc.fan_resources.html.component.MediumCard
import org.skyluc.fan_resources.html.component.SectionHeader
import org.skyluc.html.BodyElement

class TourPage(
    tourCompiledData: ElementCompiledData,
    shows: Seq[ElementCompiledData],
    description: PageDescription,
) extends SitePage(description) {

  import TourPage.*

  override def elementContent(): Seq[BodyElement[?]] = {
    val largeDetails = LargeDetails.generate(tourCompiledData)

    val showsSections: Seq[BodyElement[?]] = Seq(
      SectionHeader.generate(SECTION_SHOWS),
      MediumCard.generateList(shows),
    )

    Seq(
      largeDetails
    ) ++ showsSections
  }

}

object TourPage {

  val SECTION_SHOWS = "Shows"

  def pagesFor(tour: Tour, generator: CompiledDataGenerator): Seq[SitePage] = {
    val compiledData = generator.getElement(tour)

    val showsCompiledData = tour.shows.map(generator.getElement)

    val mainPage = TourPage(
      compiledData,
      showsCompiledData,
      PageDescription(
        TitleAndDescription.formattedTitle(
          Some(compiledData.designation),
          None,
          tour.fullname,
          None,
          tour.shortname,
          None,
        ),
        TitleAndDescription.formattedDescription(
          Some(compiledData.designation),
          None,
          tour.fullname,
          None,
          tour.shortname,
          None,
        ),
        SitePage.absoluteUrl(compiledData.cover.source),
        SitePage.canonicalUrlFor(tour.id.path),
        tour.id.path.withExtension(Common.HTML_EXTENSION),
        None,
        None,
        false,
      ),
    )

    Seq(
      mainPage
    )
  }
}
