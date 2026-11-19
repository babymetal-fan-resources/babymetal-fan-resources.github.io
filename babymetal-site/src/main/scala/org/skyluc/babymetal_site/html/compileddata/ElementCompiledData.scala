package org.skyluc.babymetal_site.html.compileddata

import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.html as fr
import org.skyluc.babymetal_site.data.ProcessorElement
import fr.compileddata.ElementCompiledData
import fr.page.element.ElementBlockPages
import org.skyluc.fan_resources.descriptor.ChoiceCategoryRepository

class ElementCompiledDataProcessor(generator: fr.compileddata.CompiledDataGenerator)(using
    categoryRepository: ChoiceCategoryRepository
) extends fr.compileddata.ElementCompiledDataProcessor(generator)
    with ProcessorElement[fr.compileddata.ElementCompiledData] {

  override def missingElementCompiledData: ElementCompiledData =
    fr.compileddata.ElementCompiledData(
      ElementBlockPages.UNDEFINED_ELEMENT_ID,
      Common.MISSING,
      Common.MISSING,
      None,
      None,
      ElementCompiledData.FixedDelayedCompiledData(MultimediaCompiledDataProcessor.missingMultimediaCompiledData),
      Common.MISSING_DATE,
      Nil,
      Nil,
      ElementCompiledData.DelayedOptionCompiledDataEmpty(),
      ElementCompiledData.DelayedSeqCompiledDataEmpty(),
      ElementCompiledData.DelayedOptionCompiledDataEmpty(),
      Nil,
    )

}
