package ros.eagleoffire.roswallwalk.client;

public class ClientWallWalkingData {
    private static boolean wallWalking;

    public static void set(boolean wallWalking){
        ClientWallWalkingData.wallWalking = wallWalking;
    }

    public static boolean getPlayerWallWalking(){
        return wallWalking;
    }
}
