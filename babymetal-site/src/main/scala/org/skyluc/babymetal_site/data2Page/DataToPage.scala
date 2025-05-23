package org.skyluc.neki_site.data2Page

import org.skyluc.babymetal_site.data.{ChronologyPage as dChronologyPage, *}
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.babymetal_site.html.pages.*
import org.skyluc.fan_resources.data.{Processor as _, *}
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.Page
import org.skyluc.fan_resources.html.pages.CssPage
import org.skyluc.fan_resources.html.pages.SitemapPage

class DataToPage(generator: CompiledDataGenerator) extends Processor[Seq[SitePage]] {

  def generate(rootPath: Path, datums: Seq[Datum[?]]): Seq[Page] = {

    val cssStyles = CssPage(
      rootPath.resolve("static_pieces", "css"),
      Seq(
        Path("colors.css"),
        Path("referenceunit.css"),
        Path("pagelayout.css"),
        Path("pagestyle.css"),
        Path("nav.css"),
        Path("footer.css"),
        Path("maincontent.css"),
        Path("chronologypage.css"),
        Path("component", "chronology.css"),
        Path("component", "coverimage.css"),
        Path("component", "largedetails.css"),
        Path("component", "linecard.css"),
        Path("component", "markercard.css"),
        Path("component", "mediumcard.css"),
        Path("component", "mediumdetails.css"),
        Path("component", "multimediacard.css"),
        Path("component", "overlay.css"),
      ),
      "styles.css",
    )
    val res = datums.filterNot(_.hasError).map(_.process(this)).flatten

    val allPages = res

    allPages ++ Seq(cssStyles, SitemapPage(allPages))
  }

  override def processAlbumMarker(albumMarker: AlbumMarker): Seq[SitePage] = NO_DATA

  override def processBaseMarker(baseMarker: BaseMarker): Seq[SitePage] = NO_DATA

  override def processChronologyPage(chronologyPage: dChronologyPage): Seq[SitePage] =
    ChronologyPage.pageFor(chronologyPage, generator)

  override def processGroup(group: Group): Seq[SitePage] = NO_DATA

  override def processMediaMarker(mediaMarker: MediaMarker): Seq[SitePage] = NO_DATA

  override def processMultiMediaMarker(multiMediaMarker: MultiMediaMarker): Seq[SitePage] =
    NO_DATA

  override def processShowMarker(showMarker: ShowMarker): Seq[SitePage] = NO_DATA

  override def processSongMarker(songMarker: SongMarker): Seq[SitePage] = NO_DATA

  override def processAlbum(album: Album): Seq[SitePage] =
    AlbumPage.pagesFor(album, generator)

  override def processLocalImage(localImage: LocalImage): Seq[SitePage] = NO_DATA

  override def processMediaAudio(mediaAudio: MediaAudio): Seq[SitePage] =
    NO_DATA // MediaPage.pageFor(mediaAudio, compilers)

  override def processMediaWritten(mediaWritten: MediaWritten): Seq[SitePage] =
    NO_DATA // MediaPage.pageFor(mediaWritten, compilers)

  override def processPostX(postX: PostX): Seq[SitePage] = NO_DATA

  override def processPostXImage(postXImage: PostXImage): Seq[SitePage] = NO_DATA

  override def processPostXVideo(postXVideo: PostXVideo): Seq[SitePage] = NO_DATA

  override def processShow(show: Show): Seq[SitePage] =
    ShowPage.pagesFor(show, generator)

  override def processSong(song: Song): Seq[SitePage] =
    SongPage.pagesFor(song, generator)

  override def processTour(tour: Tour): Seq[SitePage] =
    TourPage.pagesFor(tour, generator)

  override def processTourMarker(tourMarker: TourMarker): Seq[SitePage] = NO_DATA

  override def processYouTubeShort(youtubeShort: YouTubeShort): Seq[SitePage] = NO_DATA

  override def processYouTubeVideo(youtubeVideo: YouTubeVideo): Seq[SitePage] = NO_DATA

  override def processZaiko(zaiko: Zaiko): Seq[SitePage] = NO_DATA

  // ----------

  val NO_DATA: Seq[SitePage] = Seq()

}
