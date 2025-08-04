enum class Manager {
    PIEQ1001,
    PIEQ1002,
    PIEQ1003,
    PIEQ1004;

    companion object {
        override fun toString(): String {
            return entries.joinToString(", ") { it.toString() }
        }

        fun from(input: String): Manager? {
            return try {
                valueOf(input.trim().uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}