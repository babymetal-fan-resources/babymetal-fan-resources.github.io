package org.skyluc.babymetal_site

case class Config(
    isLocal: Boolean,
    baseUrl: String,
)

object Config {
  private val localConfig = Config(true, "http://nebula:4101/")
  private val prodConfig = Config(false, "https://babymetal-fan-resources.github.io/")

  var cached: Config = null

  def current: Config = {
    if (cached == null) {
      if (System.getenv(PROPERTY_KEY_CONFIG_KEY) == "PROD-DISABLED") { // TODO-NOW: correct setup
        cached = prodConfig
      } else {
        cached = localConfig
      }
    }
    cached
  }

  // -------
  val PROPERTY_KEY_CONFIG_KEY = "CONFIG_KEY"
}
