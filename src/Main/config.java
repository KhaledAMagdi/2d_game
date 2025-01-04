package Main;

import java.io.*;

public class config
{
    GamePanel gp;
    public config(GamePanel gp)
    {
        this.gp = gp;
    }
    public void saveConfig()
    {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/res/config.txt"));

            //FullScreen
            if(gp.fullScreenOn == true)
            {
                bw.write("On");
            }
            if(gp.fullScreenOn == false)
            {
                bw.write("Off");
            }
            bw.newLine();

            //Music volume


            //SE volume

            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadConfig()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/res/config.txt"));
            String s = br.readLine();

            //FullScreen
            if(s.equals("On"))
            {
                gp.fullScreenOn = true;
            }if(s.equals("Off"))
            {
                gp.fullScreenOn = false;
            }

            //Music volume

            //SE volume

            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}