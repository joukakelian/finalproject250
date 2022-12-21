package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class MetroTile extends Tile {
	public double metroTimeCost = 100;
	public double metroDistanceCost = 100;
	public double metroCommuteFactor = 0.2;
	
    public MetroTile() {
        super(1,1,2);
        this.type = TileType.Metro;
    }
    
    public void fixMetro(Tile node) {
        double manhattanDist = Math.abs(this.xCoord - node.costEstimate) + Math.abs(this.yCoord - node.yCoord);
        this.metroTimeCost = manhattanDist * metroCommuteFactor;
        this.metroDistanceCost = manhattanDist / metroCommuteFactor;
    }
}
