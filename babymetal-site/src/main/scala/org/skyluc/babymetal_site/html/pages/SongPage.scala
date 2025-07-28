package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Song
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.ElementCompiledData
import org.skyluc.fan_resources.html.MultiMediaBlockCompiledData
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.LargeDetails
import org.skyluc.fan_resources.html.component.LineCard
import org.skyluc.fan_resources.html.component.LyricsSection
import org.skyluc.fan_resources.html.component.MainTitle
import org.skyluc.fan_resources.html.component.MultiMediaCard
import org.skyluc.html.*

class SongPage(
    song: Song,
    songCompiledData: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val largeDetails =
      LargeDetails.generate(songCompiledData)

    val multiMediaMainSections = MultiMediaCard.generateMainSections(multimediaBlock, songCompiledData.uId)

    val lyricsSection = song.lyrics.map(LyricsSection.generate).getOrElse(Seq())

    val additionalSection = MultiMediaCard.generateAdditionalSection(multimediaBlock, songCompiledData.uId)

    Seq(
      largeDetails
    ) ++ multiMediaMainSections
      ++ lyricsSection
      ++ additionalSection
  }

}

class SongExtraPage(
    song: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val mediaSection =
      MultiMediaCard.generateExtraMediaSection(
        multimediaBlock,
        song.uId,
      )

    Seq(
      MainTitle.generate(
        LineCard.generate(song)
      )
    )
      ++ mediaSection

  }
}

object SongPage {

  def pagesFor(song: Song, generator: CompiledDataGenerator): Seq[SitePage] = {
    val compiledData = generator.getElement(song)
    val multimediaBlock = generator.getMultiMediaBlock(song)

    val extraPath = if (multimediaBlock.extra.isEmpty) {
      None
    } else {
      Some(song.id.path.insertSecond(Common.EXTRA))
    }

    val mainPage = SongPage(
      song,
      compiledData,
      multimediaBlock,
      PageDescription(
        TitleAndDescription.formattedTitle(
          Some(compiledData.designation),
          None,
          song.fullname,
          song.fullnameEn,
          None,
          None,
        ),
        TitleAndDescription.formattedDescription(
          Some(compiledData.designation),
          None,
          song.fullname,
          song.fullnameEn,
          None,
          None,
        ),
        SitePage.absoluteUrl(compiledData.cover.source),
        SitePage.canonicalUrlFor(song.id.path),
        song.id.path.withExtension(Common.HTML_EXTENSION),
        None,
        extraPath.map(SitePage.urlFor(_)),
        song.id.dark,
      ),
    )

    extraPath
      .map { extraPath =>
        val extraPage = SongExtraPage(
          compiledData,
          multimediaBlock,
          PageDescription(
            TitleAndDescription.formattedTitle(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              song.fullname,
              song.fullnameEn,
              None,
              None,
            ),
            TitleAndDescription.formattedDescription(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              song.fullname,
              song.fullnameEn,
              None,
              None,
            ),
            SitePage.absoluteUrl(compiledData.cover.source),
            SitePage.canonicalUrlFor(extraPath),
            extraPath.withExtension(Common.HTML_EXTENSION),
            None,
            None,
            song.id.dark,
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
