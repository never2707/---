import discord
from discord.ext import commands 
import random
from core.classes import Cog_Extension
import json 

with open('items.json', "r", encoding = "utf8") as file:
    data = json.load(file)

class React(Cog_Extension):
    @commands.command()
    async def pic(self,ctx):
        """傳一張圖片"""
        rdpic = random.choice(data["pic1"])
        pic = discord.File(rdpic)
        await ctx.send(file=pic)  #傳送圖片

    @commands.command()
    async def web(self,ctx):
        """傳一張網址圖片"""
        pic = random.choice(data["webpic"])
        await ctx.send(pic)  #傳送網路圖片

    @commands.command()
    async def gif(self,ctx):
        """傳一個GIF"""
        pic = discord.File(data["gif1"])
        await ctx.send(file=pic)  #傳送gif

async def setup(bot):
    await bot.add_cog(React(bot))