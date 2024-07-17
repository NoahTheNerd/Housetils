package live.ixnoah.housetils.commands.core

import live.ixnoah.housetils.features.Bookmarks
import live.ixnoah.housetils.utils.ChatUtils
import live.ixnoah.housetils.utils.NeoComponent
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class BookmarkCommand : CommandBase() {
    override fun getCommandName(): String {
        return "bookmarks"
    }

    private val acceptedArgs = listOf("add","remove")
    private val nameRegex = Regex("^[0-9A-z_]{1,16}$")

    private fun formatUUID(uuid: String): String {
        return "${uuid.substring(0, 8)}-${uuid.substring(8, 12)}-${uuid.substring(12, 16)}-${uuid.substring(16, 20)}-${uuid.substring(20)}"
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            if (Bookmarks.data.bookmarks.isEmpty()) {
                ChatUtils.chat("&cYou don't have any houses bookmarked! Use /bookmarks add <player> to bookmark a house!", true)
                return
            }
            ChatUtils.chat("\n&aYour Bookmarks:", true)
            Bookmarks.data.bookmarks.forEach { entry ->
                ChatUtils.chat(
                    NeoComponent("§e  ${entry.name}")
                        .onClick("run_command", "/visit ${formatUUID(entry.uuid)}")
                        .onHover("show_text", "§a${entry.name}\n§eClick to visit!")
                )
            }
            return
        }

        if (!acceptedArgs.contains(args[0])) {
            ChatUtils.chat("&cUsage: /bookmarks <add/remove> <player>", true)
            return
        }

        if (!nameRegex.matches(args[1])) {
            ChatUtils.chat("&cPlease enter a valid player name!", true)
            return
        }

        if (args[0] == "add") Bookmarks.addBookmarkViaMojang(args[1])
        if (args[0] == "remove") Bookmarks.removeBookmark(args[1])
    }

    override fun getCommandAliases(): List<String> {
        return mutableListOf("bookmark")
    }

    override fun getCommandUsage(sender: ICommandSender): String { return "/" + this.commandName }
    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean { return true }
}