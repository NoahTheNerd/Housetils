package live.ixnoah.housetils.config.categories

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class CategoryDemo {
    @Expose @JvmField
    @ConfigEditorBoolean @ConfigOption(name = "Option", desc = "Description")
    var boolean = false
}