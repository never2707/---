import discord
from discord.ext import commands 
import json
import os

bot = commands.Bot(intents=discord.Intents.all(),command_prefix="=",owner_ids = "615463968173195265") #前綴字符


@bot.event  #啟動後顯示準備完成
async def on_ready():
    print("Log in as",bot.user.name)
    game = discord.Game('Apex Legend')
    #discord.Status.<狀態>，可以是online,offline,idle,dnd,invisible
    await bot.change_presence(status=discord.Status.do_not_disturb, activity=game)

    for filename in os.listdir("./cmds"):
        if filename.endswith(".py"):
            await bot.load_extension(f'cmds.{filename[:-3]}')   #導入cmds的檔案 
            

with open('items.json', "r", encoding = "utf8") as file:
    data = json.load(file)    #導入items.json這個檔案

@bot.command()
async def load(ctx,extension):
    """加載一個模組"""
    await bot.load_extension(f"cmds.{extension}")
    await ctx.send(f"Loaded {extension} done.")  #載入cog

@bot.command()
async def unload(ctx,extension):
    """卸載一個模組"""
    await bot.unload_extension(f"cmds.{extension}")
    await ctx.send(f"Unloaded {extension} done.")   #卸載cog

@bot.command()
async def reload(ctx,extension):
    """重新載入一個模組"""
    await bot.reload_extension(f"cmds.{extension}")
    await ctx.send(f"Reloaded {extension} done.")  #重載入cog

if __name__ == "__main__":
    bot.run(data['token']) #啟動機器人