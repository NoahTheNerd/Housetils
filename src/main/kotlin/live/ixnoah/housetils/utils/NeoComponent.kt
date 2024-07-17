package live.ixnoah.housetils.utils

import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.IChatComponent

class NeoComponent(text: String) : ChatComponentText(text) {
    private var chatStyle = ChatStyle()

    fun onClick(action: ClickEvent.Action, string : String): NeoComponent {
        chatStyle.setChatClickEvent(ClickEvent(action, string))
        setChatStyle(chatStyle)
        return this
    }
    fun onClick(action: String, string : String): NeoComponent {
        chatStyle.setChatClickEvent(ClickEvent(ClickEvent.Action.valueOf(action), string))
        setChatStyle(chatStyle)
        return this
    }

    fun onHover(action: HoverEvent.Action, string : String): NeoComponent {
        chatStyle.setChatHoverEvent(HoverEvent(action, NeoComponent(string)))
        setChatStyle(chatStyle)
        return this
    }
    fun onHover(action: String, string : String): NeoComponent {
        chatStyle.setChatHoverEvent(HoverEvent(HoverEvent.Action.valueOf(action), NeoComponent(string)))
        setChatStyle(chatStyle)
        return this
    }


}