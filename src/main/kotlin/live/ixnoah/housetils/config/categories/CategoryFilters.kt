package live.ixnoah.housetils.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class CategoryFilters {
    @Expose @JvmField
    @ConfigEditorBoolean @ConfigOption(
        name = "Hide House Join Messages",
        desc = "Hide the default join and leave messages sent in houses"
    )
    var hideJoinMessages = false


    @JvmField @Expose
    @ConfigEditorBoolean @ConfigOption(
        name = "Hide Lobby Join Messages",
        desc = "Hide the lobby join messages from MVP+ and MVP++ players"
    )
    var hideLobbyJoinMessages = false
}