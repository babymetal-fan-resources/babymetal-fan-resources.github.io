package org.skyluc.babymetal_site.data

import org.skyluc.fan_resources.data as fr

import fr.op.ImplicitDatumExpander

object Data {

  val implicitDatumsExpander = ImplicitDatumExpander()

  val creator = new fr.Data.DataBuilderProcessorCreator {
    def create(dataBuilder: fr.Data.DataBuilder): fr.Data.DataBuilderProcessor =
      DataBuilderProcessor(dataBuilder)

  }

  class DataBuilderProcessor(dataBuilder: fr.Data.DataBuilder)
      extends fr.Data.DataBuilderProcessor(dataBuilder)
      with Processor[Unit] {}

}
