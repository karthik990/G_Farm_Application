package org.jdom2;

class CloneBase implements Cloneable {
    protected CloneBase() {
    }

    /* access modifiers changed from: protected */
    public CloneBase clone() {
        try {
            return (CloneBase) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(String.format("Unable to clone class %s which should always support it.", new Object[]{getClass().getName()}), e);
        }
    }
}
