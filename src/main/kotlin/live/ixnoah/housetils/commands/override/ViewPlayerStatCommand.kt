package com.github.noahthenerd.housetils.commands.override

import com.github.noahthenerd.housetils.Housetils
import com.github.noahthenerd.housetils.utils.ChatUtils
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class ViewPlayerStatCommand : CommandBase() {
    override fun getCommandName(): String {
        return "viewplayerstats"
    }

    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.size > 0 || !Housetils.configData.data.defaultPlayer)
            return ChatUtils.command("viewstats " + args[0])

        ChatUtils.command("viewstats " + sender.name)
    }

    override fun getCommandAliases(): List<String> {
        return mutableListOf("viewstats")
    }

    override fun getCommandUsage(sender: ICommandSender): String { return "/" + this.commandName }
    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean { return true }
}