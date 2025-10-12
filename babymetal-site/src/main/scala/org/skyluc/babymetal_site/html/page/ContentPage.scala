package org.skyluc.babymetal_site.html.page

import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data as dfr
import org.skyluc.fan_resources.html as fr
import org.skyluc.html.*

import dfr.Path

class ContentPage(
    contentPage: fr.compileddata.ElementCompiledData,
    configuration: fr.component.ChronologySectionConfiguration,
) extends MainSitePage {

  override val outputPath: Path = contentPage.id.path.tailSegments().withExtension(Common.HTML_EXTENSION)

  override val pageConfiguration: fr.page.MainSitePageConfiguration =
    val isRoot = outputPath == ContentPage.INDEX_PATH

    MainSitePageConfiguration(
      TitleGenerator.generateTitle(contentPage.label),
      TitleGenerator.generateDescription(contentPage.label),
      if (isRoot) "/" else outputPath.toAbsoluteString(),
      contentPage.cover.imageUrl,
      isRoot,
    )

  override def mainContent(): Seq[BodyElement[?]] = {
    contentPage.description.map { d =>
      fr.component.MainIntro.generate(d)
    }.toSeq
      :+ fr.component.ChronologySection.generate(configuration)
  }

}

object ContentPage {

  def pagesFor(
      contentPage: dfr.ContentPage,
      generator: fr.compileddata.CompiledDataGenerator,
  ): Seq[fr.page.MainSitePage] = {
    val compiledData = generator.getElementCompiledData(contentPage)

    val configuration = fr.component.ChronologySectionConfiguration.createFromIds(
      contentPage.content,
      contentPage.startDate,
      contentPage.endDate,
      contentPage.categories,
      contentPage.displayType,
      generator,
    )

    Seq(
      ContentPage(compiledData, configuration)
    )

  }

  val INDEX_PATH = Path("index.html")

}
