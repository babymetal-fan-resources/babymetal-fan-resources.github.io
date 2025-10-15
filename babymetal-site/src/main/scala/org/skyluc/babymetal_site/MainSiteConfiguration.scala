package org.skyluc.babymetal_site

import org.skyluc.babymetal_site.yaml.BabymetalSiteDecoders
import org.skyluc.fan_resources as fr

import fr.data.Path

class MainSiteConfiguration(override val rootFolder: Path) extends fr.MainSiteConfiguration {

  override val decoders: fr.yaml.FrDecoders = BabymetalSiteDecoders

}
