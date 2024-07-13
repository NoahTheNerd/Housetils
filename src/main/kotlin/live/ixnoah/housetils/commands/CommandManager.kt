package com.github.noahthenerd.housetils.commands

import com.github.noahthenerd.housetils.commands.core.ConfigCommand
import com.github.noahthenerd.housetils.commands.override.EditPlayerStatCommand
import com.github.noahthenerd.housetils.commands.override.ViewPlayerStatCommand
import net.minecraftforge.client.ClientCommandHandler

class CommandManager {
    private val commandHandler = ClientCommandHandler.instance

    init {
        commandHandler.registerCommand(ConfigCommand())

        commandHandler.registerCommand(EditPlayerStatCommand())
        commandHandler.registerCommand(ViewPlayerStatCommand())
    }
}