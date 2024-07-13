package live.ixnoah.housetils.commands.core

import live.ixnoah.housetils.Housetils
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender


class ConfigCommand : CommandBase() {
    override fun getCommandName(): String {
        return "housetils"
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        Housetils.openConfigGui()
    }

    override fun getCommandAliases(): List<String> {
        return mutableListOf("ht")
    }

    override fun getCommandUsage(sender: ICommandSender): String { return "/" + this.commandName }
    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean { return true }
}