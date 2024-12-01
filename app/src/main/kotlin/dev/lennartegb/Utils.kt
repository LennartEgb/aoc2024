package dev.lennartegb

import java.io.File

fun file(name: String): File {
    val path = System.getProperty("user.dir") + "/app/src/main/resources"
    return File("$path/$name")
}
