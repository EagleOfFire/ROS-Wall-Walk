package ros.eagleoffire.roswallwalk.capabilities;

public class WallWalkingCapability implements IWallWalkingCapability {
    private boolean wallWalking = false;

    @Override
    public boolean isWallWalking() {
        return wallWalking;
    }

    @Override
    public void setWallWalking(boolean wallWalking) {
        this.wallWalking = wallWalking;
    }
}
