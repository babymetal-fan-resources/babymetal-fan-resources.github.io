package org.skyluc.babymetal_site

import org.skyluc.babymetal_site.data.Data
import org.skyluc.babymetal_site.data2Page.DataToPage
import org.skyluc.babymetal_site.html.CompiledDataGeneratorBuilder
import org.skyluc.babymetal_site.yaml.BabymetalSiteDecoders
import org.skyluc.fan_resources.ErrorsHolder
import org.skyluc.fan_resources.Main.displayErrors
import org.skyluc.fan_resources.data as frData
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.data.checks.DataCheck
import org.skyluc.fan_resources.html.SiteOutput

import frData.op.DataLoader

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

    val errors = ErrorsHolder()

    val (loaderErrors, data) =
      DataLoader.load(
        dataFolder,
        BabymetalSiteDecoders,
        Data.dispatcherBuilder,
        Data.defaultExpanders,
        Data.defaultPopulaters,
      )

    errors.append("LOADER ERRORS", loaderErrors)

    val checkErrors = DataCheck.check(data, (Data.defaultCheckers(staticFolder)))

    errors.append("CHECKS ERRORS", checkErrors)

    displayErrors(errors, 10)

    if (errors.hasCriticalErrors) {
      System.exit(2)
    }

    val generator = CompiledDataGeneratorBuilder.generator(data)

    val pages = DataToPage(generator).generate(rootPath, data)

    SiteOutput.generate(pages, Seq(staticFrFolder, staticFolder), outputFolder)

  }

  // -----------

  val DATA_PATH = "data"
  val STATIC_PATH = "static"
  val ASSET_PATH = "asset"
  val IMAGE_PATH = "image"
  val TARGET_PATH = "target"
  val SITE_PATH = "site"

  val BASE_IMAGE_ASSET_PATH = Path(ASSET_PATH, IMAGE_PATH)

}
