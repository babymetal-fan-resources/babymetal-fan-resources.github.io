var selectedCategories = []

function updateSelectedCategories() {
  var ids = Array.from(document.querySelectorAll(".chronology-category-check")).flatMap((input) => {
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
  var previousDayVisible = false
  var previousMonthVisible = false

  var years = Array.from(document.querySelectorAll(".chronology-year"))

  years.reverse().forEach(
    (year) => {
      var months = Array.from(year.querySelectorAll(".chronology-month"))

      months.reverse().forEach(
        (month) => {
          if (month.querySelectorAll(".marker-card-visible").length > 0) {
            previousMonthVisible = true
            month.classList.remove("chronology-block-hidden")
            month.classList.remove("chronology-block-separator")

            var days = Array.from(month.querySelectorAll(".chronology-day"))

            days.reverse().forEach(
              (day) => {
                if (day.querySelectorAll(".marker-card-visible").length > 0) {
                  previousDayVisible = true
                  day.classList.remove("chronology-block-hidden")
                  day.classList.remove("chronology-block-separator")
                } else if (previousDayVisible) {
                  previousDayVisible = false
                  day.classList.remove("chronology-block-hidden")
                  day.classList.add("chronology-block-separator")
                } else {
                  day.classList.add("chronology-block-hidden")
                  day.classList.remove("chronology-block-separator")
                }
              }
            )
          } else if (previousMonthVisible) {
            previousMonthVisible = false
            month.classList.remove("chronology-block-hidden")
            month.classList.add("chronology-block-separator")
          } else {
            month.classList.add("chronology-block-hidden")
            month.classList.remove("chronology-block-separator")
          }
        }
      )
    }
  )
}


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
  refreshBlocksVisibilityAndLabelColor()
}
