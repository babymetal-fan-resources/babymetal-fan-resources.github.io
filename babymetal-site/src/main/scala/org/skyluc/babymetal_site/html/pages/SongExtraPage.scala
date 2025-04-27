package org.skyluc.neki_site.html.pages

import org.skyluc.fan_resources.data.Song
import org.skyluc.fan_resources.html.ElementCompiledData
import org.skyluc.fan_resources.html.MultiMediaBlockCompiledData
import org.skyluc.fan_resources.html.component.LineCard
import org.skyluc.fan_resources.html.component.MainTitle
import org.skyluc.fan_resources.html.component.MultiMediaCard
import org.skyluc.html.*
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.babymetal_site.html.PageDescription

class SongExtraPage(
    song: ElementCompiledData,
    multimediaBlock: MultiMediaBlockCompiledData,
    description: PageDescription,
) extends SitePage(description) {

  override def elementContent(): Seq[BodyElement[?]] = {
    val mediaSection =
      MultiMediaCard.generateExtraMediaSection(
        multimediaBlock,
        Song.FROM_KEY,
      )

    Seq(
      MainTitle.generate(
        LineCard.generate(song)
      )
    )
      ++ mediaSection

  }
}
