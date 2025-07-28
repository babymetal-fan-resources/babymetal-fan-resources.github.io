package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Media
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.ElementCompiledData
import org.skyluc.fan_resources.html.MultiMediaBlockCompiledData
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.LargeDetails
import org.skyluc.fan_resources.html.component.LineCard
import org.skyluc.fan_resources.html.component.MainTitle
import org.skyluc.fan_resources.html.component.MultiMediaCard
import org.skyluc.html.BodyElement

class MediaPage(
    mediaCompiledData: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val largeDetails = LargeDetails.generate(mediaCompiledData)

    val multiMediaMainSections = MultiMediaCard.generateMainSections(multimediaBlock, mediaCompiledData.uId)

    val additionalSection = MultiMediaCard.generateAdditionalSection(multimediaBlock, mediaCompiledData.uId)

    Seq(
      largeDetails
    ) ++ multiMediaMainSections
      ++ additionalSection
  }

}

class MediaExtraPage(
    mediaCompiledData: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val mediaSection =
      MultiMediaCard.generateExtraMediaSection(
        multimediaBlock,
        mediaCompiledData.uId,
      )

    Seq(
      MainTitle.generate(
        LineCard.generate(mediaCompiledData)
      )
    )
      ++ mediaSection

  }
}

object MediaPage {

  def pagesFor(media: Media, generator: CompiledDataGenerator): Seq[SitePage] = {
    val compiledData = generator.getElement(media)
    val multimediaBlock = generator.getMultiMediaBlock(media)

    val extraPath = if (multimediaBlock.extra.isEmpty) {
      None
    } else {
      Some(media.id.path.insertSecond(Common.EXTRA))
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
        SitePage.canonicalUrlFor(media.id.path),
        media.id.path.withExtension(Common.HTML_EXTENSION),
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
