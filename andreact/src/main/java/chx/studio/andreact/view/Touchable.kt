package chx.studio.andreact.view

class Touchable : ViewContainer() {
    var onClick: () -> Unit = { }

    override fun bindView() {
        super.bindView()
        view.setOnClickListener { onClick() }
    }
}