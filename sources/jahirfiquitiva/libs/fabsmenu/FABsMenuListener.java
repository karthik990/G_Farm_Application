package jahirfiquitiva.libs.fabsmenu;

public abstract class FABsMenuListener {
    public void onMenuCollapsed(FABsMenu fABsMenu) {
    }

    public void onMenuExpanded(FABsMenu fABsMenu) {
    }

    public void onMenuClicked(FABsMenu fABsMenu) {
        fABsMenu.toggle();
    }
}
