package com.github.noahthenerd.housetils.commands.override

import com.github.noahthenerd.housetils.Housetils
import com.github.noahthenerd.housetils.utils.ChatUtils
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class EditPlayerStatCommand : CommandBase() {
    private val operations = mutableListOf(
        "inc", "dec", "set", "multiply", "divide"
    )

    override fun getCommandName(): String {
        return "editplayerstat"
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.size != 3 || !Housetils.configData.data.defaultPlayer)
            return ChatUtils.command("editstat " + args.joinToString(" "))

        if (operations.contains(args[1].lowercase()) && args[2].toIntOrNull() != null) {
            ChatUtils.command("editstat ${sender.name} " + args.joinToString(" "))
        } else {
            ChatUtils.command("editstat " + args.joinToString(" "))
        }
    }

    override fun getCommandAliases(): List<String> {
        return mutableListOf("editstat","editplayerstats","editstats")
    }

    override fun getCommandUsage(sender: ICommandSender): String { return "/" + this.commandName }
    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean { return true }
}