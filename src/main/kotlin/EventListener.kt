import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener
import kotlin.reflect.KClass

class EventListener : EventListener {
    val handlers = mutableMapOf<KClass<*>, (GenericEvent) -> Unit>()

    override fun onEvent(event: GenericEvent) {
        val handler = handlers[event::class]
        if(handler != null) handler(event)
    }
}