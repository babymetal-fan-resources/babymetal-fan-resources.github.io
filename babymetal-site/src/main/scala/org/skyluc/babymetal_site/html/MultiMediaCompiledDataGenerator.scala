package org.skyluc.babymetal_site.html

import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.{ProcessorElement as _, *}
import org.skyluc.fan_resources.html as fr
import org.skyluc.fan_resources.html.MultiMediaCompiledData

import fr.ImageWithOverlayCompiledData

class MultiMediaCompiledDataGenerator(generator: fr.CompiledDataGenerator) extends fr.MultiMediaCompiledDataGenerator {

  val processor: MultiMediaCompiledDataGeneratorProcessor = new MultiMediaCompiledDataGeneratorProcessor(generator)

  val missingCompiledData: MultiMediaCompiledData = MultiMediaCompiledData(
    Common.SPACE,
    Common.MISSING,
    Date(2000, 1, 1),
    ImageWithOverlayCompiledData(
      Site.MISSING_IMAGE_URL,
      fr.MultiMediaCompiledDataGenerator.OVERLAY_LOCAL_IMAGE_SOURCE,
      Common.MISSING,
      fr.MultiMediaCompiledDataGenerator.OVERLAY_LOCAL_IMAGE_ALT,
      true,
    ),
    None,
    Nil,
    false,
    Site.MISSING_URL,
    "missing",
  )
}

class MultiMediaCompiledDataGeneratorProcessor(generator: fr.CompiledDataGenerator)
    extends fr.MultiMediaCompiledDataGeneratorProcessor(generator) {}
