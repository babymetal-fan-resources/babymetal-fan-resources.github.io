package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Album
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

class AlbumPage(
    album: ElementCompiledData,
    songsByYears: Seq[ChronologyYear],
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  import AlbumPage._

  override def elementContent(): Seq[BodyElement[?]] = {
    val largeDetails =
      LargeDetails.generate(album)

    val songsSection: Seq[BodyElement[?]] = Seq(
      SectionHeader.generate(SECTION_SONGS),
      ChronologySection.generate(songsByYears, true, false),
    )

    val multiMediaMainSections = MultiMediaCard.generateMainSections(multimediaBlock, Album.FROM_KEY)

    val additionalSection = MultiMediaCard.generateAdditionalSection(multimediaBlock, Album.FROM_KEY)

    Seq(
      largeDetails
    ) ++ songsSection
      ++ multiMediaMainSections
      ++ additionalSection

  }
}

class AlbumExtraPage(
    album: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val mediaSection =
      MultiMediaCard.generateExtraMediaSection(
        multimediaBlock,
        Album.FROM_KEY,
      )

    Seq(
      MainTitle.generate(
        LineCard.generate(album)
      )
    )
      ++ mediaSection
  }
}

object AlbumPage {

  val SECTION_SONGS = "Songs"

  def pagesFor(album: Album, generator: CompiledDataGenerator): Seq[SitePage] = {

    val compiledData = generator.getElement(album)

    val multimediaBlock = generator.getMultiMediaBlock(album)

    val extraPath = if (multimediaBlock.extra.isEmpty) {
      None
    } else {
      Some(album.id.path.insertSecond(Common.EXTRA))
    }

    val songs = album.songs.map(generator.get)

    val ordered = songs.sortBy(_.releaseDate)

    val songByYears = ChronologySection.compiledData(
      ordered.head.releaseDate,
      ordered.last.releaseDate,
      songs,
      generator,
    )

    val mainPage = AlbumPage(
      compiledData,
      songByYears,
      multimediaBlock,
      PageDescription(
        TitleAndDescription.formattedTitle(
          Some(compiledData.designation),
          None,
          album.fullname,
          None,
          None,
          album.altname,
        ),
        TitleAndDescription.formattedDescription(
          Some(compiledData.designation),
          None,
          album.fullname,
          None,
          None,
          album.altname,
        ),
        SitePage.absoluteUrl(compiledData.cover.source),
        SitePage.canonicalUrlFor(album.id.path),
        album.id.path.withExtension(Common.HTML_EXTENSION),
        None,
        extraPath.map(SitePage.urlFor(_)),
        false,
      ),
    )

    extraPath
      .map { extraPath =>
        val extraPage = AlbumExtraPage(
          compiledData,
          multimediaBlock,
          PageDescription(
            TitleAndDescription.formattedTitle(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              album.fullname,
              None,
              None,
              album.altname,
            ),
            TitleAndDescription.formattedDescription(
              Some(compiledData.designation),
              TitleAndDescription.EXTRA,
              album.fullname,
              None,
              None,
              album.altname,
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
      .getOrElse(Seq(mainPage))

  }
}
