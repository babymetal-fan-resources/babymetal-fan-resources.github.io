package org.skyluc.babymetal_site.html.compileddata

import org.skyluc.babymetal_site.data.ProcessorMultimedia
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html as fr

import fr.compileddata.MultimediaCompiledData

class MultimediaCompiledDataProcessor(generator: fr.compileddata.CompiledDataGenerator)
    extends fr.compileddata.MultimediaCompiledDataProcessor(generator)
    with ProcessorMultimedia[fr.compileddata.MultimediaCompiledData] {

  override def missingMultimediaCompiledData: MultimediaCompiledData =
    MultimediaCompiledDataProcessor.missingMultimediaCompiledData
}

object MultimediaCompiledDataProcessor {

  val missingMultimediaCompiledData: MultimediaCompiledData = MultimediaCompiledData(
    Common.MISSING,
    Common.MISSING,
    Path("asset", "image", "event", "siteimages", "kitsune_cover.png").toAbsoluteString(),
    Common.MISSING,
    Common.MISSING_URL,
    Common.MISSING_DATE,
    Common.MISSING,
    true,
    None,
    Nil,
    fr.compileddata.Overlay(Common.MISSING_URL, Common.MISSING, false),
  )
}
