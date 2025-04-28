package org.skyluc.babymetal_site

import org.skyluc.babymetal_site.checks.CheckLocalAssetExists
import org.skyluc.babymetal_site.checks.PopulateRelatedTo
import org.skyluc.babymetal_site.element2data.ElementToData
import org.skyluc.babymetal_site.html.CompiledDataGeneratorBuilder
import org.skyluc.babymetal_site.yaml.NodeToElement
import org.skyluc.fan_resources.checks.DataCheck
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.element2data.DataTransformer
import org.skyluc.fan_resources.html.SiteOutput
import org.skyluc.fan_resources.yaml.YamlReader
import org.skyluc.neki_site.data2Page.DataToPage

import java.nio.file.Paths

object Main {

  def main(args: Array[String]): Unit = {
    val dataFolder = Paths.get(DATA_PATH)
    val staticFolder = Paths.get(STATIC_PATH)
    val outputFolder = Paths.get(TARGET_PATH, SITE_PATH)

    val (parserErrors, elements) = YamlReader.load(dataFolder, new NodeToElement())

    println("--------------")

    println("PARSER ERRORS: ")
    parserErrors.foreach { e =>
      println("  " + e)
    }
    println("--------------")

    val (toDataErrors, datums) =
      DataTransformer.toData(elements, ElementToData)

    println("TODATA ERRORS: ")
    toDataErrors.foreach { e =>
      println("  " + e)
    }
    println("--------------")

    val (checkErrors, checkedDatums) =
      DataCheck.check(datums, PopulateRelatedTo, CheckLocalAssetExists(BASE_IMAGE_ASSET_PATH))

    println("CHECKS ERRORS: ")
    checkErrors.foreach { e =>
      println("  " + e)
    }
    println("--------------")

    println(checkedDatums)

    val generator = CompiledDataGeneratorBuilder.generator(checkedDatums)

    val pages = DataToPage(generator).generate(checkedDatums)

    println(s"nb of pages: ${pages.size}")

    SiteOutput.generate(pages, Seq(staticFolder), outputFolder)

  }

  // -----------

  val DATA_PATH = "data"
  val STATIC_PATH = "static"
  val ASSET_PATH = "asset"
  val IMAGE_PATH = "image"
  val TARGET_PATH = "target"
  val SITE_PATH = "site"

  val BASE_IMAGE_ASSET_PATH = Path(STATIC_PATH, ASSET_PATH, IMAGE_PATH)

}
