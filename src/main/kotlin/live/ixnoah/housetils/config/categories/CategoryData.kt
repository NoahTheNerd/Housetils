package live.ixnoah.housetils.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class CategoryData {
    @Expose @JvmField
    @ConfigEditorBoolean @ConfigOption(name = "Player Stat Default", desc = "When using /editplayerstat or /viewstats without a player argument, it will default to your name.")
    var defaultPlayer = false
}