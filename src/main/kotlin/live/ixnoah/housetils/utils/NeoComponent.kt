package live.ixnoah.housetils.utils

import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle

class NeoComponent(text: String) : ChatComponentText(text) {
    private var chatStyle = ChatStyle()

    fun onClick(action: ClickEvent.Action, string : String): NeoComponent {
        chatStyle.setChatClickEvent(ClickEvent(action, string))
        setChatStyle(chatStyle)
        return this
    }
    fun onClick(action: String, string : String): NeoComponent {
        chatStyle.setChatClickEvent(ClickEvent(ClickEvent.Action.valueOf(action.uppercase()), string))
        setChatStyle(chatStyle)
        return this
    }

    fun onHover(action: HoverEvent.Action, string : String): NeoComponent {
        chatStyle.setChatHoverEvent(HoverEvent(action, NeoComponent(string)))
        setChatStyle(chatStyle)
        return this
    }
    fun onHover(action: String, string : String): NeoComponent {
        chatStyle.setChatHoverEvent(HoverEvent(HoverEvent.Action.valueOf(action.uppercase()), NeoComponent(string)))
        setChatStyle(chatStyle)
        return this
    }


}