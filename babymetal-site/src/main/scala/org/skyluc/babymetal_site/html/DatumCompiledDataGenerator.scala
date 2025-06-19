package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.data.ChronologyPage
import org.skyluc.babymetal_site.data.CategoriesPage
import org.skyluc.babymetal_site.data.ContentPage
import org.skyluc.babymetal_site.data.Processor
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.*
import org.skyluc.fan_resources.html as fr

import fr.DatumCompiledData
import fr.CompiledDataGenerator

class DatumCompiledDataGenerator(data: Data, backupPath: Path, generator: CompiledDataGenerator)
    extends fr.DatumCompiledDataGenerator(data, backupPath, generator)
    with Processor[DatumCompiledData] {

  override def missingCompiledData(id: Id[?]): DatumCompiledData = DatumCompiledData(
    id,
    Common.MISSING,
    Site.MISSING_IMAGE,
    Nil,
    Nil,
  )

  override def processCategoriesPage(categoriesPage: CategoriesPage): DatumCompiledData = {
    val attributes = Seq() // TODO: errored

    DatumCompiledData(
      categoriesPage.id,
      categoriesPage.id.id,
      Site.MISSING_IMAGE,
      attributes,
      categoriesPage.linkedTo,
    )
  }

  override def processContentPage(contentPage: ContentPage): DatumCompiledData = {
    val attributes = Seq() // TODO: errored

    DatumCompiledData(
      contentPage.id,
      contentPage.id.id,
      Site.MISSING_IMAGE,
      attributes,
      contentPage.linkedTo,
    )
  }

  override def processChronologyPage(chronologyPage: ChronologyPage): DatumCompiledData = {
    val attributes = Seq() // TODO: errored

    DatumCompiledData(
      chronologyPage.id,
      chronologyPage.id.id,
      Site.MISSING_IMAGE,
      attributes,
      chronologyPage.linkedTo,
    )
  }

}
