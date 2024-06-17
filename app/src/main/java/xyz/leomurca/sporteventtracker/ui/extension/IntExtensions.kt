package xyz.leomurca.sporteventtracker.ui.extension
fun Int.formatRemainingSeconds(): String {
    if (this == 0) return "00h:00m:00s"
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return "%02dh:%02dm:%02ds".format(hours, minutes, seconds)
}
