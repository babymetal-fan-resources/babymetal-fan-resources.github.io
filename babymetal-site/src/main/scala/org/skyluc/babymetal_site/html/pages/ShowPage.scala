package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Show
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.ElementCompiledData
import org.skyluc.fan_resources.html.MultiMediaBlockCompiledData
import org.skyluc.fan_resources.html.component.LargeDetails
import org.skyluc.fan_resources.html.component.MultiMediaCard
import org.skyluc.html.BodyElement
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.MainTitle
import org.skyluc.fan_resources.html.component.LineCard

class ShowPage(
    showCompiledData: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val largeDetails = LargeDetails.generate(showCompiledData)

    val multiMediaMainSections = MultiMediaCard.generateMainSections(multimediaBlock, Show.FROM_KEY)

    val additionalSection = MultiMediaCard.generateAdditionalSection(multimediaBlock, Show.FROM_KEY)

    Seq(
      largeDetails
    ) ++ multiMediaMainSections
      ++ additionalSection
  }

}

class ShowExtraPage(
    show: ElementCompiledData,
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
        LineCard.generate(show)
      )
    )
      ++ mediaSection

  }
}

object ShowPage {

  def pagesFor(show: Show, generator: CompiledDataGenerator): Seq[SitePage] = {
    val compiledData = generator.getElement(show)
    val multimediaBlock = generator.getMultiMediaBlock(show)

    val extraPath = if (multimediaBlock.extra.isEmpty) {
      None
    } else {
      Some(show.id.path.insertSecond(Common.EXTRA))
    }

    val mainPage = ShowPage(
      compiledData,
      multimediaBlock,
      PageDescription(
        TitleAndDescription.formattedTitle(
          Some(compiledData.designation),
          None,
          show.fullname,
          None,
          show.shortname,
          None,
        ),
        TitleAndDescription.formattedDescription(
          Some(compiledData.designation),
          None,
          show.fullname,
          None,
          show.shortname,
          None,
        ),
        SitePage.absoluteUrl(compiledData.cover.source),
        SitePage.canonicalUrlFor(show.id.path),
        show.id.path.withExtension(Common.HTML_EXTENSION),
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
              show.fullname,
              None,
              show.shortname,
              None,
            ),
            TitleAndDescription.formattedDescription(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              show.fullname,
              None,
              show.shortname,
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
