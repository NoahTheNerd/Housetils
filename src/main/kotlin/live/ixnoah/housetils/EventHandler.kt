package live.ixnoah.housetils

import live.ixnoah.housetils.utils.ChatUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class EventHandler {
    private val joinLeaveRegex = Regex("(\\[(.*?)] )[A-z0-9_]{1,16} (entered|left) the world\\.")

    @SubscribeEvent
    fun onChat( event: ClientChatReceivedEvent ) {
        if (event.message.formattedText.startsWith("§r§7* ")) {
            val prefix = Housetils.configData.tweaks.asteriskBehaviour
            val message = event.message.formattedText.drop(6)

            event.setCanceled(true)

            ChatUtils.chat( prefix + message, true )
        }

        if (event.message.unformattedText.matches(joinLeaveRegex)
            && Housetils.configData.tweaks.hideJoinMessages    ) {
            event.setCanceled(true)
        }

    }
}