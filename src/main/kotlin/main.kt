import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import java.io.File
import kotlin.reflect.KClass

@RemoteLoadStrategy(Strategy.LOAD_REMOTE_BEFORE_UNLOAD)
@Module("ZXeRRinor", "DiscordConnection", "0.1", ModuleType.EXECUTABLE, [])
class DiscordConnection : ExecutableModule {
    lateinit var token: String
    private val eventListener = EventListener()

    override fun initialize() {
        val rawParameters = File("parameters.yml")
        val parameters = rawParameters.readLines().map {
            val splitted = it.split(':')
            splitted[0] to splitted[1]
        }
        parameters.forEach {
            if (it.first == "token") token = it.second
        }
    }

    override fun run() {
        JDABuilder.createDefault(token).build().addEventListener(eventListener)
    }

    override fun prepareToUnload() {

    }

    override fun stop() {

    }

    fun addHandlerForEvent(eventClass: KClass<*>, handler: (GenericEvent) -> Unit) {
        val existingHandler = eventListener.handlers[eventClass]
        if(existingHandler != null) eventListener.handlers[eventClass] = {
            existingHandler(it)
            handler(it)
        } else eventListener.handlers[eventClass] = handler
    }

    fun clearHandlersForEvent(eventClass: KClass<*>) {
        eventListener.handlers.remove(eventClass)
    }
}