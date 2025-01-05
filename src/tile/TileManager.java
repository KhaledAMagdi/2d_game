package tile;

import Main.GamePanel;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;


public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    int tileNum = 714;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[tileNum];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/map.txt");
    }

    private void getTileImage() {
        for (int i = 0; i < tileNum; i++) {
            tile[i] = new Tile();
            String fileName = String.format("/res/tiles/tile%03d.png", i);

            try {
                tile[i].image = ImageIO.read(getClass().getResourceAsStream(fileName));
            } catch (IOException e) {
                System.out.println("Failed to load image: " + fileName);
            }
        }

        // Define collision tiles
        tile[57].collision = true;
        tile[58].collision = true;
        tile[59].collision = true;
        tile[70].collision = true;
        tile[71].collision = true;
        tile[72].collision = true;
        tile[42].collision = true;
        tile[43].collision = true;
        tile[44].collision = true;
        tile[82].collision = true;
        tile[81].collision = true;
        tile[80].collision = true;
    }

    private void loadMap(String map) {
        try {
            InputStream is = getClass().getResourceAsStream(map);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                int col = 0;
                int row = 0;

                while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                    String line = br.readLine();

                    while (col < gp.maxWorldCol) {
                        String numbers[] = line.split(" ");

                        int num = Integer.parseInt(numbers[col]);

                        mapTileNum[col][row] = num;
                        col++;
                    }
                    if (col == gp.maxWorldCol) {
                        col = 0;
                        row++;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Failed to load image: ");
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNumber = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
                    && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize
                    && worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
                    && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
                g2.drawImage(tile[tileNumber].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}