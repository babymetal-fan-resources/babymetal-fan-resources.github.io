package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.data.Processor
import org.skyluc.fan_resources.html as fr

import fr.MarkerCompiledData
import fr.CompiledDataGenerator
import org.skyluc.babymetal_site.data.ChronologyPage

class ChronologyMarkerCompiledDataGenerator(generator: CompiledDataGenerator)
    extends fr.ChronologyMarkerProcessor(0, generator)
    with Processor[MarkerCompiledData] {

  override def processChronologyPage(chronologyPage: ChronologyPage): MarkerCompiledData = ???
}
