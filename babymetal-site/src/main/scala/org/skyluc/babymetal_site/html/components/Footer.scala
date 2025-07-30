package org.skyluc.babymetal_site.html.components

import org.skyluc.fan_resources.html.Url
import org.skyluc.html.*

import Html.*

object Footer {

  // classes
  val CLASS_FOOTER_CONTENT = "footer-content"
  val CLASS_FOOTER_BOTTOM_RIGHT = "footer-bottom-right"
  val CLASS_FOOTER_BOTTOM_RIGHT_TEXT_TOP = "footer-bottom-right-text-top"
  val CLASS_FOOTER_BOTTOM_RIGHT_TEXT_BOTTOM = "footer-bottom-right-text-bottom"
  val CLASS_FOOTER_BOTTOM_RIGHT_IMAGE = "footer-bottom-right-image"
  val CLASS_FOOTER_BOTTOM_LEFT = "footer-bottom-left"

  // text
  val TEXT_OPPOSITE_LINK = "π"
  val TEXT_FOOTER_1 = "This website is not associated with the group BABYMETAL or their production team."
  val TEXT_FOOTER_2 =
    "© Original content, website structure: SkyLuc. Lyrics, band resources, external resources: their respective owners."
  val TEXT_FOOTER_3 =
    "We aim to provide information as accurate as possible. If you notice a problem, please contact us."
  val TEXT_FOOTER_4 = "Questions and requests"
  val TEXT_FOOTER_5 = "Provided<br>by the"
  val TEXT_FOOTER_6 = "Fan<br>Resources<br>Network"

  // URLs
  // TODO: use computed values ?
  val ABOUT_PATH = "/about.html#questions"

  def generate(oppositeUrl: Option[Url]): Seq[BodyElement[?]] = {
    oppositeUrl.map { ou =>
      a()
        .withHref(ou.toString())
        .withClass(CLASS_FOOTER_BOTTOM_LEFT)
        .appendElements(
          text(TEXT_OPPOSITE_LINK)
        )
    }.toList :::
      List(
        div()
          .withClass(CLASS_FOOTER_CONTENT)
          .appendElements(
            text(TEXT_FOOTER_1)
          ),
        div()
          .withClass(CLASS_FOOTER_CONTENT)
          .appendElements(
            text(TEXT_FOOTER_2)
          ),
        div()
          .withClass(CLASS_FOOTER_CONTENT)
          .appendElements(
            text(TEXT_FOOTER_3),
            a()
              .withHref(ABOUT_PATH)
              .appendElements(
                text(TEXT_FOOTER_4)
              ),
          ),
        a()
          .withClass(CLASS_FOOTER_BOTTOM_RIGHT)
          .withHref("https://fan-resources.net")
          .appendElements(
            div().withClass(CLASS_FOOTER_BOTTOM_RIGHT_TEXT_TOP).appendElements(text(TEXT_FOOTER_5)),
            div().withClass(CLASS_FOOTER_BOTTOM_RIGHT_TEXT_BOTTOM).appendElements(text(TEXT_FOOTER_6)),
          ),
      )
  }
}
