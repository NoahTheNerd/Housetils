package live.ixnoah.housetils.events

import live.ixnoah.housetils.Housetils
import live.ixnoah.housetils.utils.ChatUtils
import net.minecraft.client.Minecraft
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class EventWorldJoin {
    private var hasNotifiedAboutOutdated = false
    private val updateLinkChatStyle = ChatStyle()
        .setChatClickEvent(ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/housetils"))
        .setChatHoverEvent(HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponentText("§b§nhttps://modrinth.com/mod/housetils")))

    @SubscribeEvent
    fun updateNotify( event: EntityJoinWorldEvent) {
        if (event.entity != Minecraft.getMinecraft().thePlayer) return

        if (Housetils.OUTDATED && !hasNotifiedAboutOutdated) {
            hasNotifiedAboutOutdated = true

            Thread {
                Thread.sleep(5000)
                ChatUtils.chat("\n&c [!] &fHousetils §ev${Housetils.MOD_VER} §ris outdated! §ev${Housetils.LATEST_VER}§r is now available!", true)
                ChatUtils.chat(ChatComponentText("§b §nClick to view the latest update!\n").setChatStyle(updateLinkChatStyle))
            }.start()
        }
    }
}