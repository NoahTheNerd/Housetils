package live.ixnoah.housetils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.github.notenoughupdates.moulconfig.managed.ManagedConfig
import live.ixnoah.housetils.commands.CommandManager
import live.ixnoah.housetils.config.HousetilsConfig
import live.ixnoah.housetils.events.EventChat
import live.ixnoah.housetils.events.EventWorldJoin
import live.ixnoah.housetils.utils.Version
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import java.io.File
import java.io.InputStreamReader
import java.net.URL
import java.util.Properties

@Mod(modid = "housetils", useMetadata = true)
class Housetils {
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        CommandManager()

        MinecraftForge.EVENT_BUS.register(EventChat())
        MinecraftForge.EVENT_BUS.register(EventWorldJoin())

        // update notifier
        Thread {
            try {
                val url = URL("https://api.modrinth.com/v2/project/tyQ7or40/version?featured=true")
                val apiResponse = Gson().fromJson(InputStreamReader(url.openStream()), JsonArray::class.java)

                val latestVersion = apiResponse.get(0) as JsonObject
                LATEST_VER = Version(latestVersion.get("version_number").asString)

                if (MOD_VER.lessThan(LATEST_VER)) OUTDATED = true
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }.start()
    }

    companion object {
        val MOD_VER = Version("1.1.0")
        var LATEST_VER = MOD_VER
        var OUTDATED = false

        private val managedConfig = ManagedConfig.create(File("config/housetils/config.json"), HousetilsConfig::class.java)
        val config = managedConfig.instance

        fun openConfigGui() {
            Thread { Minecraft.getMinecraft().addScheduledTask { managedConfig.openConfigGui() } }.start()
        }
    }
}
