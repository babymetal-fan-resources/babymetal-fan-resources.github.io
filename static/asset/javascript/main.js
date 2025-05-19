var selectedCategories = []

function updateSelectedCategories() {
  var ids = Array.from(document.querySelectorAll(".cat-check")).flatMap((input) => {
    if (input.checked)
      return input.id.substring(10)
    return []
  })
  updateQueryValue("cats", ids.join("."))
}

function toggleMarkerCategory(category) {
  toggleMarkers(categories[category])
  updateSelectedCategories()
}

function toggleMarkerCategories(cats) {
  toggleMarkers(cats.flatMap((c) => categories[c]))
}

function toggleMarkers(markers) {
  markers.forEach(
    (id) => {
      var markerDiv = document.querySelectorAll("#" + id)[0]
      markerDiv.classList.toggle("marker-card-hidden")
      markerDiv.classList.toggle("marker-card-visible")
    }
  )

  refreshBlocksVisibilityAndLabelColor()
}

function refreshBlocksVisibilityAndLabelColor() {
  setChronologyBlocksVisibility(document.querySelectorAll(".chronology-day"), true)
  setChronologyBlocksVisibility(document.querySelectorAll(".chronology-month"), false)
  setChronologyBlocksVisibility(document.querySelectorAll(".chronology-year"), true)
}

function setChronologyBlocksVisibility(blocks, startDark) {
  var dark = startDark
  blocks.forEach(
    (block) => {
      if (block.querySelectorAll(".marker-card-visible").length > 0) {
        block.classList.remove("chronology-block-hidden")
        if (dark)
          block.children[0].classList.add("chronology-block-label-bgdark")
        else
          block.children[0].classList.remove("chronology-block-label-bgdark")
        dark = !dark
      } else {
        block.classList.add("chronology-block-hidden")
      }
    }
  )
}

refreshBlocksVisibilityAndLabelColor()

// display overlay for 'uId' in the query parameters
const searchParamsMain = new URLSearchParams(window.location.search)
if (searchParamsMain.has("cats")) {
  selectedCategories = searchParamsMain.get("cats").split(".")
  var cats = Object.keys(categories)
  var toHide = cats.filter((c) => !selectedCategories.includes(c))

  toHide.forEach(
    (c) => {
      var input = document.querySelectorAll("#cat-check-" + c)[0]
      input.checked = false
    }
  )

  toggleMarkerCategories(toHide)
} else {
  selectedCategories = ["all"]
}
