package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.data.*
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.{Processor as _, *}
import org.skyluc.fan_resources.html as fr
import org.skyluc.fan_resources.html.Url

object UrlResolver extends Processor[Url] with fr.UrlResolver {

  def resolve(datum: Datum[?]): Url = {
    datum.process(this)
  }

  override def processAlbumMarker(albumMarker: AlbumMarker): Url = ???

  override def processBaseMarker(baseMarker: BaseMarker): Url = ???

  override def processGroup(group: Group): Url = ???

  override def processMediaMarker(mediaMarker: MediaMarker): Url = ???

  override def processMultiMediaMarker(multiMediaMarker: MultiMediaMarker): Url = ???

  override def processShowMarker(showMarker: ShowMarker): Url = ???

  override def processSongMarker(songMarker: SongMarker): Url = ???

  override def processChronologyPage(chronologyPage: ChronologyPage): Url = ???

  override def processAlbum(album: Album): Url = generateBasic(album)

  override def processLocalImage(localImage: LocalImage): Url = ???

  override def processMediaAudio(mediaAudio: MediaAudio): Url = generateBasic(mediaAudio)

  override def processMediaWritten(mediaWritten: MediaWritten): Url = generateBasic(mediaWritten)

  override def processPostX(postX: PostX): Url = ???

  override def processPostXImage(postXImage: PostXImage): Url = ???

  override def processPostXVideo(postXVideo: PostXVideo): Url = ???

  override def processShow(show: Show): Url = generateBasic(show)

  override def processSong(song: Song): Url = generateBasic(song)

  override def processTour(tour: Tour): Url = generateBasic(tour)

  override def processTourMarker(tourMarker: TourMarker): Url = ???

  override def processYouTubeShort(youtubeShort: YouTubeShort): Url = ???

  override def processYouTubeVideo(youtubeVideo: YouTubeVideo): Url = ???

  override def processZaiko(zaiko: Zaiko): Url = ???

  private def generateBasic(datum: Datum[?]): Url =
    Url(datum.id.path.withExtension(Common.HTML_EXTENSION))
}
