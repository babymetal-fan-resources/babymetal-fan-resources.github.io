package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data as dfr
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.ChronologySection
import org.skyluc.fan_resources.html.component.ChronologySection.*
import org.skyluc.fan_resources.html.component.MainIntro
import org.skyluc.html.BodyElement

import dfr.CategoryDescriptor

class ContentPage(
    description: String,
    categories: Seq[CategoryDescriptor],
    years: Seq[ChronologyYear],
    withLinks: Boolean,
    withSubElements: Boolean,
    pageDescription: PageDescription,
) extends SitePage(pageDescription) {

  override def elementContent(): Seq[BodyElement[?]] = {
    Seq(
      MainIntro.generate(description),
      ChronologySection.generate(years, categories, withLinks, withSubElements),
    )
  }

}

object ContentPage {

  def pageFor(contentPage: dfr.ContentPage, generator: CompiledDataGenerator): Seq[SitePage] = {

    val byYears = ChronologySection.compiledDataIds(
      contentPage.startDate,
      contentPage.endDate,
      contentPage.content,
      generator,
    )

    val (withLinks, withSubElements) = if (contentPage.displayType == "content") {
      (true, true)
    } else if (contentPage.displayType == "choronology") {
      (false, false)
    } else {
      (false, true)
    }

    val (path, canonicalUrl, isRoot) =
      if (contentPage.id.id == "index")
        (Path("index.html"), SitePage.canonicalUrlFor(Path()), true)
      else
        (
          Path(contentPage.id.id).withExtension(Common.HTML_EXTENSION),
          SitePage.canonicalUrlFor(Path(contentPage.id.id)),
          false,
        )

    val mainPage =
      ContentPage(
        contentPage.description,
        contentPage.categories,
        byYears,
        withLinks,
        withSubElements,
        PageDescription(
          TitleAndDescription.formattedTitle(
            None,
            None,
            contentPage.label,
            None,
            None,
            None,
          ),
          TitleAndDescription.formattedDescription(
            None,
            None,
            contentPage.label,
            None,
            None,
            None,
          ),
          SitePage.absoluteUrl(generator.getMultiMedia(contentPage.coverImage).image.source),
          canonicalUrl,
          path,
          None,
          None,
          false,
          isRoot,
        ),
      )

    Seq(mainPage)
  }

}
