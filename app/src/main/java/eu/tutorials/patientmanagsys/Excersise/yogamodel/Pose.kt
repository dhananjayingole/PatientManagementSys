package eu.tutorials.patientmanagsys.Excersise.yogamodel

data class Pose(
    val category_name: String,
    val english_name: String,
    val id: Int,
    val pose_benefits: String,
    val pose_description: String,
    val sanskrit_name: String,
    val sanskrit_name_adapted: String,
    val translation_name: String,
    val url_png: String,
    val url_svg: String,
    val url_svg_alt: String
)