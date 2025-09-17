   // Calcula el total general del carrito sumando todos los totalPrice
    fun getTotal(): Double = items.sumOf { it.totalPrice() }

    // Genera una representación en string del carrito con anchos fijos
        fun display(): String {
        if (items.isEmpty()) return "Carrito vacío"
        val sb = StringBuilder()
        sb.appendLine(String.format("%-6s  %-40s  %-10s  %-12s  %-10s", "  Código  ", "  Nombre  ", "  Cantidad  ", "  Precio  Unitario  ", "  Subtotal  "))
        sb.appendLine(String.format("%-6s  %-40s  %-10s  %-12s  %-10s", "  ------  ", "  ------  ", "  --------  ", "  ------------  ", "  --------  "))
        items.forEach { item ->
            sb.appendLine(String.format("%-6s  %-40s  %-10d  %-12.2f  %-10.2f", item.product.productCode, item.product.name, item.quantity, item.product.price, item.totalPrice()))
        }
        sb.appendLine(String.format("%-86s  %-10.2f", "  Total  general  :", getTotal()))
        return sb.toString()
    }

    // Brinda una copia de la lista de ítems para evitar modificaciones externas
    fun getItems(): List<CartItem> = items.toList()

    // Limpia todos los ítems del carrito para caso de reiniciar el carrito despues de realizar una compra
    fun clear() {
        items.clear()
    }
}
