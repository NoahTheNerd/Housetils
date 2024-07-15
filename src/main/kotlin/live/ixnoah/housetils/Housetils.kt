package live.ixnoah.housetils

import io.github.notenoughupdates.moulconfig.managed.ManagedConfig
import live.ixnoah.housetils.commands.CommandManager
import live.ixnoah.housetils.config.HousetilsConfig
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import java.io.File

@Mod(modid = "housetils", useMetadata = true)
class Housetils {
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        CommandManager()
        MinecraftForge.EVENT_BUS.register(EventHandler())
    }

    companion object {
        private val managedConfig = ManagedConfig.create(File("config/housetils/config.json"), HousetilsConfig::class.java)
        val config = managedConfig.instance

        fun openConfigGui() {
            Thread { Minecraft.getMinecraft().addScheduledTask { managedConfig.openConfigGui() } }.start()
        }
    }
}
