package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.Site
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.SectionHeader
import org.skyluc.fan_resources.html.component.SocialMediaCard
import org.skyluc.html.*

import Html.*

class AboutPage(description: PageDescription) extends SitePage(description) {

  import AboutPage._

  override def elementContent(): Seq[BodyElement[?]] = {
    List(
      SectionHeader.generate("What is this site"),
      siteSection(),
    ) :::
      kitsune()
      //  ::: List(
      //   SectionHeader.generate("Updates"),
      //   update(),
      // )
      ::: questions()
  }

  private def siteSection(): Div = {
    div()
      .withClass(CLASS_ABOUT_SECTION)
      .appendElements(
        p().appendElements(
          text("The goal of this site is to provide comprehensive data and resources about BABYMETAL's career.")
        ),
        p().appendElements(
          text("It started after creating a "),
          a().withHref("http://nebula.local:4001/chronology.html").appendElements(text("timeline image")),
          text(
            " for another band I support: NEK!. I thought it could also be useful for BABYMETAL, to see everything which happened in the group's career displayed chronological."
          ),
        ),
        p().appendElements(
          text(
            "Currently, the data is presented in a chronological list. After most of the data from the group's 15-years history is added to the site, I'll take a look at creating a more graphical version."
          )
        ),
      )
  }

  // TODO: create the bluesky account
  // private def update(): Div = {
  //   div()
  //     .withClass(CLASS_ABOUT_SECTION)
  //     .appendElements(
  //       p().appendElements(
  //         text("Notification about content updates, and other general updates, are done at "),
  //         SocialMediaCard
  //           .generate("neki-fan-resources.github.io", "https://bsky.app/profile/", "bluesky", "bluesky.svg", true),
  //         text("."),
  //       )
  //     )

  // }

  private def kitsune(): List[BodyElement[?]] = {
    List(
      div()
        .withClass(CLASS_ABOUT_KITSUNE)
        .appendElements(
          img()
            .withSrc(
              "/asset/image/site/patronus-fox-670.png"
            )
        ),
      SectionHeader.generate("What about the fox?"),
      div()
        .withClass(CLASS_ABOUT_SECTION)
        .appendElements(
          p().appendElements(
            text(
              "The fox is at the base of the BABYMETAL's lore. The Fox God selected three young spirits to present and share the BABYMETAL with the world."
            )
          ),
          p().appendElements(
            text(
              "The starting point for this specific fox, is from the animated video for \""
            ),
            a().withHref("https://youtu.be/skXlKxjlUoo?t=126").appendElements(text("Bekhauf.")),
            text(
              "\"<br>In the video, MOAMETAL gets slightly annoyed, and with the help of SU-METAL and MOMOMETAL, she summons a Kitsune Patronus."
            ),
          ),
          p().appendElements(
            text(
              "Our fox is also a Patronus, keeping a close look at the information displayed on the site."
            )
          ),
        ),
      // )
    )
  }

  private def questions(): List[BodyElement[?]] = {
    List(
      a().withName("questions"),
      SectionHeader.generate("Questions - Suggestions - Requests - Contact"),
      div()
        .withClass(CLASS_ABOUT_SECTION)
        .appendElements(
          p().appendElements(
            text("For any questions, suggestions, requests, please use "),
            a()
              .withHref("mailto:babymetal-contact@fan-resources.net")
              .appendElements(
                text("babymetal-contact@fan-resources.net")
              ),
            text(" or "),
            SocialMediaCard.generate(
              "babymetal-fan-resources.github.io/issues",
              "https://github.com/babymetal-fan-resources/",
              "GitHub",
              "github.svg",
              true,
            ),
            text("."),
          )
        ),
    )
  }

}

object AboutPage {

  // classes
  val CLASS_ABOUT_KITSUNE = "about-kitsune"
  val CLASS_ABOUT_SECTION = "about-section"

  val PAGE_PATH = Path("about")

  def pages(): Seq[SitePage] = {
    val mainPage = AboutPage(
      PageDescription(
        TitleAndDescription.formattedTitle(
          None,
          None,
          "About",
          None,
          None,
          None,
        ),
        TitleAndDescription.formattedDescription(
          None,
          None,
          "About",
          None,
          None,
          None,
        ),
        SitePage.absoluteUrl(Site.DEFAULT_COVER_IMAGE.source),
        SitePage.canonicalUrlFor(PAGE_PATH),
        PAGE_PATH.withExtension(Common.HTML_EXTENSION),
        None,
        None,
        false,
      )
    )

    Seq(mainPage)
  }
}
