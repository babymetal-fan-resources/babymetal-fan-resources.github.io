package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.data.ChronologyPage
import org.skyluc.babymetal_site.data.Processor
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.*
import org.skyluc.fan_resources.html as fr

import fr.DatumCompiledData
import fr.CompiledDataGenerator

class DatumCompiledDataGenerator(datums: Seq[Datum[?]], backupPath: Path, generator: CompiledDataGenerator)
    extends fr.DatumCompiledDataGenerator(datums, backupPath, generator)
    with Processor[DatumCompiledData] {

  override def missingCompiledData(id: Id[?]): DatumCompiledData = DatumCompiledData(
    id,
    Common.MISSING,
    Site.MISSING_IMAGE,
    Nil,
    Nil,
  )

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
