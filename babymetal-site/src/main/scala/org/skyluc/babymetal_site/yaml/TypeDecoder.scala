package org.skyluc.babymetal_site.yaml

import org.skyluc.babymetal_site.yaml.YamlKeys.*
import org.skyluc.fan_resources.data.Datum
import org.skyluc.fan_resources.data.Id
import org.skyluc.fan_resources.yaml.FrDecoders
import org.skyluc.yaml.YamlDecoder

object BabymetalSiteDecoders extends FrDecoders {
  override def item: Map[String, YamlDecoder[? <: Datum[?], FrDecoders]] = super.item +
    ((CATEGORIESPAGE, CategoriesPageDecoder)) +
    ((CHRONOLOGYPAGE, ChronologyPageDecoder)) +
    ((CONTENTPAGE, ContentPageDecoder))

  override def id: Map[String, YamlDecoder[? <: Id[?], FrDecoders]] = super.id +
    ((PAGE, PageIdDecoder))
}
