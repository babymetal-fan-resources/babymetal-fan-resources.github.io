package org.skyluc.babymetal_site

import org.skyluc.fan_resources as fr
import org.skyluc.babymetal_site.data as d
import org.skyluc.babymetal_site.yaml.BabymetalSiteDecoders

import fr.data.Path

class MainSiteConfiguration(override val rootFolder: Path) extends fr.MainSiteConfiguration {

  import MainSiteConfiguration.*

  override val decoders: fr.yaml.FrDecoders = BabymetalSiteDecoders

  override def implicitDataExpander: fr.data.op.ImplicitDataExpander = ImplicitDataExpander

}

object MainSiteConfiguration {

  object ImplicitDataExpander extends fr.data.op.ImplicitDataExpander {

    override val expanderProcessor: fr.data.op.ImplicitDatumExpanderProcessor =
      d.op.ImplicitDatumExpanderProcessor()

  }

}
