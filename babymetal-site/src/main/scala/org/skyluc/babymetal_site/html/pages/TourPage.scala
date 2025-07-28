package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Tour
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.ElementCompiledData
import org.skyluc.fan_resources.html.MultiMediaBlockCompiledData
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.ChronologySection
import org.skyluc.fan_resources.html.component.ChronologySection.ChronologyYear
import org.skyluc.fan_resources.html.component.LargeDetails
import org.skyluc.fan_resources.html.component.LineCard
import org.skyluc.fan_resources.html.component.MainTitle
import org.skyluc.fan_resources.html.component.MultiMediaCard
import org.skyluc.fan_resources.html.component.SectionHeader
import org.skyluc.html.BodyElement

class TourPage(
    tourCompiledData: ElementCompiledData,
    showsByYears: Seq[ChronologyYear],
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  import TourPage.*

  override def elementContent(): Seq[BodyElement[?]] = {
    val largeDetails = LargeDetails.generate(tourCompiledData)

    val multiMediaMainSections = MultiMediaCard.generateMainSections(multimediaBlock, tourCompiledData.uId)

    val showsSections: Seq[BodyElement[?]] = Seq(
      SectionHeader.generate(SECTION_SHOWS),
      ChronologySection.generate(showsByYears, Nil, true, false),
    )

    Seq(
      largeDetails
    ) ++ showsSections
      ++ multiMediaMainSections
  }

}

class TourExtraPage(
    tour: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val mediaSection =
      MultiMediaCard.generateExtraMediaSection(
        multimediaBlock,
        tour.uId,
      )

    Seq(
      MainTitle.generate(
        LineCard.generate(tour)
      )
    )
      ++ mediaSection

  }
}

object TourPage {

  val SECTION_SHOWS = "Shows"

  def pagesFor(tour: Tour, generator: CompiledDataGenerator): Seq[SitePage] = {
    val compiledData = generator.getElement(tour)
    val multimediaBlock = generator.getMultiMediaBlock(tour)

    val shows = tour.shows.map(generator.get)

    val showByYears = ChronologySection.compiledData(
      tour.firstDate,
      tour.lastDate,
      shows,
      generator,
    )

    val extraPath = if (multimediaBlock.extra.isEmpty) {
      None
    } else {
      Some(tour.id.path.insertSecond(Common.EXTRA))
    }

    val mainPage = TourPage(
      compiledData,
      showByYears,
      multimediaBlock,
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
        extraPath.map(SitePage.urlFor(_)),
        false,
      ),
    )

    extraPath
      .map { extraPath =>
        val extraPage = TourExtraPage(
          compiledData,
          multimediaBlock,
          PageDescription(
            TitleAndDescription.formattedTitle(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              tour.fullname,
              None,
              tour.shortname,
              None,
            ),
            TitleAndDescription.formattedDescription(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              tour.fullname,
              None,
              tour.shortname,
              None,
            ),
            SitePage.absoluteUrl(compiledData.cover.source),
            SitePage.canonicalUrlFor(extraPath),
            extraPath.withExtension(Common.HTML_EXTENSION),
            None,
            None,
            tour.id.dark,
          ),
        )
        Seq(extraPage, mainPage)
      }
      .getOrElse(
        Seq(
          mainPage
        )
      )
  }
}
