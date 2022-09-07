package studio.eyesthetics.tetaarchitecturelesson.application.base

sealed class Notification(open val message: String) {
    data class ErrorMessage(
        override val message: String,
        val label: String = "",
        val handler: (() -> Unit)? = null
    ) : Notification(message)
}