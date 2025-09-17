package com.carrito

import com.carrito.models.ShoppingCart
import com.carrito.services.Inventory
import java.io.FileWriter
import java.io.IOException
import kotlin.math.max
// Menu principal

// Función para confirmar la compra y generar factura con tabulaciones fijas
fun confirmPurchase(cart: ShoppingCart) {
    if (cart.getItems().isEmpty()) {
        println("\nCarrito vacío. No hay nada que comprar.")
        return
    }
    println("\n===  Factura  ===")
    println(cart.display())
    val taxRate = 0.13 // IVA 13% para El Salvador
    val subtotal = cart.getTotal()
    val tax = subtotal * taxRate
    val totalWithTax = subtotal + tax
    println(String.format("%-20s  %-20s", "  Subtotal  :", "  $" + String.format("%.2f", subtotal)))
    println(String.format("%-20s  %-20s", "  Impuestos  (IVA  13%)  :", "  $" + String.format("%.2f", tax)))
    println(String.format("%-20s  %-20s", "  Total  Final  :", "  $" + String.format("%.2f", totalWithTax)))
    if (confirmAction("¿Confirmar compra?")) {
        cart.getItems().forEach { item ->
            Inventory.updateQuantity(item.product.productCode, item.quantity)
        }
        println("Compra confirmada. Inventario actualizado.")
        print("¿Deseas seguir comprando? (s/n): ")
        if (readLine()?.trim()?.lowercase() == "s") {
            cart.clear() // Reinicia el carrito a cero
            println("Carrito reiniciado. ¡Puedes continuar comprando!")
        } else {
            println("Sesión terminada.")
            System.exit(0) // Sale limpiamente si no continúa
        }
    } else {
        println("Compra cancelada.")
    }
}

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

// Función para loguear errores en un archivo
fun logError(message: String) {
    try {
        FileWriter("log/errors.log", true).use { writer ->
            writer.appendLine("${java.time.LocalDateTime.now()}: $message")
        }
    } catch (e: IOException) {
        println("Error al loguear: ${e.message}")
    }
}

// Función para validar input con un predicado personalizado
fun validateInput(prompt: String, validator: (String) -> Boolean, errorMsg: String): String? {
    print(prompt)
    val input = readLine()?.trim()
    return if (input != null && validator(input)) input else {
        println(errorMsg)
        logError("$errorMsg - Input: $input")
        null
    }
}

// Función para validar códigos de producto (alfanuméricos simples)
fun validateProductCode(code: String): Boolean {
    val regex = Regex("^[A-Za-z0-9]+$") // Solo alfanuméricos, sin espacios
    return code.isNotBlank() && regex.matches(code)
}

// Función para validar cantidades numéricas (enteros positivos)
fun validateQuantity(input: String): Int? {
    val qty = input.trim().toIntOrNull()
    return if (qty != null && qty > 0) qty else null
}

// Función para validar precio decimal (double positivo)
fun validatePrice(input: String): Double? {
    val price = input.trim().toDoubleOrNull()
    return if (price != null && price > 0.0) price else null
}

// Función para pedir confirmación con s/n
fun confirmAction(prompt: String): Boolean {
    print("$prompt (s/n): ")
    val response = readLine()?.trim()?.lowercase()
    return response == "s"
}


}