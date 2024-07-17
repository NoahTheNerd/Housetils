package live.ixnoah.housetils.commands

import live.ixnoah.housetils.commands.core.BookmarkCommand
import live.ixnoah.housetils.commands.core.ConfigCommand
import live.ixnoah.housetils.commands.override.EditPlayerStatCommand
import live.ixnoah.housetils.commands.override.ViewPlayerStatCommand
import live.ixnoah.housetils.features.Bookmarks
import net.minecraftforge.client.ClientCommandHandler

class CommandManager {
    private val commandHandler = ClientCommandHandler.instance

    init {
        commandHandler.registerCommand(ConfigCommand())

        // Custom
        commandHandler.registerCommand(BookmarkCommand())
        Bookmarks() // fixme: if i dont do this terribleness the bookmark command causes a lag spike upon first usage. this is stupid.

        // Overrides
        commandHandler.registerCommand(EditPlayerStatCommand())
        commandHandler.registerCommand(ViewPlayerStatCommand())
    }
}