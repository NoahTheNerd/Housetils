package live.ixnoah.housetils.utils

data class Version(val version: String) {
    private val domains = version.split('.')
        .map { d -> return@map d.toIntOrNull() }
        .filterNotNull()

    fun compareTo(other: Version, depth: Int = 3): Int {
        if (domains.size < other.domains.size) {
            domains.forEachIndexed { i, d ->
                if (i > depth) return 0
                if (other.domains[i] == d) return@forEachIndexed
                if (other.domains[i]  > d) return 1
                if (other.domains[i]  < d) return -1
            }
        } else {
            other.domains.forEachIndexed { i, d ->
                if (i > depth) return 0
                if (domains[i] == d) return@forEachIndexed
                if (domains[i] >  d) return 1
                if (domains[i] <  d) return -1
            }
        }

        return 0
    }

    fun greaterThan(other: Version, depth: Int = 3): Boolean {
        return this.compareTo(other, depth) > 0
    }

    fun lessThan(other: Version, depth: Int = 3): Boolean {
        return this.compareTo(other, depth) < 0
    }

    override fun toString(): String {
        return domains.joinToString(".")
    }
}