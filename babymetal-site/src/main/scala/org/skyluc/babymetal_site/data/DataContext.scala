package org.skyluc.babymetal_site.data

import org.skyluc.babymetal_site.yaml.YamlKeys.*
import org.skyluc.fan_resources.data as fr
import org.skyluc.fan_resources.html.compileddata.ElementInfo

import fr.AttributeDefinition

trait BabymetalDataContext extends fr.DataContext {

  override def attributeDefinitions: fr.AttributesDefinitionContext = BabymetalAttributesDefinitionContext
}

object BabymetalAttributesDefinitionContext extends fr.DefaultAttributesDefinitionContext {
  val ATTRIBUTE_DEFINITION_WIKI =
    AttributeDefinition(
      WIKI,
      "BABYMETAL Wiki",
      true,
      ElementInfo.INFO_LEVEL_ALL,
      false,
    )

  val ATTRIBUTE_DEFINITION_CHOREOGRAPHER =
    AttributeDefinition(
      CHOREOGRAPHER,
      "choreographer",
      false,
      ElementInfo.INFO_LEVEL_ALL,
      true,
    )

  override def albumAttributeDefinitions: Seq[AttributeDefinition] = super.albumAttributeDefinitions ++ Seq(
    ATTRIBUTE_DEFINITION_WIKI
  )

  override def songAttributeDefinitions: Seq[AttributeDefinition] = super.songAttributeDefinitions ++ Seq(
    ATTRIBUTE_DEFINITION_CHOREOGRAPHER,
    ATTRIBUTE_DEFINITION_WIKI,
  )
}
