public class Square {
    private BorderType upperEdge;
    private BorderType bottomEdge;
    private BorderType leftEdge;
    private BorderType rightEdge;

    public BorderType getUpperEdge() {
        return upperEdge;
    }

    public BorderType getBottomEdge() {
        return bottomEdge;
    }

    public BorderType getLeftEdge() {
        return leftEdge;
    }

    public BorderType getRightEdge() {
        return rightEdge;
    }

    public void setUpperEdge(BorderType upperEdge) {
        this.upperEdge = upperEdge;
    }

    public void setBottomEdge(BorderType bottomEdge) {
        this.bottomEdge = bottomEdge;
    }

    public void setLeftEdge(BorderType leftEdge) {
        this.leftEdge = leftEdge;
    }

    public void setRightEdge(BorderType rightEdge) {
        this.rightEdge = rightEdge;
    }
}
