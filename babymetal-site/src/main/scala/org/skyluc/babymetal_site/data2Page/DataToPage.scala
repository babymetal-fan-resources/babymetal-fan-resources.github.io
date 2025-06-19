package org.skyluc.babymetal_site.data2Page

import org.skyluc.babymetal_site.data.{
  CategoriesPage as dCategoriesPage,
  ContentPage as dContentPage,
  ChronologyPage as dChronologyPage,
  *,
}
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.babymetal_site.html.pages.*
import org.skyluc.fan_resources.data.{ProcessorElement as _, *}
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.Page
import org.skyluc.fan_resources.html.pages.CssPage
import org.skyluc.fan_resources.html.pages.SitemapPage

class DataToPage(generator: CompiledDataGenerator) extends ProcessorElement[Seq[SitePage]] {

  def generate(rootPath: Path, data: Data): Seq[Page] = {

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
      data.elements.values.filterNot(_.hasError).map(_.process(this)).flatten.toSeq // ++ MusicPage.pageFor(generator)

    allPages ++ Seq(cssStyles, SitemapPage(allPages))
  }
  override def processAlbum(album: Album): Seq[SitePage] =
    AlbumPage.pagesFor(album, generator)

  override def processCategoriesPage(categoriesPage: dCategoriesPage): Seq[SitePage] =
    CategoriesPage.pageFor(categoriesPage, generator)

  override def processContentPage(contentPage: dContentPage): Seq[SitePage] =
    ContentPage.pageFor(contentPage, generator)

  override def processChronologyPage(chronologyPage: dChronologyPage): Seq[SitePage] =
    AllPage.pageFor(chronologyPage, generator)

  override def processEvent(event: Event): Seq[SitePage] =
    EventPage.pagesFor(event, generator)

  override def processGroup(group: Group): Seq[SitePage] = NO_DATA

  override def processMediaAudio(mediaAudio: MediaAudio): Seq[SitePage] =
    MediaPage.pagesFor(mediaAudio, generator)

  override def processMediaWritten(mediaWritten: MediaWritten): Seq[SitePage] =
    MediaPage.pagesFor(mediaWritten, generator)

  override def processShow(show: Show): Seq[SitePage] =
    ShowPage.pagesFor(show, generator)

  override def processSong(song: Song): Seq[SitePage] =
    SongPage.pagesFor(song, generator)

  override def processTour(tour: Tour): Seq[SitePage] =
    TourPage.pagesFor(tour, generator)

  // ----------

  val NO_DATA: Seq[SitePage] = Seq()

}
