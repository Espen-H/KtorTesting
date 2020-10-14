package com.Ktor.Testing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.features.*
import io.ktor.http.content.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
            call.respond(
                TextContent(
                    "${it.value} ${it.description}",
                    ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                    it
                )
            )
        }
        status(HttpStatusCode.Unauthorized) {
            call.respond(
                TextContent(
                    "${it.value} ${it.description}",
                    ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                    it
                )
            )
        }
    }

    data class KotlinEvent(val name: String, val video: String = "", val slides: String = "")

    val client = HttpClient(Apache) {}
    val eventListDay1 = mutableListOf<KotlinEvent>(
        KotlinEvent(
            "Opening Keynote",
            "https://youtu.be/pD58Dw17CLk",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/keynote.pdf"
        ),
        KotlinEvent(
            "Contributing to Kotlin",
            "https://youtu.be/Ei32LzH1pe8",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/community.pdf"
        ),
        KotlinEvent(
            "New Language Features in Kotlin 1.4",
            "https://youtu.be/9ihevvUCoG0",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/features14.pdf"
        ),
        KotlinEvent(
            "Kotlin 1.4 in IntelliJ-based IDEs: Boosting Quality and Performance",
            "https://youtu.be/-5ZjMwNzWrk",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/ide.pdf"
        ),
        KotlinEvent(
            "A Look Into the Future",
            "https://youtu.be/0FF19HJDqMo",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/future.pdf"
        ),
        KotlinEvent(
            "Q & A",
            "https://www.youtube.com/watch?v=xJawa3C6pss&feature=youtu.be&t=11059",
        )
    )
    val eventListDay2 = mutableListOf<KotlinEvent>(
        KotlinEvent(
            "Coroutines Update",
            "https://youtu.be/E5bje5HgKs0",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/coroutines.pdf"
        ),
        KotlinEvent(
            "kotlinx.serialization 1.0",
            "https://youtu.be/Azi57n59ICM",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/serialiaztion.pdf"
        ),
        KotlinEvent(
            "News From the Kotlin Standard Library",
            "https://youtu.be/NJjEFXeiuKY",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/stdlib.pdf"
        ),
        KotlinEvent(
            "Introducing kotlinx-datetime",
            "https://youtu.be/YwN0kAMNvXI",
            "https://resources.jetbrains.com/storage/products/kotlin/events/kotlin14/Slides/datetime%20.pdf"
        ),
        KotlinEvent(
            "Q&A",
            "https://youtu.be/bz33QjVUJbs?t=8990",
        ),
    )


    routing {
        get("/") {
            call.respondText("Testing Ktor! Try out /hello and /schedule", contentType = ContentType.Text.Plain)
        }

        get("/hello") {
            call.respondHtml {
                head {
                    title { +"Ktor testing" }
                }

                body {
                    h1 { +"Hello World" }
                    h2 { +"Random image from Unsplashed to improve your day!" }
                    img { src = "https://source.unsplash.com/random" }
                }
            }
        }

        get("/schedule") {

            val href = Placeholder<HtmlBlockTag>()

            call.respondHtml(status = HttpStatusCode.OK) {
                body {

                    h1 { +"Kotlin 1.4 day 1" }
                    ul {
                        for (event in eventListDay1) {
                            li { +event.name }
                            ol {
                                unsafe { +"<a href='${event.video}'>video</a> " };
                                if (event.slides != "") unsafe {
                                    +" || <a href ='${event.slides}'> slides </a>"
                                }
                            }
                        }
                    }

                    h1 { +"Kotlin 1.4 day 2" }
                    ul {
                        for (event in eventListDay2) {
                            li { +event.name }
                            ol {
                                unsafe { +"<a href='${event.video}'>video</a> " };
                                if (event.slides != "") unsafe {
                                    +" || <a href ='${event.slides}'> slides </a>"
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


