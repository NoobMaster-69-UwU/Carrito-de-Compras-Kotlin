































































































































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
    val name = "Kotlin"
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println("Hello, " + name + "!")

    for (i in 1..5) {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }
}
