package org.skyluc.babymetal_site

import org.skyluc.fan_resources as fr

import org.skyluc.fan_resources.base.Path
import org.skyluc.babymetal_site.data.ExtendedDatum
import fr.descriptor.ChoiceCategoryRepository

class MainSiteConfiguration(override val rootFolder: Path) extends fr.MainSiteConfiguration {

  override val categoryRepository: ChoiceCategoryRepository = ExtendedDatum.categoryRepository
}
