package org.skyluc.babymetal_site

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

  def main(rootFolder: Path): Unit = {

    println("\n***** Running BABYMETAL Fan Resources site *****\n")

    val configuration = MainSiteConfiguration(rootFolder)

    val errors = ErrorsHolder()

    val data = errors.record("DATA LOADING", DataLoader.load(configuration.mainDataFolder, configuration))

    errors.append("DATA CHECKING", DataCheck.check(data, configuration.mainDataCheckers))

    displayErrors(errors, 10)

    if (errors.hasCriticalErrors) {
      System.exit(2)
    }

    val pages = html.ElementToPage.generate(data, configuration)

    SiteOutput.generate(pages, configuration)
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
