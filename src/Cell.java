public class Cell {
    private int costC = 0;
    private int costCh = 0;
    private int parentI, parentJ;
    private boolean isStartCell = false;
    private int i,j; // i, j - współrzędne w squares
    private Cell parent;

    public Cell(int i, int j, int parentI, int parentJ)
    {
        this.i = i;
        this.j = j;
        this.parentI = parentI;
        this.parentJ = parentJ;
    }

    public int getCostC() {
        return costC;
    }

    public void setCostC(int costC) {
        this.costC = costC;
    }

    public int getCostCh() {
        return costCh;
    }

    public void setCostCh(int Ch) {
        this.costCh = Ch;
    }

    public int getParentI() {
        return parentI;
    }

    public int getParentJ() {
        return parentJ;
    }

    public void setIJ(int i, int j)
    {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public boolean isStartCell() {
        return isStartCell;
    }

    public void setStartCell(boolean startCell) {
        isStartCell = startCell;
    }
}
