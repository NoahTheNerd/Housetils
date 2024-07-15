package live.ixnoah.housetils.utils

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent

object ChatUtils {
    fun chat(message: String, replaceAmpersand: Boolean = false) {
        var endMessage : String = message
        if (replaceAmpersand) endMessage = addColor(message)

        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(endMessage))
    }

    fun chat(message: IChatComponent) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(message)
    }

    fun addColor(message: String): String {
        return message.replace(Regex("&(?=[0-9a-fk-or])"), "§")
    }

    fun removeColor(message: String): String {
        return message.replace(Regex("§[0-9a-fk-or]"), "")
    }

    fun command(command: String) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/$command")
    }
}