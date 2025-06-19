package org.skyluc.babymetal_site.html

import org.skyluc.babymetal_site.data.*
import org.skyluc.fan_resources.html as fr

import fr.MarkerCompiledData
import fr.CompiledDataGenerator

class ChronologyMarkerCompiledDataGenerator(generator: CompiledDataGenerator)
    extends fr.ChronologyMarkerProcessor(0, generator)
    with Processor[MarkerCompiledData] {

  override def processCategoriesPage(categoriesPage: CategoriesPage): MarkerCompiledData = ???

  override def processContentPage(contentPage: ContentPage): MarkerCompiledData = ???

  override def processChronologyPage(chronologyPage: ChronologyPage): MarkerCompiledData = ???
}
