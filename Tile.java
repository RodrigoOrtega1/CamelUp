public class Tile {
    boolean hasModifier = false;
    boolean modifier;
    SimpleLinkedList<Camel> camelStack = new SimpleLinkedList<>();
    
    public boolean getHasModifier() {
        return hasModifier;
    }
    public void setHasModifier(boolean hasModifier) {
        this.hasModifier = hasModifier;
    }
    public boolean getModifier() {
        return modifier;
    }
    public void setModifier(boolean modifier) {
        this.modifier = modifier;
    }
    

}
