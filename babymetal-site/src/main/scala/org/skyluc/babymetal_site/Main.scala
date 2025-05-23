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

object Main {

  def main(args: Array[String]): Unit = {
    main(Path())
  }

  def main(rootPath: Path): Unit = {

    println("\n***** Running BABYMETAL Fan Resources site *****\n")

    val dataFolder = rootPath.resolve(DATA_PATH)
    val staticFolder = rootPath.resolve(STATIC_PATH)
    val staticFrFolder = rootPath.resolve("fan-resources", STATIC_PATH)
    val outputFolder = rootPath.resolve(TARGET_PATH, SITE_PATH)

    val (parserErrors, elements) = YamlReader.load(dataFolder.asFilePath(), new NodeToElement())

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
      DataCheck.check(datums, PopulateRelatedTo, CheckLocalAssetExists(rootPath.resolve(BASE_IMAGE_ASSET_PATH)), false)

    println("CHECKS ERRORS: ")
    checkErrors.foreach { e =>
      println("  " + e)
    }
    println("--------------")

    val generator = CompiledDataGeneratorBuilder.generator(checkedDatums)

    val pages = DataToPage(generator).generate(rootPath, checkedDatums)

    println(s"nb of pages: ${pages.size}")

    SiteOutput.generate(pages, Seq(staticFrFolder.asFilePath(), staticFolder.asFilePath()), outputFolder.asFilePath())

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
