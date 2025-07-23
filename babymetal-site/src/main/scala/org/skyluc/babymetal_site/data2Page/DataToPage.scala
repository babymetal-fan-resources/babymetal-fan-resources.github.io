package org.skyluc.babymetal_site.data2Page

import org.skyluc.babymetal_site.data.*
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.babymetal_site.html.pages.*
import org.skyluc.fan_resources.data as dfr
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.Page
import org.skyluc.fan_resources.html.pages.CssPage
import org.skyluc.fan_resources.html.pages.SitemapPage
import dfr.Path

class DataToPage(generator: CompiledDataGenerator) extends ProcessorElement[Seq[SitePage]] {

  def generate(rootPath: Path, data: dfr.Data): Seq[Page] = {

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
    val allPages =
      data.elements.values.filterNot(_.hasError).map(_.process(this)).flatten.toSeq

    allPages ++ Seq(cssStyles, SitemapPage(allPages))
  }
  override def processAlbum(album: dfr.Album): Seq[SitePage] =
    AlbumPage.pagesFor(album, generator)

  override def processCategoriesPage(categoriesPage: dfr.CategoriesPage): Seq[SitePage] =
    CategoriesPage.pageFor(categoriesPage, generator)

  override def processContentPage(contentPage: dfr.ContentPage): Seq[SitePage] =
    ContentPage.pageFor(contentPage, generator)

  override def processEvent(event: dfr.Event): Seq[SitePage] =
    EventPage.pagesFor(event, generator)

  override def processGroup(group: dfr.Group): Seq[SitePage] = NO_DATA

  override def processMediaAudio(mediaAudio: dfr.MediaAudio): Seq[SitePage] =
    MediaPage.pagesFor(mediaAudio, generator)

  override def processMediaWritten(mediaWritten: dfr.MediaWritten): Seq[SitePage] =
    MediaPage.pagesFor(mediaWritten, generator)

  override def processMultiMediaEvent(multimediaEvent: dfr.MultiMediaEvent): Seq[SitePage] = Nil

  override def processShow(show: dfr.Show): Seq[SitePage] =
    ShowPage.pagesFor(show, generator)

  override def processSong(song: dfr.Song): Seq[SitePage] =
    SongPage.pagesFor(song, generator)

  override def processTour(tour: dfr.Tour): Seq[SitePage] =
    TourPage.pagesFor(tour, generator)

  // ----------

  val NO_DATA: Seq[SitePage] = Seq()

}
