package com.thelow_accessory_hud.thelow_accessory_hud.hud;

import com.thelow_accessory_hud.thelow_accessory_hud.config.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class thelow_accessory_hudHUD extends Gui {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new thelow_accessory_hudHUD());
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;//プレイヤーまたはワールドがない場合は実行しない

        // プレイヤーのインベントリ取得
        ItemStack topLeftItem = mc.thePlayer.inventory.mainInventory[9]; // インベントリ左上
        ItemStack topRightItem = mc.thePlayer.inventory.mainInventory[17];//インベントリ右上
        int x = ConfigHandler.hudX;//HUD表示位置指定
        int y = ConfigHandler.hudY;//HUD表示位置指定

        if (topLeftItem != null && topLeftItem.hasTagCompound()) {//アイテムがnullではない且アイテムにタグがある

            String item_id = "null";
            String info = "";
            NBTTagCompound nbt = topLeftItem.getTagCompound();
                
            if (nbt.hasKey("thelow_item_id")) {
                item_id = nbt.getString("thelow_item_id");//item_idにthelow_item_idを格納
            }
            
            String itemName = "null";
            int level = 0;
            if (nbt.hasKey("thelow_item_strength_level")) {//レベルデータがあるかをチェック
            	level = (int) nbt.getLong("thelow_item_strength_level");//levelに格納
            }
            else if (nbt.hasKey("display")) {//無かったら表示名を取得
                NBTTagCompound display = nbt.getCompoundTag("display");//表示タグ格納
                if (display.hasKey("Name")) {//表示名を確認
                    String name = display.getString("Name");//表示名格納
                    // JSON形式ならパースする
                    try {
                        com.google.gson.JsonObject json = new com.google.gson.JsonParser().parse(name).getAsJsonObject();//JSON形式に変換するらしい
                        if (json.has("text")) {
                            name = json.get("text").getAsString();//それをnameに格納
                        }
                    } catch (Exception e) {
                        // JSON形式でない場合は無視
                    }

                    // 色コード除去
                    name = name.replaceAll("§[0-9a-fk-or]", "");

                    // 正規表現で +数字 を抽出
                    java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\+([0-9]+)").matcher(name);
                    if (matcher.find()) {
                        level = Integer.parseInt(matcher.group(1));//intとしてlevelに格納
                    } 
                }
            }
            if (!item_id.equals("null")) {//idが存在するなら
            	if (item_id.equals("health_accessory")) {//HP増加アクセサリー
            		itemName = "§aHP§r増加";
            		level += 1;
            		if (level != 0) {
                    	info = "機能:§aHP§rが" + level + "増加する";
                    }
            	}
            	if (item_id.equals("mp_accessory")) {//MP増加アクセサリー
            		itemName = "§bMP§r増加";
            		if (level != 0) {
                    	level *= 3;
                    	info = "機能:§bMP§rが" + level + "増加する";
                    }
            	}
            	if (item_id.equals("mobexp_accessory")) {//mob/boss経験値増加アクセサリー
            		itemName = "§2mob/boss§r経験値増加";
            		if (level != 0) {
                    	level *= 3;
                    	info = "機能:§2mob/boss§rの経験値が" + level + "%増加する";
                    }
            	}
            	if (item_id.equals("expblock_accessory")) {//経験値ブロック経験値増加アクセサリー
            		itemName = "§5経験値ブロック§r増加";
            		if (level != 0) {
                    	level *= 3;
                    	info = "機能:§5経験値ブロック§r獲得量が" + level + "%増加する";
                    }
            	}
            	if (item_id.equals("posing_accessory")) {//ポーシングアクセサリー id取得に貢献してくれた Sorla さんに感謝します
            		itemName = "§dポーシング";
            		if (level != 0) {
                    	level *= level;
                    	info = "機能:§dポーシング§r確率が" + level + "%増加する";
                    }
            		
            	}}
            if (!itemName.equals("null")) {
            	
            	GlStateManager.pushMatrix();
            	mc.fontRendererObj.drawStringWithShadow("アクセサリー: " + itemName, x, y, 0xFFFFFF);
            	mc.fontRendererObj.drawStringWithShadow( info, x, y+10, 0xFFFFFF);
            	
            	GlStateManager.popMatrix();}
        }
        if (topRightItem != null && topRightItem.hasTagCompound()) {
        	String item_id_r = "null";
            String info_r = "";
            NBTTagCompound nbt_r = topRightItem.getTagCompound();
            if (nbt_r.hasKey("thelow_item_id")) {
                item_id_r = nbt_r.getString("thelow_item_id");//item_id_rにthelow_item_idを格納
            }
            String itemName_r = "null";
            if (!item_id_r.equals("null")) {
            	if (item_id_r.equals("エイドリアンのネックレス")) {
            		itemName_r = item_id_r;
            		info_r="冥界のボス戦で最大§a体力§rが増加する";
            	}
            }
            if (!itemName_r.equals("null")) {
            	mc.fontRendererObj.drawStringWithShadow("右上装備: " + itemName_r, x, y+20, 0xFFFFFF);
            	mc.fontRendererObj.drawStringWithShadow( info_r, x, y+30, 0xFFFFFF);
            }
        }
    }
}
