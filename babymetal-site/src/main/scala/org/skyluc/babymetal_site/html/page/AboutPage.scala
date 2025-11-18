package org.skyluc.babymetal_site.html.page

import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html as fr
import org.skyluc.html.*

import Html.*

object AboutPage extends MainSitePage {

  private val CLASS_ABOUT_KITSUNE = "about-kitsune"
  private val CLASS_ABOUT_SECTION = "about-section"

  override val outputPath: Path = Path("about.html")

  override val pageConfiguration: fr.page.MainSitePageConfiguration =
    MainSitePageConfiguration(
      TitleGenerator.generateTitle("About"),
      TitleGenerator.generateTitle("About"),
      outputPath.toAbsoluteString(),
      MainSitePage.imageLogo.imageUrl,
    )

  override def mainContent(): Seq[BodyElement[?]] = {

    val sections: Seq[fr.component.ContentSection] = Seq(
      SiteSection,
      KitsuneSection,
      UpdateSection,
      SupportSection,
      QuestionsSection,
    )

    sections.flatMap(_.content())
  }

  object SiteSection extends fr.component.ContentSection {

    override val label: Option[String] = Some("What is this site?")

    override def mainContent(): BodyElement[?] = {
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
  }

  object KitsuneSection extends fr.component.ContentSection {

    override val label: Option[String] = Some("What about the fox?")

    override def mainContent(): BodyElement[?] = {
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
        )
    }

    override def customContent(): Seq[BodyElement[?]] =
      Seq(
        div()
          .withClass(CLASS_ABOUT_KITSUNE)
          .appendElements(
            img()
              .withSrc(
                "/asset/image/site/patronus-fox-670.png"
              )
          )
      )
  }

  object UpdateSection extends fr.component.ContentSection {

    override val label: Option[String] = Some("Updates")

    override def mainContent(): BodyElement[?] = {
      div()
        .withClass(CLASS_ABOUT_SECTION)
        .appendElements(
          p().appendElements(
            text("Notification about content updates, and other general updates, are done at "),
            fr.component.SocialMediaCard
              .generateBluesky("babymetal.fan-resources.net", true),
            text(" and "),
            fr.component.SocialMediaCard
              .generateX(
                "BABYMETALFanRes",
                true,
              ),
            text("."),
          )
        )
    }
  }

  object SupportSection extends fr.component.ContentSection {

    override val label: Option[String] = Some("Support")

    override def anchor(): Option[String] = Some("support")

    override def mainContent(): BodyElement[?] = {
      div()
        .withClass(CLASS_ABOUT_SECTION)
        .appendElements(
          p().appendElements(
            text(
              "While I'm doing the work because I like BABYMETAL and want to provide comprehensive information, it does take a fair bit of time and resources to create, improve, and update the site."
            )
          ),
          p().appendElements(
            text(
              "Any level of support will be greatly appreciated. Use the Fan Resources page, and don't forget to indicate in the message that you're a BABYMETAL fan."
            )
          ),
          fr.component.Kofi.generateBadge(),
        )
        .appendElements(supporters()*)
    }

    private def supporters(): Seq[BodyElement[?]] = Seq(
      ul().appendElements(
        li().appendElements(
          text("BABYMETAL supporters"),
          ul().appendElements(
            li().appendElements(text("SkyLuc"))
          ),
        ),
        li().appendElements(
          text("Fan Resources network"),
          ul().appendElements(
            li().appendElements(text("mab21")),
            li().appendElements(text("flomdo")),
            li().appendElements(text("ombe_toul")),
          ),
        ),
      )
    )
  }

  object QuestionsSection extends fr.component.ContentSection {

    override val label: Option[String] = Some("Questions - Suggestions - Requests - Contact")

    override def anchor(): Option[String] = Some("questions")

    override def mainContent(): BodyElement[?] = {
      div()
        .withClass(CLASS_ABOUT_SECTION)
        .appendElements(
          p().appendElements(
            text("For any questions, suggestions, requests, please use "),
            fr.component.SocialMediaCard.generateX(
              "BABYMETALFanRes",
              true,
            ),
            text(" or "),
            fr.component.SocialMediaCard.generateBluesky(
              "BABYMETAL.fan-resources.net",
              true,
            ),
            text("."),
          )
        )
    }

  }
}
