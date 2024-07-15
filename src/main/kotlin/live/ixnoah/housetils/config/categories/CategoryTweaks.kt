package live.ixnoah.housetils.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class CategoryTweaks {
    @Expose @JvmField
    @ConfigEditorText @ConfigOption(
        name = "Asterisk",
        desc = "Modify the asterisk prefix from messages sent by houses"
    )
    var asteriskBehaviour = "&7* "


}