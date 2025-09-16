package com.carrito

import com.carrito.models.ShoppingCart
import com.carrito.services.Inventory
import java.io.FileWriter
import java.io.IOException
import kotlin.math.max




// Menu principal
fun main() {
    val cart = ShoppingCart() // Instancia del carrito
    var continueShopping = true // Controla el loop del menú

    while (continueShopping) {
        println("\n===  Sistema  de  Carrito  de  Compras  en  Consola  ===")
        println(String.format("%-40s  %-20s", "  1.  Ver  lista  de  productos  disponibles  ", "  "))
        println(String.format("%-40s  %-20s", "  2.  Agregar  producto  al  carrito  ", "  "))
        println(String.format("%-40s  %-20s", "  3.  Editar  cantidad  en  carrito  ", "  "))
        println(String.format("%-40s  %-20s", "  4.  Eliminar  producto  del  carrito  ", "  "))
        println(String.format("%-40s  %-20s", "  5.  Visualizar  carrito  ", "  "))
        println(String.format("%-40s  %-20s", "  6.  Confirmar  compra  y  generar  factura  ", "  "))
        println(String.format("%-40s  %-20s", "  7.  Salir  ", "  "))
        print("Elige una opción (1-7): ")

        val option = readLine()?.trim()?.toIntOrNull()
        if (option == null) {
            println("Entrada inválida. Intenta de nuevo.")
            logError("Entrada no numérica en menú principal")
            continue
        }

        try {
            when (option) {
                1 -> {
                    val productsList = Inventory.displayProducts()
                    if (productsList == "Inventario vacío") {
                        println("No hay productos disponibles en este momento. ¡Vuelve más tarde!")
                    } else {
                        println(productsList)
                    }
                }
                2 -> addToCart(cart)
                3 -> editCartItem(cart)
                4 -> removeFromCart(cart)
                5 -> println(cart.display())
                6 -> confirmPurchase(cart)
                7 -> continueShopping = false
                else -> println("Opción inválida. Elige entre 1 y 7.")
            }
        } catch (e: Exception) {
            logError(e.message ?: "Error desconocido")
            println("Ocurrió un error: ${e.message}. Intenta de nuevo.")
            println("Stack trace: ${e.stackTraceToString()}") // Para depuración
        }
    }
    println("¡Gracias por usar el sistema! 🛒")
}