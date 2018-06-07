package chx.studio.andreact.view

fun flex(fn: Flex.() -> Unit): Flex {
    return Flex().apply { fn() }
}

fun stack(fn: Stack.() -> Unit): Stack {
    return Stack().apply { fn() }
}

fun ViewGroup<*>.flex(fn: Flex.() -> Unit): Flex {
    return Flex().also {
        it.fn()
        children.add(it)
    }
}

fun ViewGroup<*>.stack(fn: Stack.() -> Unit): Stack {
    return Stack().also {
        it.fn()
        children.add(it)
    }
}

fun ViewGroup<*>.button(fn: Button.() -> Unit): Button {
    return Button().also {
        it.fn()
        children.add(it)
    }
}

fun ViewGroup<*>.text(fn: Text.() -> Unit): Text {
    return Text().also {
        it.fn()
        children.add(it)
    }
}

fun ViewGroup<*>.image(fn: Image.() -> Unit): Image {
    return Image().also {
        it.fn()
        children.add(it)
    }
}

fun ViewGroup<*>.touchable(fn: Touchable.() -> Unit): Touchable {
    return Touchable().also {
        it.fn()
        children.add(it)
    }
}