package eu.tutorials.patientmanagsys.Excersise.yogamodel

data class YogaItem(
    val category_description: String,
    val category_name: String,
    val id: Int,
    val poses: List<Pose>
)
