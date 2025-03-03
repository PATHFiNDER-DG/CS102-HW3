import java.util.Random;
import java.util.ArrayList;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players (DA)
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        for (int i = 0; i < players.length; i++) {
            for (int j = i*14; j < i*14 + 14; j++) {
                players[i].addTile(tiles[j]); // TODO addTile
            }
        }
        players[0].addTile(tiles[56]); //TODO addTile
    }

    /*
     * TODO: get the last discarded tile for the current player (FSK)-DONE-
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        Tile pickedTile = lastDiscardedTile;
        if (pickedTile == null)
        {
            return "No tile to pick up!";
        }
        lastDiscardedTile = null;

        return pickedTile.toString();

    }

    /*
     * TODO: get the top tile from tiles array for the current player (FSK)-DONE-
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked Ao
     */
    public String getTopTile() {
        
        boolean tileLeft = false;
        for (Tile tile : tiles) {
            if (tile != null) {
                tileLeft = true;
            }
        }

        if(!tileLeft)
        {
            return "No tiles left.";
        }

        Tile [] newTiles = new Tile[tiles.length-1];
        Tile pickedTile = tiles[tiles.length-1];
        tiles[tiles.length-1] = null;

        for (int i = 0; i<tiles.length-1; i++)
        {
            newTiles[i] = tiles[i];
        }
        tiles = newTiles;
       
        return pickedTile.toString();
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts (DA)
     */
    public void shuffleTiles() {
        Random random = new Random();
        for (int i = 0; i < tiles.length; i++) {
            int randomInt = random.nextInt(tiles.length);
            Tile dummy = tiles[randomInt];
            tiles[randomInt] = tiles[i];
            tiles[i] = dummy;
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player (AYÇ) --Done--
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {

        boolean tileLeft = false;
        for (Tile tile : tiles) {
            if (tile != null) {
                tileLeft = true;
            }
        }

        if (players[currentPlayerIndex].isWinningHand())
        {
            return true;
        } else if (!tileLeft) {
            
            return true;
        }
        return false;
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following: (KD)
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {

        Player currentPlayer = players[currentPlayerIndex];

        if (currentPlayer.canUseTile(lastDiscardedTile)) {

            players[currentPlayerIndex].addTile(lastDiscardedTile);
            System.out.println(getCurrentPlayerName() + " picked up the last discraded tile " + getLastDiscardedTile());
            ;
        } else {

            boolean tileLeft = false;
            for (Tile tile : tiles) {
                if (tile != null) {
                    tileLeft = true;
                }
            }
    
            if(tileLeft) {
                players[currentPlayerIndex].addTile(tiles[0]);
                System.out.println(getCurrentPlayerName() + " picked up the top tile.");
            }
            
            
            
        }
    }

    /*
     * TODO: Current computer player will discard the least useful tile. (KD)
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
     //IMPORTANT NOTE: Added null check for the possibility for null exception if an arrays one of the members is null
     //this normally shouldnt be the case but for precaution they're added , null checks can be removed or reordered after final checks -Kayra
    public void discardTileForComputer() {
        
        Player currentPlayer = players[currentPlayerIndex];
        Tile[] playerTiles = currentPlayer.getTiles(); // Get tiles as an array
    
        Tile tileToDiscard = null;
        int minChainSize = 4;
    
        //Find if there is a duplicate tile to discard
        //Picks the first duplicate by order no special way
        for (int i = 0; i < playerTiles.length - 1; i++) {
            if (playerTiles[i] != null && playerTiles[i + 1] != null &&
                playerTiles[i].getValue() == playerTiles[i + 1].getValue()) {
                tileToDiscard = playerTiles[i];
                break;
            }
        }
    
        //If no duplicate found, find a single tile that is not part of any chain
        if (tileToDiscard == null) {
            for (Tile tile : playerTiles) {
                if (tile != null && !currentPlayer.canUseTile(tile)) { // If tile does not contribute to a valid chain
                    tileToDiscard = tile;
                    break;
                }
            }
        }
    
        //If all tiles contribute to chains, discard from the shortest chain
        if (tileToDiscard == null) {
            for (Tile tile : playerTiles) {
                int chainSize = 0;
                for (Tile t : playerTiles) {
                    if (t != null && t.getValue() == tile.getValue()) {
                        chainSize++;
                    }
                }
                if (chainSize < minChainSize) {
                    minChainSize = chainSize;
                    tileToDiscard = tile;
                }
            }
        }
    
        //Print the discarded tile and discard the tile.
        if (tileToDiscard != null) {
            System.out.println(getCurrentPlayerName() + " discarded " + tileToDiscard.toString());
            System.out.println();
            int discardingTilePlace = 0;
            for (int i = 0; i < playerTiles.length; i++) {
                if (playerTiles[i].equals(tileToDiscard)) {
                    discardingTilePlace = i;
                    break;
                }
            }
            discardTile(discardingTilePlace);//Ps. Used the method but if there is a problem discard can be added manually to discardedPile
        }

    }

    /*
     * TODO: discards the current player's tile at given index (MB)
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
   public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
