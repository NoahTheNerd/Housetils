package live.ixnoah.housetils.features

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.managed.ManagedDataFile
import kotlinx.serialization.Serializable
import live.ixnoah.housetils.utils.ChatUtils
import live.ixnoah.housetils.utils.KotlinTypeAdapterFactory
import java.awt.print.Book
import java.io.File
import java.io.InputStreamReader
import java.net.URL

@Serializable
data class Bookmarks(
    val bookmarks: MutableSet<BookmarkEntry>,
) {
    @Serializable
    data class BookmarkEntry(
        val name: String,
        val uuid: String,
    )

    companion object {
        val DATA = ManagedDataFile.create(File("config/mymod/bookmarks.json"), Bookmarks::class.java) {
            jsonMapper {
                gsonBuilder.registerTypeAdapterFactory(KotlinTypeAdapterFactory())
            }
        }
        val data get() = DATA.instance

        fun addBookmarkViaMojang(name: String) {
            Thread {
                val apiUrl = URL("https://api.mojang.com/users/profiles/minecraft/ixNoah")
                val apiRes = Gson().fromJson(
                    InputStreamReader(apiUrl.openStream()),
                    JsonObject::class.java
                )

                // Simple error-checking
                if (apiRes.has("errorMessage")) {
                    ChatUtils.chat("&cMojang Error: ${apiRes.get("errorMessage")}", true)
                    return@Thread
                }
                if (!apiRes.has("name")) {
                    ChatUtils.chat("&cSomething went wrong connecting to the Mojang API!", true)
                    return@Thread
                }

                val name = apiRes.get("name").asString
                val uuid = apiRes.get("id").asString

                var possibleDuplicate = data.bookmarks.find { entry -> entry.uuid == uuid }
                if (possibleDuplicate != null) {
                    ChatUtils.chat("&cThis player is already bookmarked!", true)

                    data.bookmarks.remove(possibleDuplicate)
                    data.bookmarks.add(BookmarkEntry( name, uuid ))

                    DATA.saveToFile()
                    return@Thread
                }

                // If a player w a duplicate name exists, update it.
                possibleDuplicate = data.bookmarks.find { entry -> entry.name == name }
                if (possibleDuplicate != null) {
                    val dupeApiUrl = URL("https://api.mojang.com/users/profiles/minecraft/${possibleDuplicate.uuid}")
                    val dupeApiRes = Gson().fromJson(
                        InputStreamReader(dupeApiUrl.openStream()),
                        JsonObject::class.java
                    )

                    // Simple error-checking
                    if (dupeApiRes.has("errorMessage")) {
                        ChatUtils.chat("&cMojang Error: ${dupeApiRes.get("errorMessage")}", true)
                        return@Thread
                    }
                    if (!dupeApiRes.has("name")) {
                        ChatUtils.chat("&cSomething went wrong connecting to the Mojang API!", true)
                        return@Thread
                    }

                    data.bookmarks.remove(possibleDuplicate)
                    addBookmark(
                        dupeApiRes["name"].asString,
                        dupeApiRes["id"].asString
                    )
                }

                addBookmark(name, uuid)
                ChatUtils.chat("&aAdded $name to your bookmarks!", true)
            }.start()
        }

        fun addBookmark(name: String, uuid: String) {
            data.bookmarks.add(BookmarkEntry(name, uuid))
            DATA.saveToFile()
        }

        fun removeBookmark(name: String) {
            data.bookmarks.removeIf { entry -> entry.name == name }
            DATA.saveToFile()
        }
    }
}