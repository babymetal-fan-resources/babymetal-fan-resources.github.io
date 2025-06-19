package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Event
import org.skyluc.fan_resources.data.Show
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.ElementCompiledData
import org.skyluc.fan_resources.html.MultiMediaBlockCompiledData
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.LargeDetails
import org.skyluc.fan_resources.html.component.LineCard
import org.skyluc.fan_resources.html.component.MainTitle
import org.skyluc.fan_resources.html.component.MultiMediaCard
import org.skyluc.html.BodyElement

class EventPage(
    eventCompiledData: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val largeDetails = LargeDetails.generate(eventCompiledData)

    val multiMediaMainSections = MultiMediaCard.generateMainSections(multimediaBlock, Show.FROM_KEY)

    val additionalSection = MultiMediaCard.generateAdditionalSection(multimediaBlock, Show.FROM_KEY)

    Seq(
      largeDetails
    ) ++ multiMediaMainSections
      ++ additionalSection
  }

}

class EventExtraPage(
    eventCompiledData: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val mediaSection =
      MultiMediaCard.generateExtraMediaSection(
        multimediaBlock,
        Show.FROM_KEY,
      )

    Seq(
      MainTitle.generate(
        LineCard.generate(eventCompiledData)
      )
    )
      ++ mediaSection

  }
}

object EventPage {

  def pagesFor(event: Event, generator: CompiledDataGenerator): Seq[SitePage] = {
    val compiledData = generator.getElement(event)
    val multimediaBlock = generator.getMultiMediaBlock(event)

    val extraPath = if (multimediaBlock.extra.isEmpty) {
      None
    } else {
      Some(event.id.path.insertSecond(Common.EXTRA))
    }

    val mainPage = ShowPage(
      compiledData,
      multimediaBlock,
      PageDescription(
        TitleAndDescription.formattedTitle(
          Some(compiledData.designation),
          None,
          compiledData.label,
          None,
          compiledData.shortLabel,
          None,
        ),
        TitleAndDescription.formattedDescription(
          Some(compiledData.designation),
          None,
          compiledData.label,
          None,
          compiledData.shortLabel,
          None,
        ),
        SitePage.absoluteUrl(compiledData.cover.source),
        SitePage.canonicalUrlFor(event.id.path),
        event.id.path.withExtension(Common.HTML_EXTENSION),
        None,
        extraPath.map(SitePage.urlFor(_)),
        false,
      ),
    )

    extraPath
      .map { extraPath =>
        val extraPage = ShowExtraPage(
          compiledData,
          multimediaBlock,
          PageDescription(
            TitleAndDescription.formattedTitle(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              compiledData.label,
              None,
              compiledData.shortLabel,
              None,
            ),
            TitleAndDescription.formattedDescription(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              compiledData.label,
              None,
              compiledData.shortLabel,
              None,
            ),
            SitePage.absoluteUrl(compiledData.cover.source),
            SitePage.canonicalUrlFor(extraPath),
            extraPath.withExtension(Common.HTML_EXTENSION),
            None,
            None,
            false,
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
