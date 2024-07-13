package com.github.noahthenerd.housetils.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class CategoryTweaks {
    @Expose @JvmField
    @ConfigEditorText @ConfigOption(name = "Asterisk", desc = "Modify the asterisk prefix from messages sent by houses")
    var asteriskBehaviour = "&7* "

    @Expose @JvmField
    @ConfigEditorBoolean @ConfigOption(name = "Hide Join Messages", desc = "Hide the default join and leave messages sent in houses")
    var hideJoinMessages = false
}