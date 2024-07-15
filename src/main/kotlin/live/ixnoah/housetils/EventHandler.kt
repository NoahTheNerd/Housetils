package live.ixnoah.housetils

import live.ixnoah.housetils.utils.ChatUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class EventHandler {
    private val joinLeaveRegex = Regex("^(\\[(.*?)] )[A-z0-9_]{1,16} (entered|left) the world\\.$")
    private val joinLobbyRegex = Regex("^( >>> )?\\[MVP\\+{1,2}] [A-z0-9_]{1,16} joined the lobby!( <<<)?$")

    @SubscribeEvent
    fun onChat( event: ClientChatReceivedEvent ) {
        if (event.message.formattedText.startsWith("§r§7* ")) {
            val prefix = Housetils.config.tweaks.asteriskBehaviour
            val message = event.message.formattedText.drop(6)

            event.setCanceled(true)

            ChatUtils.chat(prefix + message, true)
        }

        val unformattedMessage = event.message.unformattedText

        // Basic filters
        if (Housetils.config.filters.hideLobbyJoinMessages && joinLobbyRegex.matches(unformattedMessage))
            return event.setCanceled(true)

        if (Housetils.config.filters.hideJoinMessages && joinLeaveRegex.matches(unformattedMessage))
            return event.setCanceled(true)
    }


    private val statKeyRegex = Regex("§r {2}§r§e(.*): §r§b(.*)§r")
    private val statTitleRegex = Regex("§r§a(.*)('s)? Stats:§r")

    private var targetStats = mutableMapOf<String, String>()
    private var trackingSince : Long = 0

    private val maxDurationMs = 300L

    @SubscribeEvent
    fun sortStats( event: ClientChatReceivedEvent ) {
        val trackingId = System.currentTimeMillis()

        if (event.message.formattedText.matches(statTitleRegex)) {
            targetStats = mutableMapOf()
            trackingSince = trackingId

            Thread {
                Thread.sleep(maxDurationMs + 5)

                if (Housetils.config.data.sortStatsAlphabetically)
                    targetStats = targetStats.toSortedMap()


                targetStats.forEach { (key, value) ->
                    var cuteKey = key

                    val isTemp = Housetils.config.data.tempStatPrefix.isNotEmpty()
                                 && key.startsWith(Housetils.config.data.tempStatPrefix)

                    if (isTemp && Housetils.config.data.tempStatBehaviour == 1)
                        cuteKey = "§7$cuteKey"

                    Housetils.config.data.sortStatsSplitter.split(" ").forEach { splitter ->
                        if (splitter.isNotEmpty())
                            cuteKey = cuteKey.replace(splitter, "§7$splitter")
                    }

                    if (!isTemp || Housetils.config.data.tempStatBehaviour != 2)
                        ChatUtils.chat("§r §r §e$cuteKey: §r§b$value§r")
                }
                ChatUtils.chat("§aTotal Stats: ${targetStats.size}")
            }.start()

            return
        }

        if (trackingSince == 0L) return

        // If it's been longer than 300ms since the initial start of this sorting, don't continue.
        // If it takes longer than this for some reason, you probably have a bigger problem than this tool not working.
        if (trackingSince - trackingId < maxDurationMs
            && event.message.formattedText.matches(statKeyRegex)) {
            val segments = event.message.formattedText
                .split(": §r§b")
                .map { kv -> ChatUtils.removeColor(kv).trim() }

            targetStats[segments[0]] = segments[1]
            event.setCanceled(true)
        } else {
            trackingSince = 0L
        }
    }
}