package com.InfinityRaider.AgriCraft.renderers;

import com.InfinityRaider.AgriCraft.models.ModelSeedAnalyzer;
import com.InfinityRaider.AgriCraft.reference.Constants;
import com.InfinityRaider.AgriCraft.reference.Reference;
import com.InfinityRaider.AgriCraft.tileentity.TileEntitySeedAnalyzer;
import com.InfinityRaider.AgriCraft.utility.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class RenderSeedAnalyzer extends TileEntitySpecialRenderer {
    private ResourceLocation texture;
    private final ModelSeedAnalyzer model;

    public RenderSeedAnalyzer() {
        this.texture = new ResourceLocation(Reference.MOD_ID.toLowerCase()+":textures/blocks/seedAnalyzer.png");
        this.model = new ModelSeedAnalyzer();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        TileEntitySeedAnalyzer analyzer= (TileEntitySeedAnalyzer) tileEntity;
        double unit = Constants.unit;
        //render the model
        GL11.glPushMatrix();                                                            //initiate first gl renderer
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);    //sets the rendering origin to the right spot
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);            //loads texture for the model
            GL11.glRotatef(getAngle(analyzer.direction), 0.0F, 1.0F, 0.0F);             //rotates the renderer to render the model in the right orientation
            GL11.glPushMatrix();                                                        //initiate second gl renderer
                GL11.glRotatef(180, 0F, 0F, 1F);                                        //rotate the renderer so the model doesn't render upside down
                this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);        //actually renders the model
            GL11.glPopMatrix();                                                         //close second gl renderer
        GL11.glPopMatrix();                                                             //close first gl renderer
        if(analyzer.seed!=null) {
            renderSeed(analyzer, x, y, z);                                              //if the analyzer has a seed, render the seed slowly spinning
        }

    }

    //renders the seed
    private void renderSeed(TileEntitySeedAnalyzer analyzer, double x, double y, double z) {
        //set up the tessellator
        Tessellator tessellator = Tessellator.instance;
        //grab the texture
        ResourceLocation resource = RenderHelper.getResource(analyzer.getStackInSlot(0).getItem(),analyzer.getStackInSlot(0).getItemDamage());
        //define rotation angle in function of system time
        float angle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);   //credits to Pahimar
        GL11.glPushMatrix();
            //translate to the desired position
            GL11.glTranslatef((float) x + Constants.unit*8, (float) y + Constants.unit*4, (float) z + Constants.unit*8);
            //resize the texture to half the size
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            //rotate the renderer
            GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
            //bind texture
            Minecraft.getMinecraft().renderEngine.bindTexture(resource);
            //start drawing
            tessellator.startDrawingQuads();
                //front
                tessellator.addVertexWithUV(0 - Constants.unit*8, 0, 0, 1, 1);
                tessellator.addVertexWithUV(0 - Constants.unit*8, 1, 0, 1, 0);
                tessellator.addVertexWithUV(1 - Constants.unit*8, 1, 0, 0, 0);
                tessellator.addVertexWithUV(1 - Constants.unit*8, 0, 0, 0, 1);
                //back
                tessellator.addVertexWithUV(0 - Constants.unit*8, 0, 0, 1, 1);
                tessellator.addVertexWithUV(1 - Constants.unit*8, 0, 0, 0, 1);
                tessellator.addVertexWithUV(1 - Constants.unit*8, 1, 0, 0, 0);
                tessellator.addVertexWithUV(0 - Constants.unit*8, 1, 0, 1, 0);
                //note the texture is rotating around the (0,1,0) axis, which goes trough the centre of the surface, in order to rotate the texture around it's centre axis
                //this axis has to be coincident with the rotation axis, this is why I am shifting all the vertices in the x direction over half a block
            tessellator.draw();
        GL11.glPopMatrix();
    }

    //gets the rotation angle based on the direction of the tile entity
    private int getAngle(ForgeDirection direction) {
        if(direction == ForgeDirection.NORTH) {
            return 0;
        }
        if(direction == ForgeDirection.WEST) {
            return 90;
        }
        if(direction == ForgeDirection.SOUTH) {
            return 180;
        }
        if(direction == ForgeDirection.EAST) {
            return 270;
        }
        return 0;
    }
}
