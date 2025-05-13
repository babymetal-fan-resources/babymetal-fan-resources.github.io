package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.data.*
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.{Processor as _, *}
import org.skyluc.fan_resources.html as fr

class ElementCompiledDataGenerator(generator: fr.CompiledDataGenerator) extends fr.ElementCompiledDataGenerator {

  protected val processor: ElementCompiledDataGeneratorProcessor = new ElementCompiledDataGeneratorProcessor(generator)

  val missingCompiledData: fr.ElementCompiledData = fr.ElementCompiledData(
    Common.SPACE,
    Common.MISSING,
    None,
    None,
    None,
    None,
    Date(2000, 1, 1),
    None,
    Site.MISSING_IMAGE,
    Nil,
    Site.MISSING_URL,
  )
}

class ElementCompiledDataGeneratorProcessor(generator: fr.CompiledDataGenerator)
    extends fr.ElementCompiledDataGeneratorProcessor(generator)
    with Processor[fr.ElementCompiledData] {

  override def processChronologyPage(chronologyPage: ChronologyPage): fr.ElementCompiledData = ???
}
