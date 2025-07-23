package org.skyluc.babymetal_site.html

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

}
