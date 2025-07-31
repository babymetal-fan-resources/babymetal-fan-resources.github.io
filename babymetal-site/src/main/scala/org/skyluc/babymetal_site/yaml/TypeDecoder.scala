package org.skyluc.babymetal_site.yaml

import org.skyluc.fan_resources.data.AttributeDefinition
import org.skyluc.fan_resources.html.ElementInfo
import org.skyluc.fan_resources.yaml.FrDecoders

import YamlKeys.*

object BabymetalSiteDecoders extends FrDecoders {

  val ATTRIBUTE_DEFINITION_WIKI =
    AttributeDefinition(
      WIKI,
      "BABYMETAL Wiki",
      true,
      ElementInfo.INFO_LEVEL_ALL,
    )

  override def albumAttributeDefinitions: Seq[AttributeDefinition] = Seq(
    ATTRIBUTE_DEFINITION_WIKI
  )

  override def songAttributeDefinitions: Seq[AttributeDefinition] = Seq(
    AttributeDefinition(
      CHOREOGRAPHER,
      "choreographer",
      false,
      ElementInfo.INFO_LEVEL_ALL,
    ),
    ATTRIBUTE_DEFINITION_WIKI,
  )

}
