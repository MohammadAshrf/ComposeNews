package com.example.composenews

import com.example.composenews.api.model.Category

object Constants {
    const val apiKey="033795044a8a4af09d41c3164d0c736f"

    val categories = listOf(
        Category(
            "sports", R.drawable.ball,
            R.string.sports, R.color.red
        ),
        Category(
            "technology", R.drawable.politics,
            R.string.technology, R.color.blue
        ),
        Category(
            "health", R.drawable.health,
            R.string.health, R.color.pink
        ),
        Category(
            "business", R.drawable.bussines,
            R.string.business, R.color.brown_orange
        ),
        Category(
            "general", R.drawable.environment,
            R.string.general, R.color.baby_blue
        ),
        Category(
            "science", R.drawable.science,
            R.string.science, R.color.yellow
        ),
    )
}