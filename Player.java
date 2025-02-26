

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: removes and returns the tile in given index (MB)
     */
    public Tile getAndRemoveTile(int index) {
        return null;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order (AYÇ)
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {

    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition (DA)
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {
        int chains = 0;
        for (int i = 1; i < 8; i++) {
            if (countNumbers(i) > 3) {
                if (hasAllColors(i)) chains++;
            }
        }
        return chains == 3;
    }

/* given an int value, count how many times it appears in a player's tiles 
 * to be used in isWinningHand()
*/
    public int countNumbers(int i) { 
        int count = 0;
        for (Tile tile : playerTiles) {
            if (tile.getValue() == i) count++;
        }
        return count;
    }
/* given an int value, check if a chain is possible using that int
 * to be used in isWinningHand()
 */
    public boolean hasAllColors (int i) {
        boolean Y = false;
        boolean B = false;
        boolean R = false;
        boolean K = false;
        for (Tile tile : playerTiles) {
            if (tile.getValue() == i) {
                if (tile.getColor() == 'Y') Y = true;
                if (tile.getColor() == 'B') B = true;
                if (tile.getColor() == 'R') R = true;
                if (tile.getColor() == 'K') K = true;
            }
        }
        return Y && B && R && K;
    }
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
