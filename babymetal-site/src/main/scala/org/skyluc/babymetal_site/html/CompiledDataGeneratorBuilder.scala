package org.skyluc.babymetal_site.html

import org.skyluc.fan_resources.html as fr
import org.skyluc.fan_resources.data.Data

object CompiledDataGeneratorBuilder {
  def generator(data: Data): fr.CompiledDataGenerator = {
    new fr.CompiledDataGenerator(
      data,
      UrlResolver,
      elementGeneratorBuilder,
      multimediaGeneratorBuilder,
      localImageCopyGeneratorBuilder,
    )
  }

  def elementGeneratorBuilder(generator: fr.CompiledDataGenerator): fr.ElementCompiledDataGenerator = {
    new ElementCompiledDataGenerator(generator)
  }

  def multimediaGeneratorBuilder(generator: fr.CompiledDataGenerator): fr.MultiMediaCompiledDataGenerator = {
    new MultiMediaCompiledDataGenerator(generator)
  }

  def localImageCopyGeneratorBuilder(generator: fr.CompiledDataGenerator): fr.LocalImageCopyCompiledDataGenerator = {
    LocalImageCopyCompiledDataGenerator
  }
}
