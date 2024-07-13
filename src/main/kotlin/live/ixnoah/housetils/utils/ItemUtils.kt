package live.ixnoah.housetils.utils

import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.nbt.JsonToNBT
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction

object ItemUtils {
    private val netHandler = Minecraft.getMinecraft().netHandler

    fun giveItem(item: ItemStack, slot : Int?) {
        netHandler.addToSendQueue(
            C10PacketCreativeInventoryAction(
                slot ?: Minecraft.getMinecraft().thePlayer.inventory.currentItem,
                item
            )
        )
    }

    fun jsonToNBT(json: String): NBTTagCompound? {
        return JsonToNBT.getTagFromJson(json)
    }

    fun jsonToItemStack(json: String): ItemStack? {
        return ItemStack.loadItemStackFromNBT(jsonToNBT(json))
    }
}