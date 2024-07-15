package live.ixnoah.housetils.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorDropdown
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class CategoryData {
    @Expose @JvmField
    @ConfigEditorBoolean @ConfigOption(
        name = "Default Player",
        desc = "When using /editstat or /viewstats with no player, it will default to you"
    )
    var defaultPlayer = false

    @Expose @JvmField
    @ConfigEditorBoolean @ConfigOption(
        name = "Alphabetical Stats",
        desc = "Sorts stats alphabetically when viewing stats"
    )
    var sortStatsAlphabetically = true

    @Expose @JvmField
    @ConfigEditorText @ConfigOption(
        name = "Categorised Stats",
        desc = "Grays out anything before the splitters (separated by space) when viewing stats"
    )
    var sortStatsSplitter = "/"

    @Expose @JvmField
    @ConfigEditorText @ConfigOption(
        name = "Temp Stat Prefix",
        desc = "Set prefixes for temporary stats (seperated by space) when viewing stats"
    )
    var tempStatPrefix = "_"

    @Expose @JvmField
    @ConfigEditorDropdown( values = ["Do nothing","Gray out","Hide from list"])
    @ConfigOption(
        name = "Temp Stat Action",
        desc = "What to do with temporary stats when viewing stats"
    )
    var tempStatBehaviour = 0
}