package org.example.runtime

import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.DigitalOutput
import com.pi4j.io.gpio.digital.DigitalOutputProvider
import com.pi4j.util.Console
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import runtime.setup.Configuration
import runtime.setup.Injector


/**
 * LESSON 03: Hardware configs parsing, Hot Plugging presets
 * GPIO-Kotlin-Pi4j project.
 * Kotlin Gpio project. Working with IO lines on Raspberry Pi using Pi4J Kotlin/Java langs and remote
 * compiling / debugging to any ARM GPIO compatible hardware. Advanced AI features (TensorFlow)
 */

//Hardware
lateinit var pi4j: Context
lateinit var console: Console
lateinit var configuration: Configuration
//add hardware config later
lateinit var ledOutput: DigitalOutput

fun main() {

    init()

    //Print out
    console.println(pi4j.boardInfo().boardModel)
    println(configuration)

    ledOutput = pi4j.digitalOutput<DigitalOutputProvider>().create(2)

    runBlocking {
        for (i in 0..10) {
            if (ledOutput.isLow) {
                ledOutput.high()
            } else {
                ledOutput.low()
            }
            delay(1000)
            println("$i")
        }
    }

    shutdownHardware()

}

fun init() {
    pi4j = Injector.getPi4j()
    console = Injector.getPi4jConsole()
    configuration = Injector.getRuntimeConfiguration().getConfiguration("lesson03config.json")
}

fun shutdownHardware() {
    ledOutput.low()
    pi4j.shutdown()
    println("...Connection was closed.")
}