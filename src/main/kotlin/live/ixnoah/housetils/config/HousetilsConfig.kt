package com.github.noahthenerd.housetils.config
import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Category
import com.github.noahthenerd.housetils.config.categories.CategoryData
import com.github.noahthenerd.housetils.config.categories.CategoryTweaks
import io.github.notenoughupdates.moulconfig.Config as MoulConfig

class HousetilsConfig: MoulConfig() {
    override fun getTitle(): String {
        return "Housetils Config"
    }

    @Expose @JvmField
    @Category(name = "Tweaks", desc = "Minor general changes or fixes for your Housing experience")
    val tweaks = CategoryTweaks()

    @Expose @JvmField
    @Category(name = "Stats & Data", desc = "Changes related to stats & data management")
    val data = CategoryData()
}