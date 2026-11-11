package tfar.stonesoupadditions;

public record ClientControls(boolean holdingLeftClick,boolean holdingRightClick, long lastLeftClick, long lastRightClick, long ticksLeftClickHeld) {

    public boolean recentlyHoldingLeftClick(long currentTime) {
        return lastLeftClick >=0 && (currentTime - lastLeftClick) < 10;
    }

    public boolean recentlyHoldingRightClick(long currentTime) {
        return lastRightClick >=0 && (currentTime - lastRightClick) < 10;
    }

    public ClientControls update(long timestamp, boolean isHoldingLeft, boolean isHoldingRight) {

        boolean hasClickedLeft = !this.holdingLeftClick && isHoldingLeft;
        boolean hasClickedRight = !this.holdingRightClick && isHoldingRight;

        long newLastLeftClick = lastLeftClick;
        long newLastRightClick = lastRightClick;

        boolean incrementLeftClickHeld = isHoldingLeft && this.holdingLeftClick;

        long newLeftClickHeld = incrementLeftClickHeld ? (ticksLeftClickHeld+1) : 0;

        if (hasClickedLeft) {
            newLastLeftClick = timestamp;
        }

        if (hasClickedRight) {
            newLastRightClick = timestamp;
        }

        return new ClientControls(isHoldingLeft,isHoldingRight,newLastLeftClick,newLastRightClick,newLeftClickHeld);
    }
}
