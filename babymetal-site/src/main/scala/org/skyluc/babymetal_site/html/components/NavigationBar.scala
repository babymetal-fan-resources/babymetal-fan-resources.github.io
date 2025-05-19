package org.skyluc.babymetal_site.html.components

import org.skyluc.html.*

import Html.*

object NavigationBar {

  // div tag
  private val NAV_DIV = "nav"

  // class
  private val CLASS_NAV_LOGO = "nav-logo"
  private val CLASS_NAV_LOGO_IMG = "nav-logo-img"
  private val CLASS_NAV_SITE_TITLE = "nav-site-title"
  private val CLASS_NAV_MAIN_ITEMS = "nav-main-items"
  private val CLASS_NAV_MAIN_ITEM = "nav-main-item"
  private val CLASS_NAV_SUPPORT_ITEMS = "nav-support-items"
  private val CLASS_NAV_SUPPORT_ITEM = "nav-support-item"
  private val CLASS_NAV_ITEM_SELECTED = "nav-item-selected"

  // text
  private val NAV_LOGO_ALT = "BMfr pratronus fox logo"
  private val NAV_TITLE_TEXT = "BABYMETAL<br>Fan<br>Resources"

  // URLs
  private val NAV_LOGO_PATH = "/asset/image/site/patronus-fox-670.png"
  private val ROOT_PATH = "/"

  def generate(): Seq[BodyElement[?]] = {
    Seq(
      div()
        .withClass(CLASS_NAV_LOGO)
        .appendElements(
          img()
            .withClass(CLASS_NAV_LOGO_IMG)
            .withSrc(NAV_LOGO_PATH)
            .withAlt(NAV_LOGO_ALT)
        ),
      div(NAV_DIV).appendElements(
        a()
          .withClass(CLASS_NAV_SITE_TITLE)
          .withHref(ROOT_PATH)
          .appendElements(
            text(NAV_TITLE_TEXT)
          )
      ),
    )
  }

}
