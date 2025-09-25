package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data as dfr
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.ElementUpdateCompiledData
import org.skyluc.fan_resources.html.TextCompiledData
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.UpdateCompiledData
import org.skyluc.fan_resources.html.component.ElementUpdatesSection
import org.skyluc.fan_resources.html.component.UpdatesSection
import org.skyluc.html.*

class UpdatePage(
    updates: Seq[UpdateCompiledData],
    elementUpdates: Seq[ElementUpdateCompiledData],
    pageDescription: PageDescription,
) extends SitePage(pageDescription) {

  override def elementContent(): Seq[BodyElement[?]] = {
    ElementUpdatesSection.generate(elementUpdates)
      ++ UpdatesSection.generate(updates)
  }

}

object UpdatePage {

  def pages(updatePage: dfr.UpdatePage, generator: CompiledDataGenerator): Seq[SitePage] = {

    val path = Path(updatePage.id.id)

    val updates = TextCompiledData.toCompiledData(updatePage.id, updatePage.updates, generator)

    val elementUpdates = generator.getWithType[dfr.ElementUpdate](dfr.ElementUpdate.ID_BASE)

    val elementUpdatesCompiledData = elementUpdates.map { elementUpdate =>
      ElementUpdateCompiledData(
        generator.getElement(elementUpdate.id.elementId),
        elementUpdate.id.date,
        elementUpdate.text,
      )
    }

    val pageDescription =
      PageDescription(
        TitleAndDescription.formattedTitle(
          None,
          None,
          updatePage.label,
          None,
          None,
          None,
        ),
        TitleAndDescription.formattedDescription(
          None,
          None,
          updatePage.label,
          None,
          None,
          None,
        ),
        SitePage.absoluteUrl(generator.getMultiMedia(updatePage.coverImage).image.source),
        SitePage.canonicalUrlFor(path),
        path.withExtension(Common.HTML_EXTENSION),
        None,
        None,
        updatePage.id.dark,
      )

    Seq(UpdatePage(updates, elementUpdatesCompiledData, pageDescription))
  }

}
