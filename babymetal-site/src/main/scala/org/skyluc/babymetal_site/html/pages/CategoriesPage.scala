package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.data as d
import org.skyluc.babymetal_site.html.ChronologyMarkerCompiledDataGenerator
import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.Site
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.ChronologySection
import org.skyluc.fan_resources.html.component.ChronologySection.*
import org.skyluc.html.BodyElement

class CategoriesPage(years: Seq[ChronologyYear], description: PageDescription) extends SitePage(description) {

  var yearCounter: Boolean = false
  var monthCounter: Boolean = true
  var dayCounter: Boolean = false

  var blockId: Int = 0

  override def elementContent(): Seq[BodyElement[?]] = {
    Seq(
      ChronologySection.generate(years, true, false)
    )
  }

}

object CategoriesPage {

  def pageFor(categoriesPage: d.CategoriesPage, generator: CompiledDataGenerator): Seq[SitePage] = {

    val byYears = ChronologySection.compiledDataCategories(
      categoriesPage.startDate,
      categoriesPage.endDate,
      categoriesPage.categories,
      new ChronologyMarkerCompiledDataGenerator(generator),
      generator,
    )

    val path = Path(categoriesPage.id.id)

    val mainPage =
      MusicPage(
        byYears,
        PageDescription(
          TitleAndDescription.formattedTitle(
            None,
            None,
            categoriesPage.name,
            None,
            None,
            None,
          ),
          TitleAndDescription.formattedDescription(
            None,
            None,
            categoriesPage.name,
            None,
            None,
            None,
          ),
          SitePage.absoluteUrl(Site.DEFAULT_COVER_IMAGE.source),
          SitePage.canonicalUrlFor(path),
          path.withExtension(Common.HTML_EXTENSION),
          None,
          None,
          false,
        ),
      )

    Seq(mainPage)
  }

}
