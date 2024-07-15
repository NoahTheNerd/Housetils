package live.ixnoah.housetils.config
import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Category
import live.ixnoah.housetils.config.categories.CategoryData
import live.ixnoah.housetils.config.categories.CategoryFilters
import live.ixnoah.housetils.config.categories.CategoryTweaks
import net.minecraft.client.Minecraft
import io.github.notenoughupdates.moulconfig.Config as MoulConfig

class HousetilsConfig: MoulConfig() {
    override fun getTitle(): String {
        return "Welcome to Housetils, " + Minecraft.getMinecraft().thePlayer.name + "!"
    }

    @Expose @JvmField
    @Category(name = "Data & Stats", desc = "Changes related to stats & data management")
    val data = CategoryData()

    @Expose @JvmField
    @Category(name = "Chat Filtering", desc = "Filter out messages and moderate your chat")
    val filters = CategoryFilters()

    @Expose @JvmField
    @Category(name = "Tweaks", desc = "General changes to tweak your Housing experience")
    val tweaks = CategoryTweaks()
}