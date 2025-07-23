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
import org.skyluc.html.BodyElement
import org.skyluc.fan_resources.html.component.MainIntro

class CategoriesPage(description: String, years: Seq[ChronologyYear], pageDescription: PageDescription)
    extends SitePage(pageDescription) {

  override def elementContent(): Seq[BodyElement[?]] = {
    Seq(
      MainIntro.generate(description),
      ChronologySection.generate(years, true, false),
    )
  }

}

object CategoriesPage {

  def pageFor(categoriesPage: dfr.CategoriesPage, generator: CompiledDataGenerator): Seq[SitePage] = {

    val byYears = ChronologySection.compiledDataCategories(
      categoriesPage.startDate,
      categoriesPage.endDate,
      categoriesPage.categories,
      generator,
    )

    val path = Path(categoriesPage.id.id)

    val mainPage =
      CategoriesPage(
        categoriesPage.description,
        byYears,
        PageDescription(
          TitleAndDescription.formattedTitle(
            None,
            None,
            categoriesPage.label,
            None,
            None,
            None,
          ),
          TitleAndDescription.formattedDescription(
            None,
            None,
            categoriesPage.label,
            None,
            None,
            None,
          ),
          SitePage.absoluteUrl(generator.getMultiMedia(categoriesPage.coverImage.image).image.source),
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
